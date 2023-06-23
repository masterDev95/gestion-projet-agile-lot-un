package com.lotun.gestionprojetagilelotun.models;

public class Session {
    private static String username;
    private static boolean isGerant;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Session.username = username;
    }

    public static boolean isGerant() {
        return isGerant;
    }

    public static void setGerant(boolean isGerant) {
        Session.isGerant = isGerant;
    }
}

