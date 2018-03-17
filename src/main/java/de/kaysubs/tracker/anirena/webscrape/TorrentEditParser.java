package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.model.Category;
import de.kaysubs.tracker.anirena.model.EditRequest;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;

import java.util.List;

public class TorrentEditParser implements Parser<EditRequest> {
    @Override
    public EditRequest parsePage(Document page) {
        List<Connection.KeyVal> form = ((FormElement)page.selectFirst("form#edit")).formData();

        return new EditRequest(
                Integer.parseInt(ParseUtils.getFormValue(form, "id")),
                ParseUtils.getFormValue(form, "n"),
                Integer.valueOf(ParseUtils.getFormValue(form, "d")) == 1,
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
