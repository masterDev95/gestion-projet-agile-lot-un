package com.lotun.gestionprojetagilelotun.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Le contrôleur pour la vue "à propos"
 */
public class AboutController {
    /**
     * L'image d'Alexis
     */
    @FXML
    private ImageView imageAlexis;

    /**
     * L'image de Kelvin
     */
    @FXML
    private ImageView imageKelvin;

    /**
     * Initialise le contrôleur.
     * Charge les images des auteurs dans les ImageView correspondantes.
     */
    @FXML
    private void initialize() {
        imageAlexis.setImage(new Image(Objects.requireNonNull(getClass().getResource("alexis-alt.png")).toString()));
        imageKelvin.setImage(new Image(Objects.requireNonNull(getClass().getResource("kelvin-alt.png")).toString()));
    }
}
