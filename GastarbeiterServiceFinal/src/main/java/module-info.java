module com.example.gastarbeiterservicefinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.api.services.drive;
    requires com.google.gson;
    requires com.google.api.client.auth;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires google.api.client;
    requires com.google.api.client.extensions.jetty.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires jdk.httpserver;

    opens com.example.gastarbeiterservicefinal to javafx.fxml;
    opens com.example.gastarbeiterservicefinal.drive.model to javafx.base, com.google.gson;

    exports com.example.gastarbeiterservicefinal;
}