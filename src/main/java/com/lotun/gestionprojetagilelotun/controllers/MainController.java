package com.lotun.gestionprojetagilelotun.controllers;

import com.lotun.gestionprojetagilelotun.classes.Livre;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Contrôleur principal pour la gestion de livres.
 */
public class MainController {
    /** Tableau des livres. */
    @FXML
    private TableView<Livre> tableViewLivres;
    /** Champ du titre du livre. */
    @FXML
    private TextField champTitre;
    /** Champ du prénom de l'auteur du livre. */
    @FXML
    private TextField champPrenomAuteur;
    /** Champ du nom de l'auteur du livre. */
    @FXML
    private TextField champNomAuteur;
    /** Champ de la présentation du livre. */
    @FXML
    private TextField champPresentation;
    /** Champ de la parution du livre. */
    @FXML
    private TextField champParution;
    /** Champ de la colonne du livre dans le tableau. */
    @FXML
    private TextField champColonne;
    /** Champ de la rangée du livre dans le tableau. */
    @FXML
    private TextField champRangee;

    /**
     * Ouvre un fichier.
     */
    @FXML
    protected void openFile() {}

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
    protected void saveFile() {}

    /**
     * Enregistre un fichier sous un nom donné.
     */
    @FXML
    protected void saveAsFile() {}

    /**
     * Affiche les informations sur le livre sélectionné.
     */
    @FXML
    protected void showInfo() {}

    /**
     * Crée un nouveau livre.
     */
    @FXML
    protected void creerLivre() {}

    /**
     * Supprime le livre sélectionné.
     */
    @FXML
    protected void supprimerLivre() {}

    /**
     * Modifie le livre sélectionné.
     */
    @FXML
    protected void modifierLivre() {}
}
