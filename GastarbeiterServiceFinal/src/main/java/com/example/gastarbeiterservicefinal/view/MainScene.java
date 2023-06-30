package com.example.gastarbeiterservicefinal.view;

import com.example.gastarbeiterservicefinal.drive.GoogleDrive;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainScene extends BorderPane {
    // TODO: Implementirati dodavanje u google sheet (excel)
    public MainScene(Stage parent, GoogleDrive googleDrive, double width, double height) {
        VBox vBox = new VBox(20);
        HBox hBox = new HBox(10);
        HBox hBox2 = new HBox(10);

        Button posloviButton = new Button("Poslovi");
        Button prihodiButton = new Button("Prihodi");
        Button dodajPosaoButton = new Button("Novi posao");
        Button dodajTrosakButton = new Button("Novi trosak");
        Button troskoviButton = new Button("Troskovi");

        hBox.getChildren().add(posloviButton);
        hBox.getChildren().add(prihodiButton);
        hBox.getChildren().add(troskoviButton);
        vBox.getChildren().add(hBox);
        hBox2.getChildren().addAll(dodajPosaoButton, dodajTrosakButton);
        vBox.getChildren().add(hBox2);
        vBox.setAlignment(Pos.CENTER);
        hBox.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);

        this.setAlignment(vBox, Pos.CENTER);
        this.setCenter(vBox);

        dodajPosaoButton.setOnAction(e -> {
            DodajScene dodajScene = new DodajScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(dodajScene, width, height));
        });

        dodajTrosakButton.setOnAction((e -> {
            DodajTrosakScene dodajTrosakScene = new DodajTrosakScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(dodajTrosakScene, width, height));
        }));

        posloviButton.setOnAction(e -> {
            PosloviScene posloviScene = new PosloviScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(posloviScene, width, height));
        });

        prihodiButton.setOnAction(e -> {
            PrihodiScene prihodiScene = new PrihodiScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(prihodiScene, width, height));
        });

        troskoviButton.setOnAction(e -> {
            TroskoviScene troskoviScene = new TroskoviScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(troskoviScene, width, height));
        });
    }
}
