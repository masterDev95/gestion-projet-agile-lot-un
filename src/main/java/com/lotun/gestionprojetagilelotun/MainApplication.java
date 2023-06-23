package com.lotun.gestionprojetagilelotun;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Classe principale de l'application. Elle lance l'application en créant une nouvelle fenêtre JavaFX
 * avec le fichier FXML correspondant.
 */
public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    /**
     * Méthode héritée de la classe Application. Elle crée une nouvelle fenêtre JavaFX en chargeant le
     * fichier FXML "main-view.fxml". Elle définit le titre de la fenêtre, l'affiche et la rend non
     * redimensionnable.
     *
     * @param stage La fenêtre principale de l'application.
     * @throws IOException Si une erreur survient lors du chargement du fichier FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("controllers/PageConnexion.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("La folle B I B L I O");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
