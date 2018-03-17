package de.kaysubs.tracker.anirena.webscrape;

import de.kaysubs.tracker.anirena.exception.WebScrapeException;
import de.kaysubs.tracker.anirena.model.DataSize;
import org.jsoup.Connection;

import java.math.BigDecimal;
import java.util.List;

public class ParseUtils {
    public static DataSize parseDataSize(String string) {
        String[] split = string.split(" ");
        if(split.length != 2)
            throw new WebScrapeException("Cannot parse data size");

        DataSize.DataUnit unit = parseDataUnit(split[1]);

        BigDecimal size = new BigDecimal(split[0]);

        while(size.stripTrailingZeros().scale() > 0) {
            if(unit == DataSize.DataUnit.BYTE)
                throw new WebScrapeException("Cannot parse fractional byte size " + string);

            unit = DataSize.DataUnit.values()[unit.ordinal() - 1];
            size = size.movePointRight(3);
        }

        return new DataSize(size.intValueExact(), unit);
    }

    private static DataSize.DataUnit parseDataUnit(String unitName) {
        for(DataSize.DataUnit unit : DataSize.DataUnit.values())
            if(unit.getUnitName().equalsIgnoreCase(unitName))
                return unit;

        throw new WebScrapeException("Unknown unit size \"" + unitName + "\"");
    }

    public static String getFormValue(List<Connection.KeyVal> vals, String key) {
        for(Connection.KeyVal val : vals)
            if(val.key().equals(key))
                return val.value();

        throw new IllegalArgumentException("Expected form value for key \"" + key + "\"");
    }

    public static String[] parseTrackerList(String s) {
        String[] trackers = s.split("\n");
        return trackers.length == 1 && trackers[0].isEmpty() ? new String[0] : trackers;
    }
}
