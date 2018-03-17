package de.kaysubs.tracker.anirena.webscrape;

import org.jsoup.nodes.Document;

public interface Parser<T> {

    T parsePage(Document page);

}
