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
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.util.Date;
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

    @FXML
    private TableColumn<Livre, String> colTitre;

    @FXML
    private TableColumn<Livre, String> colAuteur;

    @FXML
    private TableColumn<Livre, String> colPresentation;

    @FXML
    private TableColumn<Livre, String> colParution;

    @FXML
    private TableColumn<Livre, String> colColonne;

    @FXML
    private TableColumn<Livre, String> colRangee;

    @FXML
    private List<Livre> listesLivres;

    private BibliothequeDAO dao;

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
        System.out.println(getClass().getResource("about-view.fxml"));
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
            boolean check = checkChamps();
            if(check == false){
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
        }
        clearChamps();
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

    @FXML
    void showDialog(String contentErreure){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Erreure");
        alert.setContentText(contentErreure);
        Optional<ButtonType> result = alert.showAndWait();

    }

    @FXML
    private boolean checkChamps() {
        Date dt=new Date();
        int year=dt.getYear();
        System.out.println("Year for date object is : "+year);
        int current_Year=year+1900;
        System.out.println("Current year is : "+current_Year);


        if(champTitre.getText().isEmpty()){
            showDialog("Le champs titre est vide");
            return true;
        } else if (champPrenomAuteur.getText().isEmpty()) {
            showDialog("Le champs PrenomAuteur est vide");
            return true;
        } else if (champNomAuteur.getText().isEmpty()) {
            showDialog("Le champs NomAuteur est vide");
            return true;
        } else if (champPresentation.getText().isEmpty()) {
            showDialog("Le champs Presentation est vide");
            return true;
        } else if (Integer.parseInt(champParution.getText()) == current_Year || champParution.getText().isEmpty()) {
            showDialog("Le champs parution est invalide");
            return true;
        }else if (champColonne.getText().isEmpty()) {
            showDialog("Le champs colonne est vide");
            return true;
        } else if (champRangee.getText().isEmpty()) {
            showDialog("Le champs rangée est vide");
        }

        return false;

    }




}
