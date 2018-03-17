package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.exception.PermissionException;
import de.kaysubs.tracker.anirena.exception.WebScrapeException;
import org.jsoup.nodes.Document;

public class CheckEditResponse implements Parser<Void> {
    @Override
    public Void parsePage(Document page) {
        String text = page.selectFirst("div.gbox.full").selectFirst("p").text();

        if(text.startsWith("Updated torrent ID")) {
            return null;
        } else if(text.equals("Access denied !")) {
            throw new PermissionException();
        } else {
            throw new WebScrapeException("Unknown Response: \"" + text + "\"");
        }
    }
}
