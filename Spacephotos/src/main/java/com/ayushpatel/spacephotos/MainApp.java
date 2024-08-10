package com.ayushpatel.spacephotos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file from the correct path
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        VBox root = loader.load();

        // Create a new Scene with the root node
        Scene scene = new Scene(root, 800, 600);

        // Apply CSS from the correct path
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        // Set up the stage
        primaryStage.setTitle("Space Photos");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
