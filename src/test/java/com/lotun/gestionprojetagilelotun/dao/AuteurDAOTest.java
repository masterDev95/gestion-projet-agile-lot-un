package com.lotun.gestionprojetagilelotun.dao;

import com.lotun.gestionprojetagilelotun.models.Auteur;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class AuteurDAOTest {

    @Test
    void getAuteurFromDBById() throws SQLException {
        Auteur auteur = AuteurDAO.getAuteurFromDBById(2);
        Auteur auteurAttendu = new Auteur();
        auteurAttendu.setNom("SUDROT");
        auteurAttendu.setPrenom("KELVIN");
        assertTrue(auteurAttendu.getNom().equals(auteur.getNom()) && auteurAttendu.getPrenom().equals(auteur.getPrenom()));
    }

    @Test
    void testInsertAuteur() throws SQLException {
        // Créer une connexion à une base de données (pour les besoins du test)
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bibliothèque", "root", "root");

        // Créer un objet Auteur pour le test
        Auteur auteur = new Auteur();
        auteur.setNom("moi");
        auteur.setPrenom("moi");
        // Appeler la fonction à tester
        int auteurId = AuteurDAO.insertAuteur(auteur, connection);

        // Vérifier le résultat attendu
        assertTrue(auteurId > 0);

        // Fermer la connexion à la base de données (après le test)
        connection.close();
    }

    @Test
    void getAuteurId() {
    }
}