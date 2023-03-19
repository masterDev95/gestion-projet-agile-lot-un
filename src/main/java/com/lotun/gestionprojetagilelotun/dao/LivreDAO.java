package com.lotun.gestionprojetagilelotun.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;

import com.lotun.gestionprojetagilelotun.classes.Livre;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

/**
 * Classe permettant l'accès aux données de la bibliothèque stockées dans un fichier XML.
 */
public class LivreDAO {
    /**
     * Récupère la liste des livres de la bibliothèque à partir du fichier XML.
     *
     * @return La liste des livres de la bibliothèque, ou null en cas d'erreur.
     */
    static public List<Livre> getLivres() {
        try {
            List<Livre> livres = new ArrayList<Livre>();

            // Création du contexte JAXB et du unmarshaller
            JAXBContext context = JAXBContext.newInstance(Bibliotheque.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // Récupération du fichier XML de la bibliothèque et unmarshalling de son contenu dans l'objet Bibliotheque
            File fichierXML = new File("src/main/resources/com/lotun/gestionprojetagilelotun/Biblio.xml");
            Bibliotheque bibliotheque = (Bibliotheque) unmarshaller.unmarshal(fichierXML);

            // Récupération de la liste des livres de la bibliothèque
            for (Livre livre : bibliotheque.getLivres()) {
                livres.add(livre);
            }

            return livres;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}