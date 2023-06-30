package com.example.gastarbeiterservicefinal.view;

import com.example.gastarbeiterservicefinal.drive.GoogleDrive;
import com.example.gastarbeiterservicefinal.drive.model.NacinPlacanja;
import com.example.gastarbeiterservicefinal.drive.model.Posao;
import com.example.gastarbeiterservicefinal.drive.model.Prihodi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class PrihodiScene extends BorderPane {
    TableView<Prihodi> tableView = new TableView<>();
    public PrihodiScene(Stage parent, GoogleDrive googleDrive, double width, double height) {
        googleDrive.initService();
        googleDrive.loadData();
        VBox vBox = new VBox(10);
        HBox hBox1 = new HBox(10);
        HBox hBox2 = new HBox(10);

        Button buttonOpsirnije = new Button("Opsirnije");
        Button buttonNazad = new Button("Nazad");
        Label ukupnoLbl = new Label("Ukupno prihodi");
        TextField ukupnoTF = new TextField();

        hBox1.getChildren().add(buttonNazad);
        hBox1.getChildren().add(buttonOpsirnije);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.setSpacing(20);
        hBox1.setPadding(new Insets(10));

        TableColumn<Prihodi, String> colGodina = new TableColumn<>("Godina");
        TableColumn<Prihodi, String> colMesec = new TableColumn<>("Mesec");
        TableColumn<Prihodi, String> colMihailo = new TableColumn<>("Mihailo");
        TableColumn<Prihodi, String> colNikola = new TableColumn<>("Nikola");
        TableColumn<Prihodi, String> colKasa = new TableColumn<>("Kasa");

        colGodina.setCellValueFactory(new PropertyValueFactory<>("godina"));
        colMesec.setCellValueFactory(new PropertyValueFactory<>("mesec"));
        colMihailo.setCellValueFactory(new PropertyValueFactory<>("mihailo"));
        colNikola.setCellValueFactory(new PropertyValueFactory<>("zdravko"));
        colKasa.setCellValueFactory(new PropertyValueFactory<>("kasa"));

        tableView.getColumns().addAll(colGodina, colMesec, colMihailo, colNikola, colKasa);
        tableView.setItems(googleDrive.getObservablePrihodiList());

        List<Posao> posaoList = googleDrive.getPosloviList();
        double ukupnoPrihodi = 0;

        for (int i = 0; i < posaoList.size(); i++) {
            if(posaoList.get(i).getGodina() == LocalDate.now().getYear() && (posaoList.get(i).getNacinPlacanja().equals(NacinPlacanja.DINARSKI) || posaoList.get(i).getNacinPlacanja().equals(NacinPlacanja.DEVIZNI))) {
                ukupnoPrihodi += posaoList.get(i).getPrihodi().getKasa();
                ukupnoPrihodi += posaoList.get(i).getPrihodi().getMihailo();
                ukupnoPrihodi += posaoList.get(i).getPrihodi().getZdravko();
            }
        }

        ukupnoLbl.setText("Ukupno prihodi " + LocalDate.now().getYear());
        ukupnoTF.setDisable(true);
        ukupnoTF.setStyle("-fx-opacity: 1.0;");
        ukupnoTF.setText(String.valueOf(ukupnoPrihodi));

        hBox2.getChildren().addAll(ukupnoLbl, ukupnoTF);

        vBox.getChildren().addAll(hBox2, hBox1);
        vBox.setPadding(new Insets(10));
        buttonNazad.setOnAction(e -> {
            MainScene mainScene = new MainScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(mainScene, width, height));
        });

        this.setCenter(tableView);
        this.setBottom(vBox);
    }
}
