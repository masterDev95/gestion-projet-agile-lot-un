package com.lotun.gestionprojetagilelotun.dao;

import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;
import com.lotun.gestionprojetagilelotun.classes.Livre;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public BibliothequeDAO() {
    }

    /**
     * Renvoie le fichier XML associé à l'instance de la classe BibliothequeDAO.
     *
     * @return le fichier XML associé à l'instance de la classe BibliothequeDAO
     */
    public File getFichierXML() {
        return fichierXML;
    }

    /**
     * Modifie le fichier XML utilisé pour la sauvegarde et la lecture de la bibliothèque.
     *
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

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM livre");

            while (resultSet.next()) {
                var livre = new Livre();
                livre.setId(resultSet.getInt("idlivre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setParution(resultSet.getInt("parution"));
                livre.setRangee(resultSet.getInt("rangee"));
                livre.setColonne(resultSet.getInt("colonne"));
                livre.setUrlImage(resultSet.getString("urlImage"));
                livre.setAuteur(AuteurDAO.getAuteurFromDBById(resultSet.getInt("auteurId")));
                livre.setPresentation(resultSet.getString("presentation"));
                livre.setEtat(resultSet.getBoolean("etat"));
                livresRecuperes.add(livre);
            }

        // Traitement des résultats
        while (resultSet.next()) {
            // Lire les valeurs des colonnes
            var livre = new Livre();
            livre.setId(resultSet.getInt("idlivre"));
            livre.setTitre(resultSet.getString("titre"));
            livre.setParution(resultSet.getInt("parution"));
            livre.setRangee(resultSet.getInt("rangee"));
            livre.setColonne(resultSet.getInt("colonne"));
            livre.setUrlImage(resultSet.getString("urlImage"));
            livre.setAuteur(AuteurDAO.getAuteurFromDBById(resultSet.getInt("auteurId")));
            livre.setPresentation(resultSet.getString("presentation"));
            livre.setEtat(resultSet.getBoolean("etat"));

            // Faire quelque chose avec les valeurs
            livresRecuperes.add(livre);
        }

            resultSet.close();
        } // La ressource statement sera automatiquement fermée ici, même en cas d'exception


        bibliotheque.setLivres(livresRecuperes);
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

    public static void ajoutLivre(Livre livre) throws SQLException {
        Connection connection = ConnectionManager.getDbConnection();
        // Réécrire la liste des livres dans la table "livre"
        // Vérifier si l'auteur existe dans la table "auteur"
        int auteurId = AuteurDAO.getAuteurId(livre.getAuteur(), connection);

        // Si l'auteur n'existe pas, l'insérer dans la table "auteur"
        if (auteurId == -1) {
            auteurId = AuteurDAO.insertAuteur(livre.getAuteur(), connection);
        }
        // Insérer le livre dans la table "livre" avec l'ID de l'auteur
        if(livre.getId() != 0){
            modifierLivre(livre, auteurId, connection);

        }else{
            insertLivre(livre, auteurId, connection);
        }



        // Fermer la connexion
        connection.close();
    }

    private static void insertLivre(Livre livre, int auteurId, Connection connection) throws SQLException {
        String query = "INSERT INTO livre (titre, auteurId, presentation, parution, colonne, rangee, etat, urlImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, livre.getTitre());
            statement.setInt(2, auteurId);
            statement.setString(3, livre.getPresentation());
            statement.setInt(4, livre.getParution());
            statement.setInt(5, livre.getColonne());
            statement.setInt(6, livre.getRangee());
            statement.setBoolean(7, livre.getEtat());
            statement.setString(8, livre.getUrlImage());
            statement.executeUpdate();
        } // La ressource statement sera automatiquement fermée ici, même en cas d'exception
    }

    private static void modifierLivre(Livre livre, int auteurId, Connection connection) throws SQLException {
        String updateQuery = "UPDATE livre SET titre = ?, auteurId = ?, presentation = ?, parution = ?, colonne = ?, rangee = ?, etat = ?, urlImage = ? WHERE idlivre = ?";
        PreparedStatement statement = connection.prepareStatement(updateQuery);
        statement.setString(1, livre.getTitre());
        statement.setInt(2, auteurId);
        statement.setString(3, livre.getPresentation());
        statement.setInt(4, livre.getParution());
        statement.setInt(5, livre.getColonne());
        statement.setInt(6, livre.getRangee());
        statement.setBoolean(7, livre.getEtat());
        statement.setString(8, livre.getUrlImage());
        statement.setInt(9,livre.getId());
        statement.executeUpdate();
        statement.close();
    }

    public static void supLivre(int id) throws SQLException {
        Connection connection = ConnectionManager.getDbConnection();
        String query = "DELETE FROM livre WHERE idlivre = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, id);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
}