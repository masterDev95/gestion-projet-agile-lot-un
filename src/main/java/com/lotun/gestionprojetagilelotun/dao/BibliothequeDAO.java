package com.lotun.gestionprojetagilelotun.dao;

import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;
import com.lotun.gestionprojetagilelotun.classes.Livre;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

    public static Bibliotheque getBibliothequeFromDB() throws SQLException {
        Connection connection = ConnectionManager.getDbConnection();
        Bibliotheque bibliotheque = new Bibliotheque();
        List<Livre> livresRecuperes = new ArrayList<>();

        // Création d'une instruction SQL
        Statement statement = connection.createStatement();

        // Exécution d'une requête
        ResultSet resultSet = statement.executeQuery("SELECT * FROM livre");

        // Traitement des résultats
        while (resultSet.next()) {
            // Lire les valeurs des colonnes
            var livre = new Livre();
            livre.setTitre(resultSet.getString("titre"));
            livre.setParution(resultSet.getInt("parution"));
            livre.setRangee(resultSet.getInt("rangee"));
            livre.setColonne(resultSet.getInt("colonne"));
            livre.setUrlImage(resultSet.getString("urlImage"));

            // Faire quelque chose avec les valeurs
            livresRecuperes.add(livre);
        }

        bibliotheque.setLivres(livresRecuperes);

        // Fermeture des ressources
        resultSet.close();
        statement.close();
        connection.close();

        return bibliotheque;
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