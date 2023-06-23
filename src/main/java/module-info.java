module com.lotun.gestionprojetagilelotun {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.xml.bind;
    requires java.sql;
    requires org.apache.pdfbox;

    opens com.lotun.gestionprojetagilelotun to javafx.fxml;
    opens com.lotun.gestionprojetagilelotun.controllers to javafx.fxml;
    opens com.lotun.gestionprojetagilelotun.models to jakarta.xml.bind, javafx.base;
    opens com.lotun.gestionprojetagilelotun.dao to java.sql;
    exports com.lotun.gestionprojetagilelotun;
    exports com.lotun.gestionprojetagilelotun.controllers;
}