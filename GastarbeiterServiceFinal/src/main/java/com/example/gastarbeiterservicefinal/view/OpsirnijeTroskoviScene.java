package com.example.gastarbeiterservicefinal.view;

import com.example.gastarbeiterservicefinal.drive.GoogleDrive;
import com.example.gastarbeiterservicefinal.drive.model.Troskovi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

public class OpsirnijeTroskoviScene extends BorderPane {
    public OpsirnijeTroskoviScene(Troskovi troskovi, Stage parent, GoogleDrive googleDrive, double width, double height) {
        VBox vBox = new VBox();
        HBox hBox = new HBox(10);
        VBox datumVbox = new VBox();
        VBox cenaVBox = new VBox();
        VBox kljucnaRecVBox = new VBox();
        VBox opisVBox = new VBox();

        TextField sumaTF = new TextField();
        TextField kljucnaRecTF = new TextField();
        DatePicker datumPicker = new DatePicker();
        TextArea opisTA = new TextArea();
        Label cenaLbl = new Label("Suma");
        Label kljucnaRecLbl = new Label("Kljucna rec");
        Label datumLbl = new Label("Datum");
        Label opisLbl = new Label("Opis");
        Button buttonNazad = new Button("Nazad");
        Button buttonDodaj = new Button("Dodaj");

        kljucnaRecVBox.getChildren().addAll(kljucnaRecLbl, kljucnaRecTF);
        kljucnaRecTF.setText(troskovi.getKljucnaRec());
        kljucnaRecTF.setMaxWidth(200);
        kljucnaRecTF.setDisable(true);
        kljucnaRecTF.setStyle("-fx-opacity: 1.0;");
        kljucnaRecVBox.setAlignment(Pos.CENTER);
        kljucnaRecVBox.setSpacing(10);

        cenaVBox.getChildren().add(cenaLbl);
        cenaVBox.getChildren().add(sumaTF);
        sumaTF.setDisable(true);
        sumaTF.setStyle("-fx-opacity: 1.0;");
        sumaTF.setText(String.valueOf(troskovi.getSuma()));
        sumaTF.setMaxWidth(200);
        cenaVBox.setAlignment(Pos.CENTER);
        cenaVBox.setSpacing(10);

        datumVbox.getChildren().add(datumLbl);
        datumVbox.getChildren().add(datumPicker);
        datumVbox.setAlignment(Pos.CENTER);
        datumVbox.setSpacing(10);

        opisVBox.getChildren().add(opisLbl);
        opisVBox.getChildren().add(opisTA);
        opisTA.setDisable(true);
        opisTA.setStyle("-fx-opacity: 1.0;");
        opisTA.setText(troskovi.getOpis());
        opisTA.setMaxWidth(400);
        opisVBox.setAlignment(Pos.CENTER);
        opisVBox.setSpacing(10);

        vBox.getChildren().addAll(kljucnaRecVBox, cenaVBox, datumVbox, opisVBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        hBox.getChildren().add(buttonNazad);
        hBox.getChildren().add(buttonDodaj);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10));

        buttonNazad.setOnAction(e -> {
            TroskoviScene troskoviScene = new TroskoviScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(troskoviScene, width, height));
        });

        this.setCenter(vBox);
        this.setBottom(hBox);
    }
}
