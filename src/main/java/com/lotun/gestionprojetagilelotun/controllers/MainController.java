package com.lotun.gestionprojetagilelotun.controllers;

import com.lotun.gestionprojetagilelotun.classes.Auteur;
import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;
import com.lotun.gestionprojetagilelotun.classes.Livre;
import com.lotun.gestionprojetagilelotun.classes.Session;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationLink;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDBorderStyleDictionary;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageXYZDestination;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Contrôleur principal pour la gestion de livres.
 */
public class MainController {
    // Constantes
    private static final String USER_DIR = "user.dir";
    private static final String FILE_EXTENSION = "*.xml";
    private static final String DEFAULT_IMAGE_PATH = "image-non-disponible.jpg";


    @FXML
    private Pane paneAjoutLivre;
    @FXML
    private Button buttonAdd;
    @FXML
    private Button buttonSup;
    /** Bouton bascule permettant de passer entre le mode en direct et le mode hors ligne. */
    @FXML
    private Button toggleLiveBouton;
    /** Bouton radio représentant l'état "Prêté". */
    @FXML
    private RadioButton boutonPrete;
    /** Bouton radio représentant l'état "Disponible". */
    @FXML
    private RadioButton boutonDisponible;
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
     * Colonne de l'etat du livre dans le tableau.
     */
    @FXML
    private  TableColumn<Livre, String> colEtat;
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
    /** Mode en direct : indique si l'application est en mode en direct ou hors ligne. */
    private Boolean liveMode;
    /** Logger pour la journalisation des événements et des erreurs. */
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Méthode d'initialisation du contrôleur.
     * Initialise le tableau de livres et définit les colonnes.
     */
    @FXML
    private void initialize() {
        liveMode = false;

        initializeTableView();
        validationFormulaireEvent();

        boolean isGerant = Session.isGerant();

        if(isGerant){
            paneAjoutLivre.setVisible(true);
        }else {
            paneAjoutLivre.setVisible(false);
            bookCoverImageView.setDisable(true);
            buttonAdd.setVisible(false);
            buttonSup.setVisible(false);
        }
    }

    @FXML
    protected void deconnexion() throws IOException {
        Scene currentScene = toggleLiveBouton.getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PageConnexion.fxml"));
        Parent root = loader.load();

        // Obtenez la fenêtre principale à partir de la scène actuelle
        Stage currentStage = (Stage) currentScene.getWindow();

        // Remplacez le contenu de la fenêtre principale avec la nouvelle page
        Scene newScene = new Scene(root);
        currentStage.setScene(newScene);

        // Facultatif : définissez des paramètres supplémentaires pour la nouvelle scène ou le nouveau stage
        currentStage.show();
    }

