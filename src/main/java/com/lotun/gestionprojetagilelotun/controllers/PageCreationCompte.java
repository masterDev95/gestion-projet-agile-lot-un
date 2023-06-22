package com.lotun.gestionprojetagilelotun.controllers;

import com.lotun.gestionprojetagilelotun.classes.Bibliotheque;
import com.lotun.gestionprojetagilelotun.classes.Livre;
import com.lotun.gestionprojetagilelotun.classes.Role;
import com.lotun.gestionprojetagilelotun.dao.AuteurDAO;
import com.lotun.gestionprojetagilelotun.dao.ConnectionManager;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageCreationCompte {

    @FXML
    private ChoiceBox<Role> roleChoiceBox;

    @FXML
    private TextField identifiantText;

    @FXML
    private TextField passwordText;

    @FXML
    private Button creationButton;



    public void initialize() throws SQLException {
        // Créez une liste de rôles
        List<Role> roles = new ArrayList<>();

        // Ajoutez les rôles à la liste du ChoiceBox
        Connection connection = ConnectionManager.getDbConnection();
        Role role = new Role();

        // Création d'une instruction SQL
        Statement statement = connection.createStatement();

        // Exécution d'une requête
        ResultSet resultSet = statement.executeQuery("SELECT * FROM role");

        // Traitement des résultats
        while (resultSet.next()) {
            // Lire les valeurs des colonnes
            int roleId = resultSet.getInt("idrole");
            String roleName = resultSet.getString("nomrole");

            // Créer un objet Role
            Role roleBdd = new Role();

            roleBdd.setId(roleId);
            roleBdd.setRole(roleName);

            // Ajouter le rôle à la liste
            roles.add(roleBdd);
        }

        // Fermeture des ressources
        resultSet.close();
        statement.close();
        connection.close();

        // Ajouter les rôles à la ChoiceBox
        roleChoiceBox.getItems().addAll(roles);

        // Définir un convertisseur pour afficher le nom du rôle dans la ChoiceBox
        roleChoiceBox.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                if (role != null) {
                    return role.getRole();
                }
                return null;
            }

            @Override
            public Role fromString(String string) {
                return null;
            }
        });
    }

    @FXML
    protected  void creatUser() throws SQLException {
        Connection connection = ConnectionManager.getDbConnection();

        String query = "INSERT INTO user (identifiant, password,idrole) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, identifiantText.getText());
        statement.setString(2, passwordText.getText());
        statement.setInt(3, roleChoiceBox.getValue().getId());

        statement.executeUpdate();
        statement.close();

        connection.close();

        Scene currentScene = creationButton.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();
        currentStage.close();

    }

}
