package com.example.gastarbeiterservicefinal.view;

import com.example.gastarbeiterservicefinal.drive.GoogleDrive;
import com.example.gastarbeiterservicefinal.drive.model.NacinPlacanja;
import com.example.gastarbeiterservicefinal.drive.model.Posao;
import com.example.gastarbeiterservicefinal.drive.model.Valuta;
import javafx.beans.value.ObservableValue;
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

public class DodajScene extends BorderPane {

    public DodajScene(Stage parent, GoogleDrive googleDrive, double width, double height) {
        googleDrive.initService();
        googleDrive.loadData();
        VBox vBox = new VBox();
        HBox hBox = new HBox(10);
        VBox klijentVBox = new VBox();
        HBox nacinPlacanjaHBox = new HBox();
        VBox cenaVBox = new VBox();
        HBox valutaKursHB = new HBox();
        VBox valutaVBox = new VBox();
        VBox kursVBox = new VBox();
        VBox datumVbox = new VBox();
        VBox troskoviVBox = new VBox();
        VBox opisVBox = new VBox();

        TextField klijentTF = new TextField();
        ChoiceBox nacinPlacanjaCB = new ChoiceBox();
        TextField cenaTF = new TextField();
        ChoiceBox valutaCB = new ChoiceBox();
        TextField kursTF = new TextField();
        DatePicker datumPicker = new DatePicker();
        TextField troskoviTF = new TextField();
        TextArea opisTA = new TextArea();
        Button buttonNazad = new Button("Nazad");
        Button buttonFinish = new Button("Sacuvaj");
        Label klijentLbl = new Label("Klijent");
        Label nacinPlacanjaLbl = new Label("Nacin placanja");
        Label cenaLbl = new Label("Cena");
        Label valutaLbl = new Label("Valuta");
        Label kursLbl = new Label("Trenutni kurs");
        Label datumLbl = new Label("Datum");
        Label troskoviLbl = new Label("Troskovi");
        Label opisPoslaLbl = new Label("Opis posla");

        klijentVBox.getChildren().add(klijentLbl);
        klijentVBox.getChildren().add(klijentTF);
        klijentTF.setMaxWidth(200);
        vBox.getChildren().add(klijentVBox);
        klijentVBox.setAlignment(Pos.CENTER);

        nacinPlacanjaHBox.getChildren().add(nacinPlacanjaLbl);
        nacinPlacanjaHBox.getChildren().add(nacinPlacanjaCB);
        nacinPlacanjaCB.getItems().addAll(NacinPlacanja.values());
        vBox.getChildren().add(nacinPlacanjaHBox);
        nacinPlacanjaHBox.setAlignment(Pos.CENTER);
        nacinPlacanjaHBox.setSpacing(10);

        cenaVBox.getChildren().add(cenaLbl);
        cenaVBox.getChildren().add(cenaTF);
        cenaTF.setMaxWidth(200);
        vBox.getChildren().add(cenaVBox);
        cenaVBox.setAlignment(Pos.CENTER);

        valutaVBox.getChildren().add(valutaLbl);
        valutaCB.getItems().addAll(Valuta.values());
        valutaVBox.getChildren().add(valutaCB);
        valutaVBox.setAlignment(Pos.CENTER);
        valutaKursHB.getChildren().add(valutaVBox);
        kursVBox.getChildren().add(kursLbl);
        kursVBox.getChildren().add(kursTF);
        kursVBox.setAlignment(Pos.CENTER);
        valutaKursHB.getChildren().add(kursVBox);
        valutaKursHB.setSpacing(20);
        valutaKursHB.setAlignment(Pos.CENTER);
        vBox.getChildren().add(valutaKursHB);

        datumVbox.getChildren().add(datumLbl);
        datumVbox.getChildren().add(datumPicker);
        vBox.getChildren().add(datumVbox);
        datumVbox.setAlignment(Pos.CENTER);

        troskoviVBox.getChildren().add(troskoviLbl);
        troskoviVBox.getChildren().add(troskoviTF);
        troskoviTF.setMaxWidth(200);
        vBox.getChildren().add(troskoviVBox);
        troskoviVBox.setAlignment(Pos.CENTER);

        opisVBox.getChildren().add(opisPoslaLbl);
        opisVBox.getChildren().add(opisTA);
        opisTA.setMaxWidth(400);
        opisVBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(opisVBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        hBox.getChildren().add(buttonNazad);
        hBox.getChildren().add(buttonFinish);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10));

