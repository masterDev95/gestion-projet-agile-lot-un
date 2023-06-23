package com.lotun.gestionprojetagilelotun.dao;

import com.lotun.gestionprojetagilelotun.models.Auteur;

import java.sql.*;

public class AuteurDAO {
    private AuteurDAO() { }

    public static Auteur getAuteurFromDBById(int id) throws SQLException {
        Connection connection = ConnectionManager.getDbConnection();
        Auteur auteurRecupere = new Auteur();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM auteur WHERE idAuteur = " + id)) {
            // Traitement des résultats
            if (resultSet.next()) {
                // Lire les valeurs des colonnes
                auteurRecupere.setNom(resultSet.getString("nom"));
                auteurRecupere.setPrenom(resultSet.getString("prenom"));
            }
        } // Les ressources statement et resultSet seront automatiquement fermées ici, même en cas d'exception

        // Fermeture de la connexion
        connection.close();

        return auteurRecupere;
    }

    public static int insertAuteur(Auteur auteur, Connection connection) throws SQLException {
        String query = "INSERT INTO auteur (nom, prenom) VALUES (?, ?)";
        int auteurId = -1;

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, auteur.getNom());
            statement.setString(2, auteur.getPrenom());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    auteurId = generatedKeys.getInt(1);
                }

                generatedKeys.close();
            }
        } // La ressource statement sera automatiquement fermée ici, même en cas d'exception

        return auteurId;
    }


    public static int getAuteurId(Auteur auteur, Connection connection) throws SQLException {
        String query = "SELECT idAuteur FROM auteur WHERE nom = ? AND prenom = ?";
        int auteurId = -1;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, auteur.getNom());
            statement.setString(2, auteur.getPrenom());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                auteurId = resultSet.getInt("idAuteur");
            }

            resultSet.close();
        } // La ressource statement sera automatiquement fermée ici, même en cas d'exception

        return auteurId;
    }

}
