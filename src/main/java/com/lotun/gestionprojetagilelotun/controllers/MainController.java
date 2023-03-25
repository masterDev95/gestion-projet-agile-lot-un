package com.lotun.gestionprojetagilelotun.controllers;

import com.lotun.gestionprojetagilelotun.classes.Auteur;
import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;
import com.lotun.gestionprojetagilelotun.classes.Livre;
import com.lotun.gestionprojetagilelotun.dao.BibliothequeDAO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Contrôleur principal pour la gestion de livres.
 */
public class MainController {
    /**
     * Tableau des livres.
     */
    @FXML
    private TableView<Livre> tableViewLivres;
    /**
     * Champ du titre du livre.
     */
    @FXML
    private TextField champTitre;
    /**
     * Champ du prénom de l'auteur du livre.
     */
    @FXML
    private TextField champPrenomAuteur;
    /**
     * Champ du nom de l'auteur du livre.
     */
    @FXML
    private TextField champNomAuteur;
    /**
     * Champ de la présentation du livre.
     */
    @FXML
    private TextField champPresentation;
    /**
     * Champ de la parution du livre.
     */
    @FXML
    private TextField champParution;
    /**
     * Champ de la colonne du livre dans le tableau.
     */
    @FXML
    private TextField champColonne;
    /**
     * Champ de la rangée du livre dans le tableau.
     */
    @FXML
    private TextField champRangee;

    /**
     * Colonne du titre du livre dans le tableau.
     */
    @FXML
    private TableColumn<Livre, String> colTitre;

    /**
     * Colonne de l'auteur du livre dans le tableau.
     */
    @FXML
    private TableColumn<Livre, String> colAuteur;

    /**
     * Colonne de la présentation du livre dans le tableau.
     */
    @FXML
    private TableColumn<Livre, String> colPresentation;

    /**
     * Colonne de la parution du livre dans le tableau.
     */
    @FXML
    private TableColumn<Livre, String> colParution;

    /**
     * Colonne de la colonne du livre dans le tableau.
     */
    @FXML
    private TableColumn<Livre, String> colColonne;

    /**
     * Colonne de la rangée du livre dans le tableau.
     */
    @FXML
    private TableColumn<Livre, String> colRangee;

    /**
     * Liste des livres de la bibliothèque.
     */
    @FXML
    private List<Livre> listesLivres;

    /**
     * Vue image de couverture du livre sélectionné dans le tableau.
     */
    @FXML
    private ImageView bookCoverImageView;

    /**
     * Accès aux opérations sur la base de données.
     */
    private BibliothequeDAO dao;

    /**
     * Méthode d'initialisation du contrôleur.
     * Initialise le tableau de livres et définit les colonnes.
     */
    @FXML
    private void initialize() {
        initializeTableView();
    }

    /**
     * Initialise le TableView avec les données de la bibliothèque et configure les cellules de chaque colonne.
     */
    @FXML
    private void initializeTableView() {
        // Configurer chaque colonne avec sa propre PropertyValueFactory pour lier les données aux cellules
        colTitre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        colPresentation.setCellValueFactory(new PropertyValueFactory<>("presentation"));
        colParution.setCellValueFactory(new PropertyValueFactory<>("parution"));
        colColonne.setCellValueFactory(new PropertyValueFactory<>("colonne"));
        colRangee.setCellValueFactory(new PropertyValueFactory<>("rangee"));

        // Configurer la colonne "Auteur" pour afficher le nom et prénom de l'auteur
        colAuteur.setCellValueFactory(cellData -> {
            Auteur auteur = cellData.getValue().getAuteur();
            String nomPrenom = auteur.getNom() + " " + auteur.getPrenom();
            return new SimpleStringProperty(nomPrenom);
        });
    }

