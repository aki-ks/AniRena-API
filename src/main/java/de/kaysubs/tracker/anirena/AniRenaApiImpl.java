package de.kaysubs.tracker.anirena;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.kaysubs.tracker.anirena.exception.AnirenaException;
import de.kaysubs.tracker.anirena.exception.LoginException;
import de.kaysubs.tracker.anirena.exception.WebScrapeException;
import de.kaysubs.tracker.anirena.model.*;
import de.kaysubs.tracker.anirena.webscrape.*;
import de.kaysubs.tracker.common.HttpUtil;
import de.kaysubs.tracker.common.exception.HttpException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

public class AniRenaApiImpl implements AniRenaApi {
    private final static AniRenaApiImpl INSTANCE = new AniRenaApiImpl();
    private final static Gson GSON = new GsonBuilder().create();

    public static AniRenaApiImpl getInstance() {
        return INSTANCE;
    }

    private <T> T parseJsonEntity(HttpEntity entity, Class<T> clazz) {
        Charset charset = ContentType.getOrDefault(entity).getCharset();
        if(charset == null)
            charset = Charset.forName("UTF-8");

        InputStream content;
        try {
            content = entity.getContent();
        } catch (IOException e) {
            throw new HttpException("Cannot read response content", e);
        }

        return GSON.fromJson(new InputStreamReader(content, charset), clazz);
    }

    protected <T> T parsePage(HttpResponse response, Parser<T> parser) {
        Document page = Jsoup.parse(HttpUtil.readIntoString(response));

        try {
            return parser.parsePage(page);
        } catch(AnirenaException | HttpException e) {
            throw e;
        } catch(Exception e) {
            throw new WebScrapeException(e);
        }
    }

    @Override
    public Statistics getStatistics() {
        HttpGet get = new HttpGet("https://www.anirena.com/");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get);
        return parsePage(response, new StatisticsParser());
    }

    @Override
    public TorrentHeader[] search(SearchRequest request) {
        URI uri;
        try {
            URIBuilder builder = new URIBuilder()
                    .setScheme("https")
                    .setHost("anirena.com")
                    .setPath("index.php");

            request.getTerm().ifPresent(term ->
                    builder.addParameter("s", term));

            request.getCategory().ifPresent(category ->
                    builder.addParameter("t", Integer.toString(category.getId())));

            request.getOffset().ifPresent(offset ->
                    builder.addParameter("start", Integer.toString(offset)));

            uri = builder.build();
        } catch (URISyntaxException e) {
            throw new HttpException("Cannot build URL", e);
        }

        HttpGet get = new HttpGet(uri);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get);

        return parsePage(response, new TorrentListParser());
    }

    @Override
    public TorrentMeta getTorrentMeta(int torrentId) {
        HttpGet get = new HttpGet("https://www.anirena.com/torrent_details.php?id=" + torrentId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get);

        return parseJsonEntity(response.getEntity(), TorrentMeta.class);
    }

    private Session fetchTempSession() {
        HttpGet get = new HttpGet("https://www.anirena.com/ucp.php?mode=login");
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        CookieStore store = new BasicCookieStore();
        HttpUtil.executeRequest(get, store);

        return new Session(store.getCookies());
    }

    @Override
    public AniRenaAuthApi login(String username, String password) {
        Session tempSession = fetchTempSession();

        HttpPost post = new HttpPost("https://www.anirena.com/ucp.php?mode=login");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        MultipartEntityBuilder form = MultipartEntityBuilder.create();
        form.addTextBody("username", username);
        form.addTextBody("password", password);
        form.addTextBody("sid", tempSession.getSid());
        form.addTextBody("redirect", "index.php");
        form.addTextBody("sid", tempSession.getSid());
        form.addTextBody("redirect", "index.php");
        form.addTextBody("login", "Login");
        post.setEntity(form.build());

        CookieStore store = new BasicCookieStore();
        HttpUtil.executeRequest(post, store);

        try {
            return new AniRenaAuthApiImpl(new Session(store.getCookies()));
        } catch (IllegalArgumentException e) {
            throw new LoginException();
        }
    }

    @Override
    public int uploadTorrentAnonymously(UploadRequest request) {
        return upload(request, Optional.empty());
    }

    protected TorrentFileInfo fetchFileTorrentInfo(UploadRequest request, Optional<Session> session) {
        HttpPost post = new HttpPost("https://www.anirena.com/torrent_upload.php");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        MultipartEntityBuilder form = MultipartEntityBuilder.create();

        File seedFile = request.getTorrentFile();
        ContentType torrentMime = ContentType.create("application/x-bittorrent");
        form.addBinaryBody("f", seedFile, torrentMime, seedFile.getName());

        form.addTextBody("submit", "Next");

        post.setEntity(form.build());

        Cookie[] cookies = session.map(Session::toCookies).orElse(new Cookie[0]);
        HttpResponse response = HttpUtil.executeRequest(post, cookies);
        return parsePage(response, new TorrentFileInfoParser());
    }

    protected int upload(UploadRequest request, Optional<Session> session) {
        TorrentFileInfo info = fetchFileTorrentInfo(request, session);

        HttpPost post = new HttpPost("https://www.anirena.com/torrent_upload.php");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        MultipartEntityBuilder form = MultipartEntityBuilder.create();

        form.addTextBody("n", request.getName().orElse(info.getName()));
        form.addTextBody("t", Integer.toString(request.getCategory().orElse(info.getCategory()).getId()));
        form.addTextBody("c", request.getComment().orElse(info.getComment()));
        form.addTextBody("a1", request.getMainTracker().orElse(info.getMainTracker()));
        form.addTextBody("a2", Arrays.stream(request.getAnnounceList().orElse(info.getAnnounceList()))
                .collect(Collectors.joining("\n")));
        form.addTextBody("info", info.getInfo());
        form.addTextBody("creation_date", Long.toString(request.getCreationDate().orElse(info.getCreationDate())));
        form.addTextBody("creation_by", request.getCreatedBy().orElse(info.getCreatedBy()));
        form.addTextBody("encoding", info.getEncoding());
        form.addTextBody("submit", "Submit");

        post.setEntity(form.build());

        Cookie[] cookies = session.map(Session::toCookies).orElse(new Cookie[0]);
        HttpResponse response = HttpUtil.executeRequest(post, cookies);
        return parsePage(response, new TorrentUploadResponseParser());
    }

}
