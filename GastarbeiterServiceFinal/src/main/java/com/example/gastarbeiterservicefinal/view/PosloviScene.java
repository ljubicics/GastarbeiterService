package com.example.gastarbeiterservicefinal.view;

import com.example.gastarbeiterservicefinal.drive.GoogleDrive;
import com.example.gastarbeiterservicefinal.drive.model.NacinPlacanja;
import com.example.gastarbeiterservicefinal.drive.model.Posao;
import com.example.gastarbeiterservicefinal.drive.model.Prihodi;
import com.example.gastarbeiterservicefinal.drive.model.Valuta;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PosloviScene extends BorderPane {
    TableView<Posao> tableView = new TableView<Posao>();
    public PosloviScene(Stage parent, GoogleDrive googleDrive, double width, double height) {
        googleDrive.initService();
        googleDrive.loadData();
        HBox hBox = new HBox(10);

        Button buttonOpsirnije = new Button("Opsirnije");
        Button buttonNazad = new Button("Nazad");

        hBox.getChildren().add(buttonNazad);
        hBox.getChildren().add(buttonOpsirnije);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10));

        TableColumn<Posao, String> colGodina = new TableColumn<>("Godina");
        TableColumn<Posao, String> colMesec = new TableColumn<>("Mesec");
        TableColumn<Posao, String> colDan = new TableColumn<>("Dan");
        TableColumn<Posao, String> colKlijent = new TableColumn<>("Klijent");
        TableColumn<Posao, Double> colCena = new TableColumn<>("Cena");
        TableColumn<Posao, Double> colKurs = new TableColumn<>("Kurs");
        TableColumn<Posao, NacinPlacanja> colNacinPlacanja = new TableColumn<>("Nacin Placanja");
        TableColumn<Posao, Valuta> colValuta = new TableColumn<>("Valuta");
        TableColumn<Posao, Double> colTroskovi = new TableColumn<>("Troskovi");

        colKlijent.setCellValueFactory(new PropertyValueFactory<>("klijent"));
        colCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
        colGodina.setCellValueFactory(new PropertyValueFactory<>("godina"));
        colMesec.setCellValueFactory(new PropertyValueFactory<>("mesec"));
        colDan.setCellValueFactory(new PropertyValueFactory<>("dan"));
        colKurs.setCellValueFactory(new PropertyValueFactory<>("kurs"));
        colNacinPlacanja.setCellValueFactory(new PropertyValueFactory<>("nacinPlacanja"));
        colValuta.setCellValueFactory(new PropertyValueFactory<>("valuta"));
        colTroskovi.setCellValueFactory(new PropertyValueFactory<>("troskovi"));

        tableView.getColumns().addAll( colGodina, colMesec, colDan,colKlijent, colCena, colKurs, colNacinPlacanja, colValuta, colTroskovi);
        tableView.setItems(googleDrive.getObservablePosloviList());

        buttonNazad.setOnAction(e -> {
            MainScene mainScene = new MainScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(mainScene, width, height));
        });

        buttonOpsirnije.setOnAction(e-> {
            Posao selected = tableView.getSelectionModel().getSelectedItem();
            OpsirnijeScene opsirnijeScene = new OpsirnijeScene(selected, parent, googleDrive, width, height);
            parent.setScene(new Scene(opsirnijeScene, width, height));
            System.out.println(selected);
        });

        this.setCenter(tableView);
        this.setBottom(hBox);
    }
}
