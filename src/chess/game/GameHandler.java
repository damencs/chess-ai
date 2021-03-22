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
package chess.game;

import chess.gui.controllers.DiceRoll;
import chess.gui.controllers.coinflip;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class GameHandler
{
    /* TODO: - Track the starting game time and compare it to current to display game time. */

    private Board board;
    private boolean isPlayerTurn;
    private boolean gameOver;
    private static AI.KingCorp kingCorp_AI;
    private static AI.KingBishopCorp kingBishopCorp_AI;
    private static AI.QueensBishopCorp queensBishopCorp_AI;

    public void setBoard(){
        board = Board.createInitialBoard(isPlayerTurn());
        kingCorp_AI = new AI.KingCorp(getBoard().getTile(3).getPiece(), board);
        kingBishopCorp_AI = new AI.KingBishopCorp(getBoard().getTile(2).getPiece(), board);
        queensBishopCorp_AI = new AI.QueensBishopCorp(getBoard().getTile(5).getPiece(), board);
    }
    public void setBoard(Board board){ this.board = board;}
    /* Return instance of board*/
    public Board getBoard() { return board; }

    public Tile[][] getBoardTiles(){
        Tile[][] tiles = new Tile[8][8];
        int index = 0;
        for(int row = 0; row < tiles.length; row++){
            for(int column = 0; column < tiles[row].length; column++){
                tiles[row][column] = board.getTile(index);
                index++;
            }
        }
        return tiles;
    }

    public void AI_makeMove(){
        MoveHandler move = kingCorp_AI.calculateBestMove();
    }


    /* Return whether or not it is the player's turn. */
    public boolean isPlayerTurn()
    {
        return isPlayerTurn;
    }

    /* Update the player turn within the GameHandler itself. */
    public void updatePlayerTurn(boolean update)
    {
        isPlayerTurn = update;
    }

    /* TODO: - Implement AI That will make its moves as soon as player ends turn */

}
