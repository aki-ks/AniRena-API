package de.kaysubs.tracker.anirena;

import de.kaysubs.tracker.anirena.model.*;
import de.kaysubs.tracker.anirena.exception.*;
import de.kaysubs.tracker.common.exception.HttpException;

public interface AniRenaApi {
    static AniRenaApi getInstance() {
        return AniRenaApiImpl.getInstance();
    }

    /**
     * Get statistics abouts all users like the total amount of downloads.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    Statistics getStatistics();

    /**
     * Show/Search in the torrent index.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    TorrentHeader[] search(SearchRequest request);

    /**
     * Get additional informations about a torrent.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    TorrentMeta getTorrentMeta(int torrentId);

    /**
     * Login with username and password.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws LoginException login failed
     * @throws HttpException networking error
     */
    AniRenaAuthApi login(String username, String password);

    /**
     * Anonymously upload a torrent.
     *
     * Prefer to login before you upload.
     * Otherwise you will *NOT* be able to edit or delete the torrent.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @return torrentId
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    int uploadTorrentAnonymously(UploadRequest request);
}
