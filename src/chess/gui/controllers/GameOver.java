/*
    Group Names:
        - Damen DeBerry (@basicDamen)
        - James Grady (@JaymeAlann)
        - Tyra Buadoo (@misstj555)
        - Ashlei Williams (@AshW-2018)
        - Mahad Farah (@mfarah-ksu)
        - Mandela Issa-Boube (@aliamaza)
        - Shivank Rao (@shivankrao)
    Project: Chess with AI Agent
    Class: CS4850 - Senior Project
 */
package chess.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOver
{
    @FXML
    private Label playerWins;

    @FXML
    private Label aiWins;

    private int PlayerChoice;

    public void setWinner(String winner)
    {
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
    void playAgain(ActionEvent event)
    {
        PlayerChoice = -1;
        closeStage(event);
    }

    @FXML
    void endGame(ActionEvent event)
    {
        PlayerChoice = 1;
        closeStage(event);
    }

    private void closeStage(ActionEvent event)
    {
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }

    public int getPlayerChoice()
    {
        return PlayerChoice;
    }
}