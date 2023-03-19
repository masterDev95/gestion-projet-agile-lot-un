package com.lotun.gestionprojetagilelotun.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;

import com.lotun.gestionprojetagilelotun.classes.Livre;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

/**
 * Classe permettant l'accès aux données de la bibliothèque stockées dans un fichier XML.
 */
public class BibliothequeDAO {
    /**
     * Récupère la bibliothèque à partir du fichier XML.
     *
     * @return La bibliothèque, ou null en cas d'erreur.
     */
    public static Bibliotheque getBibliotheque() {
        try {
            JAXBContext context = JAXBContext.newInstance(Bibliotheque.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File fichierXML = new File("src/main/resources/com/lotun/gestionprojetagilelotun/Biblio.xml");
            Bibliotheque bibliotheque = (Bibliotheque) unmarshaller.unmarshal(fichierXML);
            return bibliotheque;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}