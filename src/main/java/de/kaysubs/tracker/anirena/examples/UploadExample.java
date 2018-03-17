package de.kaysubs.tracker.anirena.examples;

import de.kaysubs.tracker.anirena.AniRenaAuthApi;
import de.kaysubs.tracker.anirena.model.Category;
import de.kaysubs.tracker.anirena.model.UploadRequest;

import java.io.File;
import java.util.Scanner;

public class UploadExample {

    public static void main(String[] args) {
        AniRenaAuthApi api = LoginExample.login();

        Scanner sc = new Scanner(System.in);
        System.out.print("path to .torrent file: ");
        File file = new File(sc.next());
        System.out.println();

        System.out.print("Creating example torrent... ");
        int torrentId = api.uploadTorrent(new UploadRequest(file)
                .setName("API Test torrent")
                .setCategory(Category.OTHER)
                .setComment("Random test torrent created through 'k sub AniRena api"));
        System.out.println("id = " + torrentId);

        System.out.print("Editing torrent... ");
        api.editTorrent(torrentId, e -> e.setName("Edited torrent"));
        System.out.println("Done");

        System.out.print("Deleting torrent again... ");
        api.deleteTorrent(torrentId);
        System.out.println("Done");
    }

}
