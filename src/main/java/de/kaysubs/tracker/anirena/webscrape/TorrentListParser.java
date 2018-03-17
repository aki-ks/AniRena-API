package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.exception.WebScrapeException;
import de.kaysubs.tracker.anirena.model.Category;
import de.kaysubs.tracker.anirena.model.DataSize;
import de.kaysubs.tracker.anirena.model.TorrentHeader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TorrentListParser implements Parser<TorrentHeader[]> {
    private final static Pattern DOWNLOAD_URL_PATTERN = Pattern.compile("\\./dl/([0-9]+)");

    @Override
    public TorrentHeader[] parsePage(Document page) {
        return page.selectFirst("div#content")
                .select("div.full2").stream()
                .flatMap(e -> e.select("table").stream())
                .map(this::parseTable)
                .toArray(TorrentHeader[]::new);
    }

    public TorrentHeader parseTable(Element table) {
        String imageUrl = table.selectFirst("td.torrents_small_type_data1")
                .selectFirst("img").attr("src");

        Category category = categoryFromImageUrl(imageUrl);

        String title = table.selectFirst("td.torrents_small_info_data1")
                .selectFirst("a[nohref]").attr("title");

        Element linkCell = table.selectFirst("td.torrents_small_info_data2");
        String relativeDownloadPath = linkCell.selectFirst("a[title=Download Torrent]").attr("href");
        int torrentId = extractIdFromDownloadLink(relativeDownloadPath);

        URL downloadLink = null;
        URI magnetLink = null;
        try {
            downloadLink = new URL(new URL("https://anirena.com/"), relativeDownloadPath);
            magnetLink = new URI(linkCell.selectFirst("a[title=Magnet Link]").attr("href"));
        } catch (MalformedURLException e) {
            throw new WebScrapeException("Cannot parse download url");
        } catch (URISyntaxException e) {
            throw new WebScrapeException("Cannot parse magnet uri");
        }

        DataSize size = ParseUtils.parseDataSize(table.selectFirst("td.torrents_small_size_data1").text());
        int seeders = Integer.parseInt(table.selectFirst("td.torrents_small_seeders_data1").text());
        int leechers = Integer.parseInt(table.selectFirst("td.torrents_small_leechers_data1").text());
        int downloads = Integer.parseInt(table.selectFirst("td.torrents_small_downloads_data1").text());

        return new TorrentHeader(category, title, torrentId, downloadLink,
                magnetLink, size, seeders, leechers, downloads);
    }

    private Category categoryFromImageUrl(String url) {
        switch (url) {
            case "./styles/tracker/imageset/cat_raw_small.png": return Category.RAW;
            case "./styles/tracker/imageset/cat_anime_small.png": return Category.ANIME;
            case "./styles/tracker/imageset/cat_hentai_small.png": return Category.HENTAI;
            case "./styles/tracker/imageset/cat_drama_small.png": return Category.DRAMA;
            case "./styles/tracker/imageset/cat_dvd_small.png": return Category.DVD;
            case "./styles/tracker/imageset/cat_hgame2_small.png": return Category.HENTAI_GAME;
            case "./styles/tracker/imageset/cat_manga_small.png": return Category.MANGA;
            case "./styles/tracker/imageset/cat_music_small.png": return Category.MUSIC;
            case "./styles/tracker/imageset/cat_musicvid_small.png": return Category.AMV;
            case "./styles/tracker/imageset/cat_noneng_small.png": return Category.NON_ENGLISH;
            case "./styles/tracker/imageset/cat_other_small.png": return Category.OTHER;
            default: throw new WebScrapeException("Unknown category image \"" + url + "\"");
        }
    }

    private int extractIdFromDownloadLink(String downloadLink) {
        Matcher matcher = DOWNLOAD_URL_PATTERN.matcher(downloadLink);

        if(!matcher.matches())
            throw new WebScrapeException("Cannot parse Download URL");

        return Integer.parseInt(matcher.group(1));
    }
}