    /**
     * Ouvre une boîte de dialogue pour sélectionner un fichier à ouvrir. Si un fichier valide est sélectionné, il charge les données du fichier dans la liste observable de livres et les affiche dans le TableView. Il ajoute également un listener à la propriété selectedItemProperty() de la selectionModel du TableView pour récupérer les données de la ligne sélectionnée.
     */
    @FXML
    protected void openFile() {
        // Obtenir le chemin du dossier de travail courant
        String defaultPath = System.getProperty("user.dir");

        // Configurer le FileChooser pour n'ouvrir que les fichiers XML avec l'extension .xml
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(defaultPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Bibliothèque (XML)", "*.xml"));

        // Ouvrir une boîte de dialogue pour sélectionner un fichier à ouvrir
        File selectedFile = fileChooser.showOpenDialog(null);

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            // Récupérer les livres du fichier sélectionné
            dao = new BibliothequeDAO(selectedFile);
            listesLivres = Objects.requireNonNull(dao.getBibliotheque()).getLivres();

            // Créer une liste observable de livres à partir des livres de la bibliothèque
            ObservableList<Livre> livres = FXCollections.observableArrayList(listesLivres);

            // Configurer le TableView pour afficher les données de la liste observable de livres
            tableViewLivres.setItems(livres);

            // Ajouter un listener à la propriété selectedItemProperty() de la selectionModel du TableView pour récupérer les données de la ligne sélectionnée
            tableViewLivres.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                // Vérifier la nouvelle valeur sélectionnée
                if (newValue != null) {
                    champTitre.setText(newValue.getTitre());
                    champNomAuteur.setText(newValue.getAuteur().getNom());
                    champPrenomAuteur.setText(newValue.getAuteur().getPrenom());
                    champPresentation.setText(newValue.getPresentation());
                    champParution.setText(Integer.toString(newValue.getParution()));
                    champColonne.setText(Integer.toString(newValue.getColonne()));
                    champRangee.setText(Integer.toString(newValue.getRangee()));

                    if (newValue.getUrlImage() != null) {
                        bookCoverImageView.setImage(new Image(newValue.getUrlImage()));
                    } else {
                        bookCoverImageView.setImage(new Image(getClass().getResource("image-non-disponible.jpg").toString()));
                    }
                }
            });

