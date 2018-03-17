package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.model.Category;
import de.kaysubs.tracker.anirena.model.TorrentFileInfo;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.util.List;

public class TorrentFileInfoParser implements Parser<TorrentFileInfo> {
    @Override
    public TorrentFileInfo parsePage(Document page) {
        List<Connection.KeyVal> form = ((FormElement)page.selectFirst("form#upload")).formData();

        return new TorrentFileInfo(
                ParseUtils.getFormValue(form, "n"),
                Category.fromId(Integer.parseInt(ParseUtils.getFormValue(form, "t"))),
                ParseUtils.getFormValue(form, "c"),
                ParseUtils.getFormValue(form, "a1"),
                ParseUtils.parseTrackerList(ParseUtils.getFormValue(form, "a2")),
                ParseUtils.getFormValue(form, "info"),
                Long.parseLong(ParseUtils.getFormValue(form, "creation_date")),
                ParseUtils.getFormValue(form, "creation_by"),
                ParseUtils.getFormValue(form, "encoding")
        );
    }
}
