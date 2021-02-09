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

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class coinflip
{
    private Boolean isPlayerTurn = false;

    public void imageClicked(MouseEvent event)
    {
        /* Decide whether or not the player starts as white (which starts first in Chess). */
        String choice = event.getPickResult().getIntersectedNode().getId();

        switch (choice)
        {
            case "playerWhite":
                isPlayerTurn = true;
                break;
            case "playerBlack":
                isPlayerTurn = false;
                break;
            case "playerRand":
                isPlayerTurn = (Math.random() < 0.5 ? true : false);
                System.out.println("Decided: " + (isPlayerTurn ? "true" : "false"));
                break;
        }

        closeStage(event);
    }

    public Boolean getPlayerTurnChoice()
    {
        return isPlayerTurn;
    }

    private void closeStage(MouseEvent event)
    {
        Node source = (Node)event.getSource();
        Stage stage = (Stage)source.getScene().getWindow();
        stage.close();
    }
}
