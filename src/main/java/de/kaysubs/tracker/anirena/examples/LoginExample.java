package de.kaysubs.tracker.anirena.examples;

import de.kaysubs.tracker.anirena.AniRenaApi;
import de.kaysubs.tracker.anirena.AniRenaAuthApi;

import java.util.Scanner;

public class LoginExample {

    public static void main(String[] args) {
        login();
    }

    public static AniRenaAuthApi login() {
        Scanner sc = new Scanner(System.in);
        System.out.print("username: ");
        String username = sc.next();
        System.out.print("password: ");
        String password = sc.next();

        return AniRenaApi.getInstance().login(username, password);
    }

}
