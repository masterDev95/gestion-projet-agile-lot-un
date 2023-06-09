package com.lotun.gestionprojetagilelotun.dao;

import java.sql.*;

public class ConnectionManager {
    public static Connection getDbConnection() throws SQLException {
        // Établissement de la connexion à la base de données
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliothèque", "root", "root");
    }
}
