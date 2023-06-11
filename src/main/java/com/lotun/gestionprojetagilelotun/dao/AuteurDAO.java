package com.lotun.gestionprojetagilelotun.dao;

import com.lotun.gestionprojetagilelotun.classes.Auteur;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
}