            clearChamps();
        }
    }

    /**
     * Quitte l'application.
     */
    @FXML
    protected void quitApplication() {
        Platform.exit();
    }

    /**
     * Enregistre un fichier.
     */
    @FXML
    protected void saveFile() {
        // Vérifier que l'objet DAO a été initialisé
        if (dao != null) {
            // Créer une nouvelle bibliothèque avec les livres de la tableview
            var nvBibliotheque = new Bibliotheque();
            nvBibliotheque.setLivres(tableViewLivres.getItems().stream().toList());

            // Mettre à jour la bibliothèque dans le fichier XML en utilisant l'objet DAO
            dao.updateBibliotheque(nvBibliotheque);
        } else {
            saveAsFile();
        }
    }

    /**
     * Enregistre un fichier sous un nom donné en utilisant un FileChooser pour choisir le chemin et le nom du fichier.
     * <p>
     * Si la bibliothèque a déjà été enregistrée auparavant, le FileChooser s'ouvre avec le chemin du fichier actuel comme répertoire initial.
     * <p>
     * Si la bibliothèque n'a pas été enregistrée auparavant, le FileChooser s'ouvre avec le répertoire du programme comme répertoire initial.
     * <p>
     * Si un fichier est sélectionné, la méthode crée un nouveau BibliothequeDAO pour le fichier sélectionné et appelle la méthode saveFile() pour enregistrer la bibliothèque dans ce fichier.
     */
    @FXML
    protected void saveAsFile() {
        String defaultPath = dao == null ? System.getProperty("user.dir") : dao.getFichierXML().getParent();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(defaultPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Bibliothèque (XML)", "*.xml"));

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            dao = new BibliothequeDAO(selectedFile);
            saveFile();
        }
    }

    /**
     * Affiche la fenêtre "à propos" des développeurs.
     */
    @FXML
    protected void showAbout() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("about-view.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Crée un nouveau livre.
     */
    @FXML
    protected void creerLivre() {
        tableViewLivres.getSelectionModel().clearSelection();
        clearChamps();
    }

    /**
     * Supprime le livre sélectionné.
     */
    @FXML
    protected void supprimerLivre() {
        // Récupération de l'index de l'élément sélectionné
        int selectedIndex = tableViewLivres.getSelectionModel().getSelectedIndex();
        // Vérification qu'un élément est bien sélectionné
        if (selectedIndex != -1) {
            // Création d'un nouvel objet Livre
            tableViewLivres.getItems().remove(selectedIndex);
            // Enlève la selection de l'élément
            tableViewLivres.getSelectionModel().clearSelection();
            // Mise à jour de l'affichage dans le TableView
            tableViewLivres.refresh();
            clearChamps();
        }
    }

    /**
     * Modifie le livre sélectionné.
     */

    @FXML
    protected void modifierLivre() {
        // Récupération de l'index de l'élément sélectionné
        int selectedIndex = tableViewLivres.getSelectionModel().getSelectedIndex();
        // Vérification qu'un élément est bien sélectionné
        if (selectedIndex != -1) {
            // Création d'un nouvel objet Livre
            var livreModifie = tableViewLivres.getSelectionModel().getSelectedItem();
            // Création d'un nouvel objet Auteur
            var auteur = new Auteur();

            // Récupération des données modifiées dans les champs de texte
            auteur.setNom(champNomAuteur.getText());
            auteur.setPrenom(champPrenomAuteur.getText());

            livreModifie.setTitre(champTitre.getText());
            livreModifie.setAuteur(auteur);
            livreModifie.setPresentation(champPresentation.getText());
            livreModifie.setParution(Integer.parseInt(champParution.getText()));
            livreModifie.setColonne(Integer.parseInt(champColonne.getText()));
            livreModifie.setRangee(Integer.parseInt(champRangee.getText()));

            // Mise à jour de l'affichage dans le TableView
            tableViewLivres.refresh();
        } else {
            Livre livre = new Livre();
            Auteur auteur = new Auteur();
            auteur.setNom(champNomAuteur.getText());
            auteur.setPrenom(champPrenomAuteur.getText());

            livre.setTitre(champTitre.getText());
            livre.setAuteur(auteur);
            livre.setPresentation(champPresentation.getText());
            livre.setParution(Integer.parseInt(champParution.getText()));
            livre.setColonne(Integer.parseInt(champColonne.getText()));
            livre.setRangee(Integer.parseInt(champRangee.getText()));
            tableViewLivres.getItems().add(livre);
        }
        clearChamps();
    }

    @FXML
    protected void changeImageURL() {
        // Récupérer le livre sélectionné dans le tableau
        Livre livreSelectionne = tableViewLivres.getSelectionModel().getSelectedItem();
        if (livreSelectionne == null) {
            // Aucun livre sélectionné, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR, "Aucun livre sélectionné");
            alert.showAndWait();
            return;
        }

        // Afficher une boîte de dialogue avec un champ de texte pour saisir la nouvelle URL
        TextInputDialog dialog = new TextInputDialog(livreSelectionne.getUrlImage());
        dialog.setTitle("Changer l'URL de l'image");
        dialog.setHeaderText("Saisissez la nouvelle URL de l'image pour le livre " + livreSelectionne.getTitre());
        dialog.setContentText("Nouvelle URL :");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().equals("")) {
            String nouvelleURL = result.get();
            // Mettre à jour l'URL de l'image dans le livre sélectionné
            livreSelectionne.setUrlImage(nouvelleURL);
            // Mettre à jour l'image affichée
            bookCoverImageView.setImage(new Image(nouvelleURL));
        }
    }

    /**
     * Efface le contenu des champs de saisie
     */
    private void clearChamps() {
        champTitre.setText("");
        champNomAuteur.setText("");
        champPrenomAuteur.setText("");
        champPresentation.setText("");
        champParution.setText("");
        champColonne.setText("");
        champRangee.setText("");
    }
}
