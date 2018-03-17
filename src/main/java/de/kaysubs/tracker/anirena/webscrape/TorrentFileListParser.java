package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.model.DataSize;
import de.kaysubs.tracker.anirena.model.TorrentMeta;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TorrentFileListParser implements Parser<TorrentMeta.FileInfo[]> {

    @Override
    public TorrentMeta.FileInfo[] parsePage(Document page) {
        Element table = page.selectFirst("table");
        return table.select("tr").stream()
                .map(row -> {
                    String filename = row.selectFirst("div[title]").text();
                    DataSize size = ParseUtils.parseDataSize(row.selectFirst("td[style]").text());

                    return new TorrentMeta.FileInfo(filename, size);
                })
                .toArray(TorrentMeta.FileInfo[]::new);
    }

}
