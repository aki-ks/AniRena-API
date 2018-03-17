package de.kaysubs.tracker.anirena.model;

public class Statistics {
    private final int totalTorrents;
    private final int totalDownloaders;
    private final int totalUploaders;
    private final int totalCompleted;
    private final DataSize totalTransferred;

    public Statistics(int totalTorrents, int totalDownloaders, int totalUploaders, int totalCompleted, DataSize totalTransferred) {
        this.totalTorrents = totalTorrents;
        this.totalDownloaders = totalDownloaders;
        this.totalUploaders = totalUploaders;
        this.totalCompleted = totalCompleted;
        this.totalTransferred = totalTransferred;
    }

    public int getTotalTorrents() {
        return totalTorrents;
    }

    public int getTotalDownloaders() {
        return totalDownloaders;
    }

    public int getTotalUploaders() {
        return totalUploaders;
    }

    public int getTotalCompleted() {
        return totalCompleted;
    }

    public DataSize getTotalTransferred() {
        return totalTransferred;
    }
}
