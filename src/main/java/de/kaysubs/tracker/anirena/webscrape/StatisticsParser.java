package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.model.DataSize;
import de.kaysubs.tracker.anirena.model.Statistics;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class StatisticsParser implements Parser<Statistics> {
    @Override
    public Statistics parsePage(Document page) {
        Element[] vars = page.selectFirst("div#topbar")
                .select("big b").toArray(new Element[0]);

        int torrentCount = Integer.parseInt(vars[0].text());
        int downloaders = Integer.parseInt(vars[1].text());
        int uploaders = Integer.parseInt(vars[2].text());
        int completed = Integer.parseInt(vars[3].text());
        DataSize transferred = ParseUtils.parseDataSize(vars[4].text());

        return new Statistics(torrentCount, downloaders, uploaders, completed, transferred);
    }
}
