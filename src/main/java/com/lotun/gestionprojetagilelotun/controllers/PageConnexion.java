package com.lotun.gestionprojetagilelotun.controllers;

import com.lotun.gestionprojetagilelotun.classes.Session;
import com.lotun.gestionprojetagilelotun.dao.ConnectionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageConnexion {

    @FXML
    private TextField identifiant;

    @FXML
    private TextField password;

    @FXML
    private Button connexionButton;

    @FXML
    protected void showCreationPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PageCreationCompte.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    protected void authenticate() throws SQLException, IOException {
        Connection connection = ConnectionManager.getDbConnection();
        String query = "SELECT * FROM user WHERE identifiant = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, identifiant.getText());
        statement.setString(2,password.getText());
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Session.setUsername(identifiant.getText());
            if(resultSet.getInt("idrole") == 1){
                Session.setGerant(true);
            }else {
                Session.setGerant(false);
            }
            Scene currentScene = connexionButton.getScene();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Parent root = loader.load();

            // Obtenez la fenêtre principale à partir de la scène actuelle
            Stage currentStage = (Stage) currentScene.getWindow();

            // Remplacez le contenu de la fenêtre principale avec la nouvelle page
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            // Facultatif : définissez des paramètres supplémentaires pour la nouvelle scène ou le nouveau stage
            currentStage.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de connexion");
            alert.setHeaderText("Identifiants incorrects");
            alert.setContentText("Veuillez vérifier votre nom d'utilisateur et votre mot de passe.");
            alert.showAndWait();
        }
    }
}
