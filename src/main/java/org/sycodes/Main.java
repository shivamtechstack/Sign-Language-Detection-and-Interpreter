package org.sycodes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

public class Main extends Application {

    public static void main(String[] args) {
        System.loadLibrary("opencv_java4110");
       launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle("Sign Language Detector");
        stage.setScene(scene);
        stage.show();
    }
}