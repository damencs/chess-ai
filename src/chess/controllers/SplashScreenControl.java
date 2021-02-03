package chess.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

// Controller for Splash Screen
public class SplashScreenControl implements Initializable {
    @FXML
    public static Label label; // static label to receive progress of program loading

    @FXML
    private Label progress; // Actual loading label on splash screen


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        label = progress;
    }
}
