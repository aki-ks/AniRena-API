package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.model.TorrentMeta;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.OptionalInt;

public class TorrentAnnounceListParser implements Parser<TorrentMeta.Tracker[]> {
    @Override
    public TorrentMeta.Tracker[] parsePage(Document page) {
        return page.selectFirst("table").select("tr").stream()
                .skip(1)
                .map(this::parseRow)
                .toArray(TorrentMeta.Tracker[]::new);
    }

    public TorrentMeta.Tracker parseRow(Element row) {
        Element[] cells = row.select("td").toArray(new Element[0]);

        String url = cells[0].selectFirst("div").text();
        OptionalInt seeders = parseInt(cells[1].text());
        OptionalInt leechers = parseInt(cells[2].text());
        OptionalInt downloaded = parseInt(cells[3].text());

        return new TorrentMeta.Tracker(url, seeders, leechers, downloaded);
    }

    private OptionalInt parseInt(String text) {
        return text.equals("---") ? OptionalInt.empty() :
                OptionalInt.of(Integer.parseInt(text));
    }
}
