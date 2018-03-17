package de.kaysubs.tracker.anirena.model;

import java.io.File;
import java.util.Optional;
import java.util.OptionalLong;

public class UploadRequest {
    private File torrentFile;
    private Optional<String> name = Optional.empty();
    private Optional<Category> category = Optional.empty();
    private Optional<String> comment = Optional.empty();
    private Optional<String> mainTracker = Optional.empty();
    private Optional<String[]> announceList = Optional.empty();
    private OptionalLong creationDate = OptionalLong.empty();
    private Optional<String> createdBy = Optional.empty();

    public UploadRequest(String torrentFile) {
        this(new File(torrentFile));
    }

    public UploadRequest(File torrentFile) {
        this.torrentFile = torrentFile;
    }

    public File getTorrentFile() {
        return torrentFile;
    }

    public UploadRequest setTorrentFile(File torrentFile) {
        this.torrentFile = torrentFile;
        return this;
    }

    public Optional<String> getName() {
        return name;
    }

    public UploadRequest setName(String name) {
        this.name = Optional.ofNullable(name);
        return this;
    }

    public Optional<Category> getCategory() {
        return category;
    }

    public UploadRequest setCategory(Category category) {
        this.category = Optional.ofNullable(category);
        return this;
    }

    public Optional<String> getComment() {
        return comment;
    }

    public UploadRequest setComment(String comment) {
        this.comment = Optional.ofNullable(comment);
        return this;
    }

    public Optional<String> getMainTracker() {
        return mainTracker;
    }

    public UploadRequest setMainTracker(String mainTracker) {
        this.mainTracker = Optional.ofNullable(mainTracker);
        return this;
    }

    public Optional<String[]> getAnnounceList() {
        return announceList;
    }

    public UploadRequest setAnnounceList(String[] announceList) {
        this.announceList = Optional.ofNullable(announceList);
        return this;
    }

    public OptionalLong getCreationDate() {
        return creationDate;
    }

    public UploadRequest setCreationDate(Long creationDate) {
        this.creationDate = creationDate == null ? OptionalLong.empty() : OptionalLong.of(creationDate);
        return this;
    }

    public UploadRequest setCreatedBy(String createdBy) {
        this.createdBy = Optional.ofNullable(createdBy);
        return this;
    }

    /**
     * Torrent client used to create this torrent
     */
    public Optional<String> getCreatedBy() {
        return createdBy;
    }
}
