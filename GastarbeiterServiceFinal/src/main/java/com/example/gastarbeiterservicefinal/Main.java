package com.example.gastarbeiterservicefinal;

import com.example.gastarbeiterservicefinal.view.MainStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        MainStage mainStage = new MainStage();
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}