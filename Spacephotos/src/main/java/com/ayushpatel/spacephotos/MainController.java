package com.ayushpatel.spacephotos;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainController {

    // NASA API key and URL for fetching the Astronomy Picture of the Day (APOD)
    private static final String NASA_API_KEY = "9Nfa1CIEIdHIzdUmS3haZqrgheSgQ5GDaG7GVRJm";
    private static final String NASA_API_URL = "https://api.nasa.gov/planetary/apod?api_key=" + NASA_API_KEY;

    @FXML
    private void fetchPhoto() {
        try {
            // Create URL object with the NASA API URL
            URL url = new URL(NASA_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET"); // Set HTTP request method to GET

            // Check if the response code is 200 (OK)
            if (connection.getResponseCode() == 200) {
                // Read and parse the JSON response from the input stream
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

                // Extract the title and image URL from the JSON object
                String title = jsonObject.get("title").getAsString();
                String urlStr = jsonObject.get("url").getAsString();

                // Display the title and image URL in an alert dialog
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("NASA APOD"); // Set the title of the alert dialog
                alert.setHeaderText(title); // Set the header text with the title of the photo
                alert.setContentText("Image URL: " + urlStr); // Display the image URL in the content text
                alert.showAndWait(); // Show the alert dialog and wait for user interaction
            } else {
                // Print an error message if the API request failed
                System.out.println("API Request failed with status code: " + connection.getResponseCode());
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace if an exception occurs
        }
    }
}
