package chess.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button playNowBtn;

    @FXML
    private Button watchGameBtn;

    @FXML
    private Button howToPlayBtn;

    @FXML
    private Button quitBtn;

    @FXML
    void quit(ActionEvent event) {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
