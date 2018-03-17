package de.kaysubs.tracker.anirena;

import de.kaysubs.tracker.anirena.exception.PermissionException;
import de.kaysubs.tracker.anirena.exception.WebScrapeException;
import de.kaysubs.tracker.anirena.model.EditRequest;
import de.kaysubs.tracker.anirena.model.UploadRequest;
import de.kaysubs.tracker.common.exception.HttpException;

import java.util.function.Consumer;

/**
 * Api calls that require authentication.
 */
public interface AniRenaAuthApi extends AniRenaApi {
    /**
     * Upload a torrent.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @return torrentId
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    int uploadTorrent(UploadRequest request);

    /**
     * Delete a torrent uploaded by yourself.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws PermissionException you do not own this torrent
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    void deleteTorrent(int torrentId);

    /**
     * Create a new edit request that contains the current torrent values.
     * It can be edited and then send to the server.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    EditRequest getEditRequest(int torrentId);

    /**
     * Send a edit request.
     * Edit request can be created through the "getEditRequest" call.
     *
     * Since this api call is based on parsing webpages, it might break anytime.
     *
     * @throws PermissionException you do not own this torrent
     * @throws WebScrapeException error while parsing webpage
     * @throws HttpException networking error
     */
    void editTorrent(EditRequest request);

    default void editTorrent(int torrentId, Consumer<EditRequest> f) {
        EditRequest request = getEditRequest(torrentId);
        f.accept(request);
        editTorrent(request);
    }
}
