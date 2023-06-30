package com.example.gastarbeiterservicefinal.view;

import com.example.gastarbeiterservicefinal.drive.GoogleDrive;
import com.example.gastarbeiterservicefinal.drive.model.Posao;
import com.example.gastarbeiterservicefinal.drive.model.Prihodi;
import com.example.gastarbeiterservicefinal.drive.model.Troskovi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TroskoviScene extends BorderPane {

    TableView<Troskovi> tableView = new TableView<>();

    public TroskoviScene(Stage parent, GoogleDrive googleDrive, double width, double height) {
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

        TableColumn<Troskovi, String> colGodina = new TableColumn<>("Godina");
        TableColumn<Troskovi, String> colMesec = new TableColumn<>("Mesec");
        TableColumn<Troskovi, String> colDan = new TableColumn<>("Dan");
        TableColumn<Troskovi, String> colSuma = new TableColumn<>("Suma");
        TableColumn<Troskovi, String> colKljucnaRec = new TableColumn<>("Kljucna Rec");
        TableColumn<Troskovi, String> colOpis = new TableColumn<>("Opis");

        colGodina.setCellValueFactory(new PropertyValueFactory<>("godina"));
        colMesec.setCellValueFactory(new PropertyValueFactory<>("mesec"));
        colDan.setCellValueFactory(new PropertyValueFactory<>("dan"));
        colSuma.setCellValueFactory(new PropertyValueFactory<>("suma"));
        colKljucnaRec.setCellValueFactory(new PropertyValueFactory<>("kljucnaRec"));
        colOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));

        colOpis.setCellFactory((tableColumn) -> {
            TableCell<Troskovi, String> tableCell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    this.setText(null);
                    this.setGraphic(null);

                    if(!empty) {
                        if(item.length() > 20) {
                            String newstr;
                            if(item.contains("\n")) {
                                newstr = item.substring(0, Math.min(item.length(), 10));
                            } else {
                                newstr = item.substring(0, Math.min(item.length(), 20));
                            }
                            newstr += "...";
                            this.setText(newstr);
                        } else {
                            this.setText(item);
                        }
                    }
                }
            };

            return tableCell;
        });
        tableView.getColumns().addAll(colGodina, colMesec, colDan, colSuma, colKljucnaRec, colOpis);
        tableView.setItems(googleDrive.getObservableTroskoviList());

        buttonNazad.setOnAction(e -> {
            MainScene mainScene = new MainScene(parent, googleDrive, width, height);
            parent.setScene(new Scene(mainScene, width, height));
        });

        buttonOpsirnije.setOnAction(e -> {
            Troskovi selected = tableView.getSelectionModel().getSelectedItem();
            OpsirnijeTroskoviScene opsirnijeScene = new OpsirnijeTroskoviScene(selected, parent, googleDrive, width, height);
            parent.setScene(new Scene(opsirnijeScene, width, height));
            System.out.println(selected);
        });

        this.setCenter(tableView);
        this.setBottom(hBox);
    }
}
