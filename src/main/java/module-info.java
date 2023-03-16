module com.lotun.gestionprojetagilelotun {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.lotun.gestionprojetagilelotun to javafx.fxml;
    exports com.lotun.gestionprojetagilelotun;
}