        valutaCB.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_val, Number new_val) -> {
                    if(new_val.intValue() == 1) {
                        kursTF.setDisable(false);
                    } else {
                        kursTF.setDisable(true);
                    }
                }
        );

        nacinPlacanjaCB.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                 Number old_val, Number new_val) -> {
                    if (new_val.intValue() == 0) {
                        kursTF.setDisable(true);
                        valutaCB.getSelectionModel().select(0);
                        valutaCB.setDisable(true);
                    } else {
                        kursTF.setDisable(false);
                        valutaCB.setDisable(false);
                        valutaCB.getSelectionModel().clearSelection();
                    }
                }

        );

        buttonFinish.setOnAction(e -> {
            String klijent = klijentTF.getText();
            if(klijent == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Klijent mora biti popunjen");
                alert.setHeaderText("Klijent nije popunjen");
                alert.setContentText("Popunite ime klijenta!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
             }
            NacinPlacanja nacinPlacanja = (NacinPlacanja) nacinPlacanjaCB.getValue();
            if(nacinPlacanja == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nacin placanja mora biti odabran");
                alert.setHeaderText("Nacin placanja nije odabran");
                alert.setContentText("Odaberite nacin placanja!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            if(cenaTF.getText() == "" || !cenaTF.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Cena mora biti popunjena");
                alert.setHeaderText("Cena nije popunjena");
                alert.setContentText("Popunite cenu!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }

            }
            double cena = Double.parseDouble(cenaTF.getText());
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
            String opisPosla = opisTA.getText();
            if(opisPosla == "") {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Opis posla mora biti dodat");
                alert.setHeaderText("Opis posla nije dodat");
                alert.setContentText("Dodajte opis posla!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            Valuta valuta = (Valuta) valutaCB.getValue();
            if(valuta == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Valuta mora biti odabrana");
                alert.setHeaderText("Valuta nije odabrana");
                alert.setContentText("Odaberite valutu!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            double kurs;
            if(valuta != Valuta.DINAR) {
                if(kursTF.getText() == "" || !kursTF.getText().matches("[0-9]+")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Kurs mora biti dodat");
                    alert.setHeaderText("Ukoliko zelite stranu valutu morate dodati kurs");
                    alert.setContentText("Dodajte kurs!");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get() == ButtonType.OK) {
                        return;
                    }
                }
                kurs = Double.parseDouble(kursTF.getText());
            } else {
                kurs = 1;
            }
            if(troskoviTF.getText() == "" || !troskoviTF.getText().matches("[0-9]+")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Troskovi moraju biti dodati");
                alert.setHeaderText("Troskovi nisu dodati");
                alert.setContentText("Dodajte troskove!");
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK) {
                    return;
                }
            }
            double troskovi = Double.parseDouble(troskoviTF.getText());
            Posao p = new Posao(klijent,cena,datum.getYear(), datum.getMonth().getValue(), datum.getDayOfMonth(), opisPosla, kurs, nacinPlacanja, troskovi, valuta);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Dodavanje posla");
            alert.setHeaderText("Da li ste sigurni da zelite da dodate novi posao");
            alert.setContentText("Klijent: " + p.getKlijent() + "\n" + "Nacin placanja: " + p.getNacinPlacanja() + "\n" +
                    "Cena: " + p.getCena() + "\n" + "Valuta: " + p.getValuta() + "\n" +
                    "Trenutni kurs: " + p.getKurs() + "\n" + "Datum: " + p.getDan() + "/" + p.getMesec() + "/" + p.getGodina() +
                    "\n" + "Troskovi: " + p.getTroskovi() + "\n" + "Opis: " + p.getOpisPosla());
            Optional<ButtonType> result = alert.showAndWait();
            try {
                if (result.get() == ButtonType.OK) {
                    googleDrive.initService();
                    googleDrive.addPosao(p);
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
