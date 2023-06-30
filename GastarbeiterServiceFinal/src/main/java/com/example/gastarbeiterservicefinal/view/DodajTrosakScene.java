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

public class DodajTrosakScene extends BorderPane {
    public DodajTrosakScene(Stage parent, GoogleDrive googleDrive, double width, double height) {
        googleDrive.initService();
        googleDrive.loadData();
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
        kljucnaRecTF.setMaxWidth(200);
        kljucnaRecVBox.setAlignment(Pos.CENTER);
        kljucnaRecVBox.setSpacing(10);

        cenaVBox.getChildren().add(cenaLbl);
        cenaVBox.getChildren().add(sumaTF);
        sumaTF.setMaxWidth(200);
        cenaVBox.setAlignment(Pos.CENTER);
        cenaVBox.setSpacing(10);

        datumVbox.getChildren().add(datumLbl);
        datumVbox.getChildren().add(datumPicker);
        datumVbox.setAlignment(Pos.CENTER);
        datumVbox.setSpacing(10);

        opisVBox.getChildren().add(opisLbl);
        opisVBox.getChildren().add(opisTA);
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

        buttonDodaj.setOnAction(e -> {
            if(kljucnaRecTF.getText() == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Kljucna rec mora biti dodata");
                alert.setHeaderText("Kljucna rec nije dodata");
                alert.setContentText("Dodajte kljucnu rec!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            String kljucnaRec = kljucnaRecTF.getText();

            if(sumaTF.getText() == "" || !sumaTF.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Suma mora biti dodata");
                alert.setHeaderText("Suma nije dodata");
                alert.setContentText("Dodajte sumu!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            double suma = Double.parseDouble(sumaTF.getText());

            LocalDate datum = datumPicker.getValue();
            if(datum == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Datum mora biti izabran");
                alert.setHeaderText("Datum nije izabran");
                alert.setContentText("Izaberite datum!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }

            if(opisTA.getText() == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Opis mora biti dodat");
                alert.setHeaderText("Opis nije dodat");
                alert.setContentText("Dodajte opis!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            String opis = opisTA.getText();

            Troskovi trosak = new Troskovi(suma, datum.getMonth().getValue(), datum.getYear(), datum.getDayOfMonth(),kljucnaRec, opis);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Zelite da dodate novi trosak");
            alert.setHeaderText("Novi trosak");
            alert.setContentText("Kljucna rec: " + kljucnaRec + "\n" +
                    "Suma: " + suma + "\n" + "Datum: " + datum + "\n" + "Opis: " + opis);
            Optional<ButtonType> result = alert.showAndWait();
            try {
                if (result.get() == ButtonType.OK) {
                    googleDrive.initService();
                    googleDrive.addTrosak(trosak);
                    MainScene mainScene = new MainScene(parent, googleDrive, width, height);
                    parent.setScene(new Scene(mainScene, width, height));
                }
            } catch (Exception e1) {

            }
        });

        buttonNazad.setOnAction(e -> {
            MainScene mainScene = new MainScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(mainScene, width, height));
        });

        this.setCenter(vBox);
        this.setBottom(hBox);
    }
}