    /**
     * Cette méthode attache un gestionnaire d'événements sur les champs de texte du formulaire
     * pour capturer l'événement de pression de la touche "Entrée" et déclencher la méthode
     * "modifierLivre()" lorsque la touche "Entrée" est pressée.
     */
    private void validationFormulaireEvent() {
        // Création d'un tableau contenant tous les champs de texte à surveiller
        TextField[] champsTexte = {champTitre, champNomAuteur, champPrenomAuteur, champPresentation, champParution, champColonne, champRangee};

        // Ajout d'un événement pour chaque champ de texte du tableau
        for (TextField champ : champsTexte) {
            champ.setOnKeyPressed(event -> {
                // Si la touche appuyée est la touche Entrée
                if (event.getCode() == KeyCode.ENTER) {
                    // On appelle la méthode modifierLivre
                    try {
                        modifierLivre();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
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
        colEtat.setCellValueFactory(cellData -> {
            Livre livre = cellData.getValue();
            boolean etat = livre.getEtat();
            String etatString;
            if(etat == false){
                etatString = "Prété";
            }else {
                etatString = "Disponible";
            }
            return new SimpleStringProperty(etatString);
        });

        // Configurer la colonne "Auteur" pour afficher le nom et prénom de l'auteur
        colAuteur.setCellValueFactory(cellData -> {
            Auteur auteur = cellData.getValue().getAuteur();
            String nomPrenom;
            try {
                nomPrenom = auteur.getNom() + " " + auteur.getPrenom();
            } catch (Exception e) {
                nomPrenom = "";
            }
            return new SimpleStringProperty(nomPrenom);
        });

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

                if (newValue.getEtat()) {
                    boutonDisponible.setSelected(true);
                } else {
                    boutonPrete.setSelected(true);
                }

                try {
                    bookCoverImageView.setImage(new Image(newValue.getUrlImage()));
                } catch (Exception e) {
                    bookCoverImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(DEFAULT_IMAGE_PATH)).toString()));
                }
            }
        });
    }

    /**
     * Ouvre une boîte de dialogue pour sélectionner un fichier à ouvrir. Si un fichier valide est sélectionné, il charge les données du fichier dans la liste observable de livres et les affiche dans le TableView. Il ajoute également un listener à la propriété selectedItemProperty() de la selectionModel du TableView pour récupérer les données de la ligne sélectionnée.
     */
    @FXML
    protected void openFile() {
        // Obtenir le chemin du dossier de travail courant
        String defaultPath = System.getProperty(USER_DIR);

        // Configurer le FileChooser pour n'ouvrir que les fichiers XML avec l'extension .xml
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(defaultPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Bibliothèque (XML)", FILE_EXTENSION));

        // Ouvrir une boîte de dialogue pour sélectionner un fichier à ouvrir
        File selectedFile = fileChooser.showOpenDialog(null);

        // Vérifier si un fichier a été sélectionné
        if (selectedFile != null) {
            // Remplissage du tableau
            dao = new BibliothequeDAO(selectedFile);
            remplirTableau(dao.getBibliotheque());
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
    protected void saveFile() throws SQLException {
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
    protected void saveAsFile() throws SQLException {
        String defaultPath = dao == null ? System.getProperty(USER_DIR) : dao.getFichierXML().getParent();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(defaultPath));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier Bibliothèque (XML)", FILE_EXTENSION));

        File selectedFile = fileChooser.showSaveDialog(null);

        if (selectedFile != null) {
            dao = new BibliothequeDAO(selectedFile);
            saveFile();
        }
    }

    /**
     * Crée un fichier PDF à partir des livres affichés dans le TableView.
     * Le fichier PDF généré contient une page de garde avec le titre "Mon Livre".
     * Le fichier est sauvegardé dans le répertoire spécifié.
     *
     * @throws IOException en cas d'erreur lors de la manipulation du fichier PDF
     */
    @FXML
    protected void createPDf() throws IOException {
        var nvBibliotheque = new Bibliotheque();
        nvBibliotheque.setLivres(tableViewLivres.getItems().stream().toList());

        try (PDDocument document = new PDDocument()) {
            // Création d'une page de garde
            PDPage coverPage = new PDPage(PDRectangle.A4);
            document.addPage(coverPage);

            // Ajout du contenu de la page de garde
            PDPageContentStream coverContent = new PDPageContentStream(document, coverPage);
            coverContent.beginText();
            coverContent.setFont(PDType1Font.HELVETICA_BOLD, 20);
            coverContent.newLineAtOffset(100, 700);
            coverContent.showText("Mon Livre");
            coverContent.endText();

            // Sauvegarde du document
            document.save("C:\\Users\\kakas\\Downloads\\testPDF.pdf");
            logger.info("Le fichier PDF a été généré avec succès.");
        }
    }

    /**
     * Crée une destination de page PDPageDestination pour le document spécifié et la page cible.
     *
     * @param document   le document PD pour lequel créer la destination
     * @param targetPage la page cible de la destination
     * @return la destination de page PDPageDestination créée
     */
    private static PDPageDestination createDestination(PDDocument document, PDPage targetPage) {
        PDPageXYZDestination destination = new PDPageXYZDestination();
        destination.setPage(targetPage);
        destination.setZoom(1);
        document.addPage(targetPage);
        return destination;
    }

    /**
     * Ajoute un lien cliquable sur une page spécifiée dans le document PD.
     *
     * @param page      la page sur laquelle ajouter le lien
     * @param document  le document PD dans lequel ajouter le lien
     * @param item      l'élément de plan PDOutlineItem avec la destination du lien
     * @throws IOException en cas d'erreur lors de la manipulation du contenu de la page
     */
    private static void addClickableLink(PDPage page, PDDocument document, PDOutlineItem item) throws IOException {
        PDAnnotationLink annotation = new PDAnnotationLink();
        annotation.setDestination(item.getDestination());
        annotation.setBorderStyle(new PDBorderStyleDictionary());
        annotation.setColor(new PDColor(new float[]{0, 0, 1}, PDDeviceRGB.INSTANCE));


        PDRectangle rect = new PDRectangle();
        rect.setLowerLeftX(100);
        rect.setLowerLeftY(500);
        rect.setUpperRightX(200);
        rect.setUpperRightY(520);
        annotation.setRectangle(rect);

        page.getAnnotations().add(annotation);

        PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        contentStream.newLineAtOffset(100, 510); // Coordonnées du lien
        contentStream.showText(item.getTitle());
        contentStream.endText();
        contentStream.close();
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
        bookCoverImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(DEFAULT_IMAGE_PATH)).toString()));
        champTitre.requestFocus();
        clearChamps();
    }

    /**
     * Supprime le livre sélectionné.
     */
    @FXML
    protected void supprimerLivre() throws SQLException {
        // Récupération de l'index de l'élément sélectionné
        int selectedIndex = tableViewLivres.getSelectionModel().getSelectedIndex();
        // Vérification qu'un élément est bien sélectionné
        if (selectedIndex != -1) {
            if(liveMode == true){
                BibliothequeDAO.supLivre(tableViewLivres.getItems().get(selectedIndex).getId());
            }
            // Création d'un nouvel objet Livre
            tableViewLivres.getItems().remove(selectedIndex);
            // Enlève la selection de l'élément
            tableViewLivres.getSelectionModel().clearSelection();
            // Mise à jour de l'affichage dans le TableView
            tableViewLivres.refresh();
            clearChamps();

        } else {
            // Afficher un message d'erreur si aucun élément n'est sélectionné
            showDialog("Veuillez sélectionner un livre à supprimer.");
        }
    }

    /**
     * Méthode qui permet de modifier un livre existant dans la bibliothèque.
     * Récupère l'index de l'élément sélectionné dans le TableView des livres,
     * vérifie si un élément est bien sélectionné et met à jour l'affichage dans le TableView.
     * Si aucun élément n'est sélectionné, vérifie que tous les champs sont remplis correctement
     * avant de mettre à jour l'affichage dans le TableView. Enfin, vide les champs.
     */
    @FXML
    protected void modifierLivre() throws SQLException {

        // Récupération de l'index de l'élément sélectionné
        int selectedIndex = tableViewLivres.getSelectionModel().getSelectedIndex();
        // Vérification qu'un élément est bien sélectionné
        if (selectedIndex != -1) {
            // Création d'un nouvel objet Livre
            var livreModifie = tableViewLivres.getSelectionModel().getSelectedItem();

            updateLivreTableView(livreModifie);

            // Mise à jour de l'affichage dans le TableView
            tableViewLivres.refresh();
        } else {
            if (!checkChamps()) {
                // Création d'un nouvel objet Livre dans le TableView
                var auteurTemp = new Auteur();
                var livreTemp = new Livre();
                livreTemp.setAuteur(auteurTemp);
                tableViewLivres.getItems().add(livreTemp);
                var nouveauLivre = tableViewLivres.getItems().get(tableViewLivres.getItems().toArray().length - 1);
                // Si tous les champs sont remplis correctement, mise à jour de l'affichage dans le TableView
                updateLivreTableView(nouveauLivre);
                tableViewLivres.refresh();
            } else {
                // Sinon, retourne
                return;
            }
        }
        // Vide les champs
        clearChamps();
    }

    /**
     * Cette méthode met à jour les données du livre sélectionné dans le TableView.
     * Elle récupère les données modifiées dans les champs de texte et les assigne à l'objet Livre correspondant.
     *
     * @throws NumberFormatException si le texte dans les champs Parution, Colonne et Rangée ne peut pas être converti en entier.
     */
    private void updateLivreTableView(Livre livreModifie) throws SQLException {
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
        livreModifie.setEtat(boutonDisponible.isSelected());
        if(liveMode == true){
            BibliothequeDAO.ajoutLivre(livreModifie);
        }
    }

    /**
     * Permet de changer l'URL de l'image d'un livre sélectionné dans le TableView.
     * Une boîte de dialogue s'affiche pour saisir la nouvelle URL, si la saisie est valide,
     * l'URL de l'image dans le livre sélectionné est mise à jour et l'image affichée est actualisée.
     * Si aucun livre n'est sélectionné, un message d'erreur s'affiche.
     */
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

    /**
     * Affiche une boîte de dialogue avec un message d'erreur.
     *
     * @param message le message d'erreur à afficher
     */
    private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Vérifie que tous les champs obligatoires sont remplis et valides.
     *
     * @return true si l'un des champs obligatoires est vide ou invalide, false sinon
     */
    private boolean checkChamps() {
        if (isChampVide(champTitre, "Le champ titre est vide.")) return true;
        if (isChampVide(champPrenomAuteur, "Le champ prénom de l'auteur est vide.")) return true;
        if (isChampVide(champNomAuteur, "Le champ nom de l'auteur est vide.")) return true;
        if (isChampVide(champPresentation, "Le champ présentation est vide.")) return true;
        if (isChampVide(champParution, "Le champ parution est vide.")) return true;
        if (isChampNonEntier(champColonne, "Le champ colonne doit être un nombre entier.")) return true;
        if (isChampNonEntier(champRangee, "Le champ rangée doit être un nombre entier.")) return true;

        String parution = champParution.getText().trim();
        String anneeCourante = String.valueOf(LocalDate.now().getYear());

        if (!isAnneeValide(parution)) {
            showDialog("Le champ parution doit être une année valide (format : YYYY).");
            champParution.requestFocus();
            return true;
        }

        if (isAnneeFuture(parution, anneeCourante)) {
            showDialog("Le champ parution doit être la même année ou antérieure à l'année courante (" + anneeCourante + ").");
            champParution.requestFocus();
            return true;
        }

        return false;
    }

    /**
     * Vérifie si le champ spécifié est vide.
     * Affiche un message d'erreur et met le focus sur le champ s'il est vide.
     *
     * @param champ         le champ à vérifier
     * @param messageErreur le message d'erreur à afficher si le champ est vide
     * @return true si le champ est vide, false sinon
     */
    private boolean isChampVide(TextField champ, String messageErreur) {
        if (champ.getText().trim().isEmpty()) {
            showDialog(messageErreur);
            champ.requestFocus();
            return true;
        }
        return false;
    }

    /**
     * Vérifie si le champ spécifié ne contient pas une valeur entière.
     * Affiche un message d'erreur et met le focus sur le champ s'il ne contient pas une valeur entière.
     *
     * @param champ         le champ à vérifier
     * @param messageErreur le message d'erreur à afficher si le champ ne contient pas une valeur entière
     * @return true si le champ ne contient pas une valeur entière, false sinon
     */
    private boolean isChampNonEntier(TextField champ, String messageErreur) {
        String valeur = champ.getText().trim();
        if (!valeur.matches("\\d+")) {
            showDialog(messageErreur);
            champ.requestFocus();
            return true;
        }
        return false;
    }

    /**
     * Vérifie si l'année de parution spécifiée est au format valide (YYYY).
     *
     * @param parution l'année de parution à vérifier
     * @return true si l'année de parution est au format valide, false sinon
     */
    private boolean isAnneeValide(String parution) {
        return parution.matches("\\d{4}");
    }

    /**
     * Vérifie si l'année de parution spécifiée est une année future par rapport à l'année courante.
     *
     * @param parution       l'année de parution à vérifier
     * @param anneeCourante l'année courante à comparer avec l'année de parution
     * @return true si l'année de parution est une année future, false sinon
     */
    private boolean isAnneeFuture(String parution, String anneeCourante) {
        return Integer.parseInt(parution) > Integer.parseInt(anneeCourante);
    }

    /**
     * Méthode gérant le basculement du mode en ligne/hors-ligne.
     * Met à jour la valeur de liveMode, le texte du bouton et remplit le tableau de livres.
     *
     * @throws SQLException en cas d'erreur lors de la récupération de la bibliothèque depuis la base de données
     */
    @FXML
    protected void toggleLiveMode() throws SQLException {
        liveMode = !liveMode;
        String modeText = liveMode ? "hors-ligne" : "en ligne";
        toggleLiveBouton.setText("Passer en mode " + modeText);

        // Remplissage du tableau
        Bibliotheque bibliotheque = BibliothequeDAO.getBibliothequeFromDB();
        remplirTableau(bibliotheque);
    }

    /**
     * Rempli le tableau de livres avec les livres de la bibliothèque spécifiée.
     *
     * @param bibliotheque la bibliothèque contenant les livres à afficher
     */
    private void remplirTableau(Bibliotheque bibliotheque) {
        // Récup les livres de la bibliothèque
        listesLivres = Objects.requireNonNull(bibliotheque).getLivres();

        // Créer une liste observable de livres à partir des livres de la bibliothèque
        ObservableList<Livre> livres = FXCollections.observableArrayList(listesLivres);

        // Configurer le TableView pour afficher les données de la liste observable de livres
        tableViewLivres.setItems(livres);

        clearChamps();
    }
}
