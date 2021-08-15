package com.ldapjavafx;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {
    private  LogginScreen logginScreen;

    @Override
    public void start(Stage stage) throws IOException {
//        var javaVersion = SystemInfo.javaVersion();
//        var javafxVersion = SystemInfo.javafxVersion();

//        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        var scene = new Scene(new StackPane(label), 640, 480);


        Parent scene = FXMLLoader.load(getClass().getResource("/logginscreen.fxml"));

        stage.setScene(new Scene(scene,400,400));
        stage.setTitle("Ramones Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}