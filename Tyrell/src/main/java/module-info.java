module app.tyrell {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.databind;
    requires org.json;
    requires java.desktop;
    requires com.fasterxml.jackson.core;
    requires java.sql.rowset;

    exports app.tyrell.controller;
    opens app.tyrell.controller to javafx.fxml;
    exports app.tyrell.backend;
    opens app.tyrell.backend to javafx.fxml;
}