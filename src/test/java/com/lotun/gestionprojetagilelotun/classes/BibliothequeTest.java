package com.lotun.gestionprojetagilelotun.classes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BibliothequeTest {

    @Test
    void testGetLivres() {
        // Créer une liste de livres pour la bibliothèque
        List<Livre> livres = new ArrayList<>();
        Livre livre1 = new Livre();
        Livre livre2 = new Livre();
        livres.add(livre1);
        livres.add(livre2);


        // Créer un objet Bibliotheque pour le test
        Bibliotheque bibliotheque = new Bibliotheque();
        bibliotheque.setLivres(livres);

        // Vérifier que getLivres() renvoie la liste des livres attendue
        assertEquals(livres, bibliotheque.getLivres());
    }

}