package de.kaysubs.tracker.anirena.utils;

import de.kaysubs.tracker.anirena.AniRenaApi;
import de.kaysubs.tracker.anirena.model.SearchRequest;
import de.kaysubs.tracker.anirena.model.TorrentHeader;

import java.util.Iterator;

public class SearchIterator implements Iterator<TorrentHeader> {

    private final SearchRequest request;
    private TorrentHeader[] cache;
    private int index;

    public SearchIterator(SearchRequest request) {
        this.request = request;
    }

    private void verifyCache() {
        if(cache == null || (index >= cache.length && cache.length != 0)) {
            cache = AniRenaApi.getInstance().search(request);
            request.setOffset(request.getOffset().orElse(0) + cache.length);
            index = 0;
        }
    }

    @Override
    public boolean hasNext() {
        verifyCache();
        return index < cache.length;
    }

    @Override
    public TorrentHeader next() {
        verifyCache();
        return cache[index++];
    }
}
