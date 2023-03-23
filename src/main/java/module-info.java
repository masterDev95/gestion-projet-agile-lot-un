module com.lotun.gestionprojetagilelotun {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;

    opens com.lotun.gestionprojetagilelotun to javafx.fxml;
    opens com.lotun.gestionprojetagilelotun.controllers to javafx.fxml;
    opens com.lotun.gestionprojetagilelotun.classes to jakarta.xml.bind, javafx.base;
    exports com.lotun.gestionprojetagilelotun;
    exports com.lotun.gestionprojetagilelotun.controllers;
}