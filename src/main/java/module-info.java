module com.lotun.gestionprojetagilelotun {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;

    opens com.lotun.gestionprojetagilelotun to javafx.fxml;
    exports com.lotun.gestionprojetagilelotun;
    opens com.lotun.gestionprojetagilelotun.classes to jakarta.xml.bind;
}