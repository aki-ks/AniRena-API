package de.kaysubs.tracker.anirena.model;

import java.net.URI;
import java.net.URL;

public class TorrentHeader {
    private final Category category;
    private final String title;
    private final int torrentId;
    private final URL downloadLink;
    private final URI magnetLink;
    private final DataSize size;
    private final int seeders;
    private final int leechers;
    private final int downloads;

    public TorrentHeader(Category category, String title, int torrentId, URL downloadLink, URI magnetLink,
                         DataSize size, int seeders, int leechers, int downloads) {
        this.category = category;
        this.title = title;
        this.torrentId = torrentId;
        this.downloadLink = downloadLink;
        this.magnetLink = magnetLink;
        this.size = size;
        this.seeders = seeders;
        this.leechers = leechers;
        this.downloads = downloads;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public int getTorrentId() {
        return torrentId;
    }

    public URL getDownloadLink() {
        return downloadLink;
    }

    public URI getMagnetLink() {
        return magnetLink;
    }

    public DataSize getSize() {
        return size;
    }

    public int getSeeders() {
        return seeders;
    }

    public int getLeechers() {
        return leechers;
    }

    public int getDownloads() {
        return downloads;
    }
}
