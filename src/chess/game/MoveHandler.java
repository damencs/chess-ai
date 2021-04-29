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
import java.util.ArrayList;


public abstract class MoveHandler
{
    protected final Board board;
    protected final Piece movingPiece;
    protected final int destination;
    private boolean gameOver = false;
    private String winner;

    private MoveHandler(final Board board, final Piece movingPiece, final int destination){
        this.board = board;
        this.movingPiece = movingPiece;
        this.destination = destination;
    }

    public boolean isGameOver() {return gameOver;}
    public String getWinner(){
        if(winner != null){
            return winner;
        }
        return null;
    }
    public Piece getMovingPiece(){return this.movingPiece; }

    public int getDestination() {return this.destination; }
    public abstract Board executeMove() throws IOException;
    public abstract Board unexecuteMove();
    public abstract Board temporaryExecuteMove();

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
        private Board.SetBoard setBoardMove = new Board.SetBoard();
        public String eventText = "";
        public boolean pieceMoved;
        private int originalDestination;
        private Board originalBoard;

        public Move(Board board, Piece movingPiece, int destination) {
            super(board, movingPiece, destination);
            this.originalBoard = board;
            this.originalDestination = movingPiece.getCoordinates();
        }

        public boolean getPieceMoved()
        {
            return pieceMoved;
        }


        @Override
        public String toString()
        {
            return eventText;
        }

        @Override
        public Board executeMove() throws IOException {
            Tile destinationTile = this.board.getTile(destination);
            if(!destinationTile.isOccupied()){

                for(Piece piece : this.board.getAlivePieces()){
                    if(!movingPiece.equals(piece)){
                        setBoardMove.setPiece(piece);
                    }
                }
                setBoardMove.setPiece(movingPiece.movePiece(destination));
                pieceMoved = true;
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
                int requiredRolled = conquerSet.getConquerSet();
                if(diceRoll > requiredRolled){

                    pieceMoved = true;
                    destinationTile.getPiece().changeCaptureStatus();
                    eventText = (movingPiece.isPlayerPiece() ? "[PL]" : "[AI]") + " ROLLED: " + diceRoll + " (REQ. " + requiredRolled + ") - SUCCESS";

                    if(destinationTile.getPiece().getName().equals("King")){
                        super.gameOver = true;
                        super.winner = (movingPiece.getCorp().isPlayerCorp()) ? "Player" : "AI";
                    }
                    if(destinationTile.getPiece().getColor().equals("white")){
                        board.getWhitePieces().remove(destinationTile.getPiece());
                        board.addToBlackGraveyard(destinationTile.getPiece());
                    }else{
                        board.getBlackPieces().remove(destinationTile.getPiece());
                        board.addToWhiteGraveyard(destinationTile.getPiece());
                    }

                    reassignCorp(destinationTile.getPiece());

                    //Board.SetBoard setBoardMove = new Board.SetBoard();
                    for(Piece piece : this.board.getAlivePieces()){
                        if(!movingPiece.equals(piece)){
                            setBoardMove.setPiece(piece);
                        }
                    }
                    if(movingPiece.name.equals("Rook"))
                    {
                        setBoardMove.setPiece(movingPiece.movePiece(movingPiece.coordinates));
                    }
                    else{
                        setBoardMove.setPiece(movingPiece.movePiece(destination));
                    }
                    return(setBoardMove.build());
                }

                pieceMoved = false;
                eventText = (movingPiece.isPlayerPiece() ? "[PL]" : "[AI]") + " ROLLED: " + diceRoll + " (REQ. " + requiredRolled + ") - FAIL";
            }
            return(board);
        }

        @Override
        public Board unexecuteMove() {
            setBoardMove = new Board.SetBoard();
            this.originalBoard.getAlivePieces().forEach(setBoardMove::setPiece);
            return setBoardMove.build();
        }

        @Override
        public Board temporaryExecuteMove() {
            Tile destinationTile = this.board.getTile(destination);
            if(!destinationTile.isOccupied()){

                for(Piece piece : this.board.getAlivePieces()){
                    if(!movingPiece.equals(piece)){
                        setBoardMove.setPiece(piece);
                    }
                }
                setBoardMove.setPiece(movingPiece.movePiece(destination));
                pieceMoved = true;
                return(setBoardMove.build());
            }else if(!destinationTile.getPiece().getColor().equals(movingPiece.getColor())){

                pieceMoved = true;
                destinationTile.getPiece().changeCaptureStatus();
                if(destinationTile.getPiece().getName().equals("King")){

                }

                if(destinationTile.getPiece().getColor().equals("white")){
                    board.getWhitePieces().remove(destinationTile.getPiece());
                }else{
                    board.getBlackPieces().remove(destinationTile.getPiece());
                }

                for(Piece piece : this.board.getAlivePieces()){
                    if(!movingPiece.equals(piece)){
                        setBoardMove.setPiece(piece);
                    }
                }
                if(movingPiece.name.equals("Rook"))
                {
                    setBoardMove.setPiece(movingPiece.movePiece(movingPiece.coordinates));
                }
                else{
                    setBoardMove.setPiece(movingPiece.movePiece(destination));
                }
                return(setBoardMove.build());
            }
            return(board);
        }


        public void reassignCorp(Piece piece){
            String pieceName = piece.getName();
            String pieceCorp = piece.getCorp().getCorpName();

            if(pieceName.equals("Bishop") || pieceName.equals("AIBishop"))
            {
                ArrayList<Piece> corp = piece.getColor().equals("black") ? this.board.getBlackCorpPieces(pieceCorp) : this.board.getWhiteCorpPieces(pieceCorp);

                if (piece.isPlayerPiece())
                {
                    for(Piece pieces : corp)
                    {
                        pieces.setCorp(board.getPlayerKing().getCorp());
                    }
                }
                else
                {
                    for(Piece pieces : corp)
                    {
                        pieces.setCorp(board.getAIKing().getCorp());
                    }
                }
            }
        }

    }
}
