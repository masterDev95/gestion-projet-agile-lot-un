package com.lotun.gestionprojetagilelotun.classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuteurTest {

    @Test
    void testGetNom() {
        // Créer un objet Auteur pour le test
        Auteur auteur = new Auteur();
        auteur.setNom("Doe");

        // Vérifier que getNom() renvoie la valeur attendue
        assertEquals("Doe", auteur.getNom());
    }

    @Test
    void testSetNom() {
        // Créer un objet Auteur pour le test
        Auteur auteur = new Auteur();

        // Appeler setNom() avec une valeur
        auteur.setNom("Doe");

        // Vérifier que la valeur de nom a été modifiée
        assertEquals("Doe", auteur.getNom());
    }

    @Test
    void testGetPrenom() {
        // Créer un objet Auteur pour le test
        Auteur auteur = new Auteur();
        auteur.setPrenom("John");

        // Vérifier que getPrenom() renvoie la valeur attendue
        assertEquals("John", auteur.getPrenom());
    }

    @Test
    void testSetPrenom() {
        // Créer un objet Auteur pour le test
        Auteur auteur = new Auteur();

        // Appeler setPrenom() avec une valeur
        auteur.setPrenom("John");

        // Vérifier que la valeur de prenom a été modifiée
        assertEquals("John", auteur.getPrenom());
    }
}