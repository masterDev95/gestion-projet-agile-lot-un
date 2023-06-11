package com.lotun.gestionprojetagilelotun.dao;

import com.lotun.gestionprojetagilelotun.classes.Auteur;

import java.sql.*;

public class AuteurDAO {
    public static Auteur getAuteurFromDBById(int id) throws SQLException {
        Connection connection = ConnectionManager.getDbConnection();
        Auteur auteurRecupere = new Auteur();

        // Création d'une instruction SQL
        Statement statement = connection.createStatement();

        // Exécution d'une requête
        ResultSet resultSet = statement.executeQuery("SELECT * FROM auteur WHERE idAuteur = " + id);

        // Traitement des résultats
        if (resultSet.next()) {
            // Lire les valeurs des colonnes
            auteurRecupere.setNom(resultSet.getString("nom"));
            auteurRecupere.setPrenom(resultSet.getString("prenom"));
        }

        // Fermeture des ressources
        resultSet.close();
        statement.close();
        connection.close();

        return auteurRecupere;
    }

    public static int insertAuteur(Auteur auteur, Connection connection) throws SQLException {
        String query = "INSERT INTO auteur (nom, prenom) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, auteur.getNom());
        statement.setString(2, auteur.getPrenom());
        int rowsAffected = statement.executeUpdate();
        int auteurId = -1;
        if (rowsAffected > 0) {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                auteurId = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        }
        statement.close();
        return auteurId;
    }

    public static int getAuteurId(Auteur auteur, Connection connection) throws SQLException {
        String query = "SELECT idAuteur FROM auteur WHERE nom = ? AND prenom = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, auteur.getNom());
        statement.setString(2, auteur.getPrenom());
        ResultSet resultSet = statement.executeQuery();
        int auteurId = -1;
        if (resultSet.next()) {
            auteurId = resultSet.getInt("idAuteur");
        }
        resultSet.close();
        statement.close();
        return auteurId;
    }
}
