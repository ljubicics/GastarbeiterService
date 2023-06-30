package com.example.gastarbeiterservicefinal.view;

import com.example.gastarbeiterservicefinal.drive.GoogleDrive;
import com.example.gastarbeiterservicefinal.drive.model.Posao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OpsirnijeScene extends BorderPane {
    public OpsirnijeScene(Posao posao, Stage parent, GoogleDrive googleDrive, double width, double height) {
        VBox vBox = new VBox();
        HBox hBox = new HBox(10);
        VBox klijentVBox = new VBox();
        HBox nacinPlacanjaHBox = new HBox();
        VBox cenaVBox = new VBox();
        VBox troskoviVBox = new VBox();
        VBox opisVBox = new VBox();

        TextField klijentTF = new TextField();
        TextField nacinPlacanjaTF = new TextField();
        TextField cenaTF = new TextField();
        TextField troskoviTF = new TextField();
        TextArea opisTA = new TextArea();
        Button buttonNazad = new Button("Nazad");
        Label klijentLbl = new Label("Klijent");
        Label nacinPlacanjaLbl = new Label("Nacin placanja");
        Label cenaLbl = new Label("Cena");
        Label troskoviLbl = new Label("Troskovi");
        Label opisPoslaLbl = new Label("Opis posla");

        klijentTF.setDisable(true);
        klijentTF.setStyle("-fx-opacity: 1.0;");
        klijentTF.setText(posao.getKlijent());
        klijentVBox.getChildren().add(klijentLbl);
        klijentVBox.getChildren().add(klijentTF);
        klijentTF.setMaxWidth(200);
        vBox.getChildren().add(klijentVBox);
        klijentVBox.setAlignment(Pos.CENTER);

        nacinPlacanjaTF.setDisable(true);
        nacinPlacanjaTF.setStyle("-fx-opacity: 1.0;");
        nacinPlacanjaTF.setText(posao.getNacinPlacanja().toString());
        nacinPlacanjaHBox.getChildren().add(nacinPlacanjaLbl);
        nacinPlacanjaHBox.getChildren().add(nacinPlacanjaTF);
        vBox.getChildren().add(nacinPlacanjaHBox);
        nacinPlacanjaHBox.setAlignment(Pos.CENTER);
        nacinPlacanjaHBox.setSpacing(10);

        cenaTF.setDisable(true);
        cenaTF.setStyle("-fx-opacity: 1.0;");
        cenaTF.setText(String.valueOf(posao.getCena()));
        cenaVBox.getChildren().add(cenaLbl);
        cenaVBox.getChildren().add(cenaTF);
        cenaTF.setMaxWidth(200);
        vBox.getChildren().add(cenaVBox);
        cenaVBox.setAlignment(Pos.CENTER);

        troskoviTF.setDisable(true);
        troskoviTF.setStyle("-fx-opacity: 1.0;");
        troskoviTF.setText(String.valueOf(posao.getTroskovi()));
        troskoviVBox.getChildren().add(troskoviLbl);
        troskoviVBox.getChildren().add(troskoviTF);
        troskoviTF.setMaxWidth(200);
        vBox.getChildren().add(troskoviVBox);
        troskoviVBox.setAlignment(Pos.CENTER);

        opisTA.setDisable(true);
        opisTA.setStyle("-fx-opacity: 1.0;");
        opisTA.setText(posao.getOpisPosla());
        opisVBox.getChildren().add(opisPoslaLbl);
        opisVBox.getChildren().add(opisTA);
        opisTA.setMaxWidth(400);
        opisVBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(opisVBox);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        hBox.getChildren().add(buttonNazad);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10));

        buttonNazad.setOnAction(e -> {
            PosloviScene posloviScene = new PosloviScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(posloviScene, width, height));
        });

        this.setCenter(vBox);
        this.setBottom(hBox);
    }
}
