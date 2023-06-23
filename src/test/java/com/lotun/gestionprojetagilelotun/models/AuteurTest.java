package com.lotun.gestionprojetagilelotun.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuteurTest {

    @Test
    void testNom() {
        // Créer un objet Auteur pour le test
        Auteur auteur = new Auteur();
        auteur.setNom("Doe");

        // Vérifier que getNom() renvoie la valeur attendue
        assertEquals("Doe", auteur.getNom());
    }

    @Test
    void testPrenom() {
        // Créer un objet Auteur pour le test
        Auteur auteur = new Auteur();
        auteur.setPrenom("John");

        // Vérifier que getPrenom() renvoie la valeur attendue
        assertEquals("John", auteur.getPrenom());
    }

}