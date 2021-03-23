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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public abstract class MoveHandler
{
    protected final Board board;
    protected final Piece movingPiece;
    protected final int destination;

    private MoveHandler(final Board board, final Piece movingPiece, final int destination){
        this.board = board;
        this.movingPiece = movingPiece;
        this.destination = destination;
    }

    public Piece getMovingPiece(){return this.movingPiece; }

    public int getDestination() {return this.destination; }
    public abstract Board executeMove() throws IOException;

    public static final class EmptyMove extends MoveHandler{

        EmptyMove(Board board, Piece movingPiece, int destination) {
            super(board, movingPiece, destination);
        }

        @Override
        public Board executeMove() throws IOException {
            return board;
        }
    }

    /**
     * Allows each piece to create a list of Potential moves without actually taking the move.
     *
     * The piece will determine each move it can take and make a list of moves with set destination.
     *  This move can then be called by the GUI or the AI and executed at a later time.
     *
     *  Each time a piece is moved, a new list of moves is then determined and stored in
     *  advance for said piece.
     */
    public static final class Move extends MoveHandler
    {
        private final Board.SetBoard setBoardMove = new Board.SetBoard();

        public Move(Board board, Piece movingPiece, int destination) {
            super(board, movingPiece, destination);
        }

        @Override
        public Board executeMove() throws IOException {
            Tile destinationTile = this.board.getTile(destination);
            if(!destinationTile.isOccupied()){
                //Board.SetBoard setBoardMove = new Board.SetBoard();
                for(Piece piece : this.board.getAlivePieces()){
                    if(!movingPiece.equals(piece)){
                        setBoardMove.setPiece(piece);
                    }
                }
                setBoardMove.setPiece(movingPiece.movePiece(destination));
                return(setBoardMove.build());
            }else if(!destinationTile.getPiece().getColor().equals(movingPiece.getColor())){

                /* DICE ROLL */
                FXMLLoader fxmlloader = new FXMLLoader();
                fxmlloader.setLocation(getClass().getResource("/chess/gui/fxml/diceroll.fxml"));
                Parent parent = fxmlloader.load();
                DiceRoll diceDecision = fxmlloader.getController();
                diceDecision.setAttAndDefPieces(movingPiece, destinationTile.getPiece());
                Scene scene = new Scene(parent, 300, 175);
                scene.setFill(Color.TRANSPARENT);

                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                /* ****************************************************** */

                ConquerSet conquerSet = new ConquerSet(movingPiece, destinationTile.getPiece());

                // TODO: Make dice roll implementation different for knight as it can attack multiple times
                int diceRoll = diceDecision.getDiceNumber();
                if(diceRoll > conquerSet.getConquerSet()){

                    if(destinationTile.getPiece().getColor().equals("white")){
                        board.getWhitePieces().remove(destinationTile.getPiece());
                    }else{
                        board.getBlackPieces().remove(destinationTile.getPiece());
                    }

                    //Board.SetBoard setBoardMove = new Board.SetBoard();
                    for(Piece piece : this.board.getAlivePieces()){
                        if(!movingPiece.equals(piece)){
                            setBoardMove.setPiece(piece);
                        }
                    }
                    setBoardMove.setPiece(movingPiece.movePiece(destination));
                    return(setBoardMove.build());
                }
            }
            return(board);
        }
    }
}
