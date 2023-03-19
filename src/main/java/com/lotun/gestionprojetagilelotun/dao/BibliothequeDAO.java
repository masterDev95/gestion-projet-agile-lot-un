package com.lotun.gestionprojetagilelotun.dao;

import java.io.File;

import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;

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

    /**
     * Met à jour le fichier XML de la bibliothèque avec les données de la bibliothèque mise à jour.
     *
     * @param bibliotheque La bibliothèque mise à jour.
     */
    public static void updateBibliotheque(Bibliotheque bibliotheque) {
        try {
            // Enregistrer la liste mise à jour dans le fichier XML
            JAXBContext context = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(bibliotheque, new File("src/main/resources/com/lotun/gestionprojetagilelotun/Biblio.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}