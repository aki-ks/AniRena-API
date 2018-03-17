package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.exception.WebScrapeException;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TorrentUploadResponseParser implements Parser<Integer> {
    private final static Pattern ID_PATTERN = Pattern.compile("Torrent uploaded succesfully under ID ([0-9]+)");

    @Override
    public Integer parsePage(Document page) {
        String text = page.selectFirst("div.gbox.full").selectFirst("p").text();

        Matcher matcher = ID_PATTERN.matcher(text);
        if(matcher.matches()) {
            return Integer.parseInt(matcher.group(1));
        } else {
            throw new WebScrapeException("Cannot extract torrent id");
        }
    }
}
