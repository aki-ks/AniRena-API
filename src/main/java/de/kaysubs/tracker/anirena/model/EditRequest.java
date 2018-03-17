package de.kaysubs.tracker.anirena.model;

public class EditRequest {
    private final int torrentId;
    private String name;
    private boolean dht;
    private Category category;
    private String comment;
    private String mainTracker;
    private String[] announceList;
    private final String info;
    private final long creationDate;
    private final String createdBy;
    private final String encoding;

    public EditRequest(int torrentId, String name, boolean dht, Category category, String comment, String mainTracker,
                       String[] announceList, String info, long creationDate, String createdBy, String encoding) {
        this.torrentId = torrentId;
        this.name = name;
        this.dht = dht;
        this.category = category;
        this.comment = comment;
        this.mainTracker = mainTracker;
        this.announceList = announceList;
        this.info = info;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.encoding = encoding;
    }

    public int getTorrentId() {
        return torrentId;
    }

    public String getName() {
        return name;
    }

    public EditRequest setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isDht() {
        return dht;
    }

    /**
     * WARNING - This will alter the torrent hash
     * and thereby require a reupload/reseed.
     */
    public EditRequest setDht(boolean dht) {
        this.dht = dht;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public EditRequest setCategory(Category category) {
        this.category = category;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public EditRequest setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getMainTracker() {
        return mainTracker;
    }

    public EditRequest setMainTracker(String mainTracker) {
        this.mainTracker = mainTracker;
        return this;
    }

    public String[] getAnnounceList() {
        return announceList;
    }

    public EditRequest setAnnounceList(String[] announceList) {
        this.announceList = announceList;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getEncoding() {
        return encoding;
    }
}
