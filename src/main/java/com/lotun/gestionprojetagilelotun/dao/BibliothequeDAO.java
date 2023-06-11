package com.lotun.gestionprojetagilelotun.dao;

import com.lotun.gestionprojetagilelotun.classes.Auteur;
import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;
import com.lotun.gestionprojetagilelotun.classes.Livre;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


/**
 * Classe permettant l'accès aux données de la bibliothèque stockées dans un fichier XML.
 */
public class BibliothequeDAO {
    /**
     * Fichier utilisé pour récupérer et écrire les données
     */
    File fichierXML;

    /**
     * Cette méthode est un constructeur de la classe BibliothequeDAO qui prend en paramètre un objet de type File représentant le fichier XML qui sera utilisé pour stocker les informations de la bibliothèque.
     *
     * @param fichier : un objet de type File représentant le fichier XML utilisé pour stocker les informations de la bibliothèque.
     */
    public BibliothequeDAO(File fichier) {
        fichierXML = fichier;
    }

    public BibliothequeDAO(){}

    /**
     * Renvoie le fichier XML associé à l'instance de la classe BibliothequeDAO.
     * @return le fichier XML associé à l'instance de la classe BibliothequeDAO
     */
    public File getFichierXML() {
        return fichierXML;
    }

    /**
     * Modifie le fichier XML utilisé pour la sauvegarde et la lecture de la bibliothèque.
     * @param fichier le nouveau fichier XML à utiliser
     */
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

    public void addLivreBd(Livre livre, Auteur auteur) throws SQLException {
        Connection connection = ConnectionManager.getDbConnection();
        System.out.println("L'auteur a été inséré avec l'ID : " + livre.getAuteur().getNom());

        // Créer la requête SQL d'insertion de l'auteur
        String queryAuteur = "INSERT INTO auteur (nom, prenom) VALUES (?, ?)";

        // Créer un objet PreparedStatement pour exécuter la requête d'insertion de l'auteur
        PreparedStatement statementAuteur = connection.prepareStatement(queryAuteur, Statement.RETURN_GENERATED_KEYS);

        statementAuteur.setString(1, livre.getAuteur().getNom());
        statementAuteur.setString(2, livre.getAuteur().getPrenom());

        // Exécuter la requête d'insertion de l'auteur
        int rowsAffectedAuteur = statementAuteur.executeUpdate();

        int auteurId = -1;

        if (rowsAffectedAuteur > 0) {
            ResultSet generatedKeys = statementAuteur.getGeneratedKeys();
            if (generatedKeys.next()) {
                auteurId = generatedKeys.getInt(1);
            }
            generatedKeys.close();
        } else {
            System.out.println("L'insertion de l'auteur a échoué.");
        }

        statementAuteur.close();

        // Si l'insertion de l'auteur a réussi, insérer le livre avec l'ID de l'auteur
        if (auteurId != -1) {
            // Créer la requête SQL d'insertion du livre
            String queryLivre = "INSERT INTO livre (titre, idauteur, presentation, parution, colonne, rangee) VALUES (?, ?, ?, ?, ?, ?)";

            // Créer un objet PreparedStatement pour exécuter la requête d'insertion du livre
            PreparedStatement statementLivre = connection.prepareStatement(queryLivre);

            // Définir les valeurs des paramètres de la requête d'insertion du livre
            statementLivre.setString(1, livre.getTitre());
            statementLivre.setInt(2, auteurId);
            statementLivre.setString(3, livre.getPresentation());
            statementLivre.setInt(4, livre.getParution());
            statementLivre.setInt(5, livre.getColonne());
            statementLivre.setInt(6, livre.getRangee());

            // Exécuter la requête d'insertion du livre
            int rowsAffectedLivre = statementLivre.executeUpdate();

            if (rowsAffectedLivre > 0) {
                System.out.println("Insertion du livre réussie !");
            } else {
                System.out.println("L'insertion du livre a échoué.");
            }

            statementLivre.close();
        }

        // Fermer la connexion
        connection.close();
    }


}