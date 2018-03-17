package de.kaysubs.tracker.anirena.model;

import de.kaysubs.tracker.anirena.webscrape.TorrentAnnounceListParser;
import de.kaysubs.tracker.anirena.webscrape.TorrentFileListParser;
import org.jsoup.Jsoup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.OptionalInt;

public class TorrentMeta {
    private String UPLOADED;
    private String CREATED_BY;
    private String TORRENT_CREATED;
    private String OWNER_NAME;
    private String COMMENT;
    private String INFO_HASH;
    private String CRC;
    private String FILE_LIST;
    private String ANNOUNCE_LIST;

    public static class FileInfo {
        private final String path;
        private final DataSize size;

        public FileInfo(String path, DataSize size) {
            this.path = path;
            this.size = size;
        }

        public String getPath() {
            return path;
        }

        public DataSize getSize() {
            return size;
        }
    }

    public static class Tracker {
        private final String url;
        private final OptionalInt seeders;
        private final OptionalInt leechers;
        private final OptionalInt downloaded;

        public Tracker(String url, OptionalInt seeders, OptionalInt leechers, OptionalInt downloaded) {
            this.url = url;
            this.seeders = seeders;
            this.leechers = leechers;
            this.downloaded = downloaded;
        }

        public String getUrl() {
            return url;
        }

        public OptionalInt getSeeders() {
            return seeders;
        }

        public OptionalInt getLeechers() {
            return leechers;
        }

        public OptionalInt getDownloaded() {
            return downloaded;
        }
    }

    public LocalDateTime getUploadDate() {
        return LocalDateTime.parse(UPLOADED, DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    public LocalDateTime getCreationDate() {
        return LocalDateTime.parse(TORRENT_CREATED, DateTimeFormatter.RFC_1123_DATE_TIME);
    }

    /**
     * Torrent-client used to create this torrent
     */
    public String getCreatedBy() {
        return CREATED_BY;
    }

    /**
     * User that uploaded the torrent
     */
    public String getOwnerName() {
        return OWNER_NAME;
    }

    public String getComment() {
        return COMMENT;
    }

    public String getInfoHash() {
        return INFO_HASH;
    }

    public Optional<String> getCRC() {
        return CRC == null || CRC.isEmpty() ? Optional.empty() : Optional.ofNullable(CRC);
    }

    public FileInfo[] getFileList() {
        return new TorrentFileListParser().parsePage(Jsoup.parseBodyFragment(FILE_LIST));
    }

    public Tracker[] getAnnounceList() {
        return ANNOUNCE_LIST.isEmpty() ? new Tracker[0]:
                new TorrentAnnounceListParser().parsePage(Jsoup.parseBodyFragment(ANNOUNCE_LIST));
    }
}
