package de.kaysubs.tracker.anirena.model;

public class TorrentFileInfo {
    private final String name;
    private final Category category;
    private final String comment;
    private final String mainTracker;
    private final String[] announceList;
    private final String info;
    private final long creationDate;
    private final String createdBy;
    private final String encoding;

    public TorrentFileInfo(String name, Category category, String comment, String mainTracker, String[] announceList,
                           String info, long creationDate, String createdBy, String encoding) {
        this.name = name;
        this.category = category;
        this.comment = comment;
        this.mainTracker = mainTracker;
        this.announceList = announceList;
        this.info = info;
        this.creationDate = creationDate;
        this.createdBy = createdBy;
        this.encoding = encoding;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getComment() {
        return comment;
    }

    public String getMainTracker() {
        return mainTracker;
    }

    public String[] getAnnounceList() {
        return announceList;
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
