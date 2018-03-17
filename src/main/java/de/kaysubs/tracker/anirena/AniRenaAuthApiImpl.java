package de.kaysubs.tracker.anirena;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import de.kaysubs.tracker.anirena.model.*;
import de.kaysubs.tracker.anirena.webscrape.CheckDeleteResponse;
import de.kaysubs.tracker.anirena.webscrape.CheckEditResponse;
import de.kaysubs.tracker.anirena.webscrape.TorrentEditParser;
import de.kaysubs.tracker.common.HttpUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;

public class AniRenaAuthApiImpl extends AniRenaApiImpl implements AniRenaAuthApi {
    private final Session session;

    public AniRenaAuthApiImpl(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    @Override
    public int uploadTorrent(UploadRequest request) {
        return upload(request, Optional.of(session));
    }

    @Override
    public void deleteTorrent(int torrentId) {
        HttpGet get = new HttpGet("http://www.anirena.com/torrent_delete.php?id=" + torrentId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, session.toCookies());
        parsePage(response, new CheckDeleteResponse());
    }

    @Override
    public EditRequest getEditRequest(int torrentId) {
        HttpGet get = new HttpGet("https://www.anirena.com/torrent_edit.php?id=" + torrentId);
        get.setConfig(HttpUtil.WITH_TIMEOUT);

        HttpResponse response = HttpUtil.executeRequest(get, session.toCookies());
        return parsePage(response, new TorrentEditParser());
    }

    @Override
    public void editTorrent(EditRequest request) {
        HttpPost post = new HttpPost("https://www.anirena.com/torrent_edit.php");
        post.setConfig(HttpUtil.WITH_TIMEOUT);

        MultipartEntityBuilder form = MultipartEntityBuilder.create();
        form.addTextBody("n", request.getName());
        form.addTextBody("d", request.isDht() ? "1" : "0");
        form.addTextBody("t", Integer.toString(request.getCategory().getId()));
        form.addTextBody("c", request.getComment());
        form.addTextBody("a1", request.getMainTracker());
        form.addTextBody("a2", Arrays.stream(request.getAnnounceList())
                .collect(Collectors.joining("\n")));
        form.addTextBody("id", Integer.toString(request.getTorrentId()));
        form.addTextBody("info", request.getInfo());
        form.addTextBody("creation_date", Long.toString(request.getCreationDate()));
        form.addTextBody("creation_by", request.getCreatedBy());
        form.addTextBody("encoding", request.getEncoding());
        form.addTextBody("submit", "Submit");
        post.setEntity(form.build());

        HttpResponse response = HttpUtil.executeRequest(post, session.toCookies());
        parsePage(response, new CheckEditResponse());
    }

}
