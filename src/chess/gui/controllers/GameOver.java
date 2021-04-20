package chess.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOver {


    @FXML
    private Button playAgainBtn;
    @FXML
    private Button quitGameBtn;
    @FXML
    private Label winnerText;

    private String winner;
    private int PlayerChoice;

    public void setWinner(String winner){
        this.winner = winner;
        winnerText.setText(winner + " Wins");
    }

    @FXML
    void playAgain(ActionEvent event){
        PlayerChoice = -1;
        System.out.println(PlayerChoice);
        closeStage(event);
    }

    @FXML
    void endGame(ActionEvent event){
        PlayerChoice = 1;
        System.out.println(PlayerChoice);
        closeStage(event);
    }

    private void closeStage(ActionEvent event)
    {
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public int getPlayerChoice(){
        return PlayerChoice;
    }

}
