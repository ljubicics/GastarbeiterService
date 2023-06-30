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

public class MainStage extends Stage {

    public MainStage() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        Double width = primaryScreenBounds.getWidth()/2;
        Double height = primaryScreenBounds.getHeight()/2;
        GoogleDrive googleDrive = new GoogleDrive();
        MainScene mainScene = new MainScene(this, googleDrive, width, height);
        Scene scene = new Scene(mainScene, width, height);
        this.setTitle("GastarbeiterService");
        this.setScene(scene);
    }

}
