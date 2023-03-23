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
    File fichierXML;

    public BibliothequeDAO(File fichier) {
        fichierXML = fichier;
    }

    public File getFichierXML() {
        return fichierXML;
    }

    public void setFichierXML(File fichier) {
        fichierXML = fichier;
    }

    /**
     * Récupère la bibliothèque à partir du fichier XML.
     *
     * @return La bibliothèque, ou null en cas d'erreur.
     */
    public Bibliotheque getBibliotheque() {
        try {
            JAXBContext context = JAXBContext.newInstance(Bibliotheque.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Bibliotheque) unmarshaller.unmarshal(fichierXML);
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
    public void updateBibliotheque(Bibliotheque bibliotheque) {
        try {
            // Enregistrer la liste mise à jour dans le fichier XML
            JAXBContext context = JAXBContext.newInstance(Bibliotheque.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(bibliotheque, fichierXML);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}