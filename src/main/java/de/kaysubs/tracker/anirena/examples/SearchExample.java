package de.kaysubs.tracker.anirena.examples;

import de.kaysubs.tracker.anirena.AniRenaApi;
import de.kaysubs.tracker.anirena.model.Category;
import de.kaysubs.tracker.anirena.model.SearchRequest;
import de.kaysubs.tracker.anirena.model.TorrentHeader;
import de.kaysubs.tracker.anirena.model.TorrentMeta;
import de.kaysubs.tracker.anirena.utils.SearchIterator;

import java.util.Arrays;
import java.util.Iterator;

public class SearchExample {

    public static void printStartPage() {
        TorrentHeader[] torrents = AniRenaApi.getInstance().search(new SearchRequest());

        System.out.println("Here are the latest uploaded torrents:");
        for (TorrentHeader torrent : torrents)
            System.out.println(torrent.getTitle());
    }

    public static void printLatestTorrentsInACategory() {
        TorrentHeader[] torrents = AniRenaApi.getInstance().search(new SearchRequest()
                .setCategory(Category.ANIME));

        System.out.println("Here are the latest anime torrents:");
        for (TorrentHeader torrent : torrents)
            System.out.println(torrent.getTitle());
    }

    public static void search() {
        TorrentHeader[] torrents = AniRenaApi.getInstance().search(new SearchRequest()
                .setTerm("one piece"));

        System.out.println("Here are some torrents that contain \"one piece\" in their title:");
        for (TorrentHeader torrent : torrents)
            System.out.println(torrent.getTitle());
    }

    public static void iterateOverSearchResults() {
        Iterator<TorrentHeader> torrents = new SearchIterator(new SearchRequest()
                .setTerm("one pieces"));

        System.out.println("Here are *ALL* torrents that contain \"one piece\" in their title:");
        torrents.forEachRemaining(torrent ->
                System.out.println(torrent.getTitle()));
    }

    public static void printDetailedInformation() {
        TorrentHeader latestTorrent = AniRenaApi.getInstance().search(new SearchRequest())[0];
        System.out.println("torrent id: " + latestTorrent.getTorrentId());
        System.out.println("title: " + latestTorrent.getTitle());
        System.out.println("category: " + latestTorrent.getCategory());
        System.out.println("size: " + latestTorrent.getSize());
        System.out.println("seeders: " + latestTorrent.getSeeders());
        System.out.println("leechers: " + latestTorrent.getLeechers());
        System.out.println("downloads: " + latestTorrent.getDownloads());
        System.out.println("download link: " + latestTorrent.getDownloadLink());
        System.out.println("magnet link: " + latestTorrent.getMagnetLink());

        // Fetch additional informations
        TorrentMeta meta = AniRenaApi.getInstance().getTorrentMeta(latestTorrent.getTorrentId());
        System.out.println("crc: " + meta.getCRC());
        System.out.println("comment: " + meta.getComment());
        System.out.println("creation date: " + meta.getCreationDate());
        System.out.println("created by torrent client: " + meta.getCreatedBy());
        System.out.println("hash: " + meta.getInfoHash());
        System.out.println("uploader name: " + meta.getOwnerName());
        System.out.println("announce list: ");
        Arrays.stream(meta.getAnnounceList())
                .forEach(e -> {
                    System.out.println(e.getUrl());
                    System.out.println("seeders: " + e.getSeeders());
                    System.out.println("leechers: " + e.getLeechers());
                    System.out.println("downloaded: " + e.getDownloaded());
                });

        System.out.println("files:");
        Arrays.stream(meta.getFileList())
                .forEach(e -> System.out.println(e.getPath() + " (" + e.getSize() + ")"));
    }
}
