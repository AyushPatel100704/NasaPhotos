package com.ayushpatel.spacephotos.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.control.ScrollPane;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NASAController {

    public ScrollPane scrollPane;
    @FXML
    private Button fetchButton;

    @FXML
    private VBox imageContainer;

    @FXML
    private void fetchPhoto() {
        String apiKey = "9Nfa1CIEIdHIzdUmS3haZqrgheSgQ5GDaG7GVRJm";
        String requestUrl = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey + "&count=10";

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream responseStream = connection.getInputStream();

            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(new java.io.InputStreamReader(responseStream), JsonArray.class);

            imageContainer.getChildren().clear(); // Clear any existing content

            for (JsonElement element : jsonArray) {
                JsonObject photo = element.getAsJsonObject();

                String imageUrl = photo.get("url").getAsString();
                String explanation = photo.get("explanation").getAsString();
                String title = photo.get("title").getAsString();

                // Load and display the image
                Image image = new Image(imageUrl);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(500); // Adjust width as needed
                imageView.setFitHeight(600); // Adjust height as needed
                imageView.setPreserveRatio(true);
                imageView.getStyleClass().add("image");
                imageView.setSmooth(true);
                imageView.setCache(true);

                // Define the scale transition for hover effect
                ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(300), imageView);
                scaleTransition.setFromX(1.0);
                scaleTransition.setFromY(1.0);
                scaleTransition.setToX(1.1); // Scale up by 10%
                scaleTransition.setToY(1.1); // Scale up by 10%
                scaleTransition.setCycleCount(1);

                // Set up hover effects
                imageView.setOnMouseEntered(e -> scaleTransition.play());
                imageView.setOnMouseExited(e -> {
                    scaleTransition.setRate(-1.0);
                    scaleTransition.play();
                });

                // Create title text and description text
                Text titleText = new Text(title);
                titleText.getStyleClass().add("title-text");
                titleText.setStyle("-fx-font-size: 30px; -fx-fill: white; -fx-font-weight: bold; -fx-font-family: 'Comic Sans MS';");

                Text description = new Text(explanation);
                description.getStyleClass().add("text");
                description.setWrappingWidth(1350); // Ensure text wraps within the image width
                description.setStyle("-fx-font-size: 22px; -fx-fill: white; -fx-font-family: 'Georgia';");


                // Stack image, title, and description vertically
                VBox vbox = new VBox(10, imageView, titleText, description);
                vbox.setAlignment(javafx.geometry.Pos.CENTER); // Center align the contents
                vbox.setMaxWidth(500); // Increase width if needed
                vbox.setPrefWidth(500);
                vbox.getStyleClass().add("image-description");

                imageContainer.getChildren().add(vbox);
            }

            // Wrap the VBox in a ScrollPane
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(imageContainer);
            scrollPane.setFitToWidth(true); // Ensure ScrollPane adjusts to the width of the application

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
