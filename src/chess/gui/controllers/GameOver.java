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
    private Label playerWins;
    @FXML
    private Label aiWins;

    private String winner;
    private int PlayerChoice;

    public void setWinner(String winner){
        this.winner = winner;
        if (winner == "Player")
        {
            playerWins.setDisable(false);
            playerWins.setOpacity(1.0);
        }
        else if (winner == "AI")
        {
            aiWins.setDisable(false);
            aiWins.setOpacity(1.0);
        }
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
