package chess.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Master AI CLASS that each commanding distributed AI will be a piece of
 *
 * Started with a parent AI class that then distributes the AI pieces into child classes
 * according to their respective corps.
 *
 * Each child corp class take in the Commanding piece and the initial board.
 */
public abstract class AI {
    private Piece corpCommander;
    private Board board;
    private Corp corp;
    private String color;
    private ArrayList<Piece> corpSubordinates;

    public AI(Piece corpCommander, Board board){
        this.corpCommander = corpCommander;
        this.board = board;
    }
    public abstract MoveHandler calculateBestMove(Board board);
    public void setboard(Board board){
        this.board = board;
    }
    public String getColor(){return this.color;}

    /** KING CORP **/
    public static class KingCorp extends AI{


        public KingCorp(Piece corpCommander, Board board) {
            super(corpCommander, board);
            getCorpMembers(board);
            super.color = corpCommander.getColor();
        }

        @Override
        public MoveHandler calculateBestMove(Board board) {
            MoveHandler bestMove = null;
            double risk = 0;
            /** Loop Through each of the pieces in the corp */
            for(Piece piece : super.corpSubordinates){

                /** list of available moves to each individual piece**/
                ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(board);

                /** Loops Through each move that the individual piece can make **/
                for(MoveHandler move : pieceMoves){

                    /** Get all the enemy pieces that can possibly attack said move destination **/
                    List<Piece> enemyPieces = (super.color.equals("black")) ? board.getBlackPieces() : board.getWhitePieces();

                    /** Loops through each enemy that can attack said destination **/
                    for(Piece enemyPiece : enemyPieces){
                        for(MoveHandler enemyMoves : enemyPiece.determineMoves(board)){
                            if(move.getDestination() == enemyMoves.getDestination()){

                                /** Looks at the dice roll from the enemies perspective to calculate a capture risk
                                 * of moving to said tile. **/
                                ConquerSet riskSet = new ConquerSet(enemyPiece, piece);

                                /* Calculation is (6 - minimum value needed to capture)/ 6  */
                                double moveTempRisk = ((double)(6 - riskSet.getConquerSet() / 6));
                                /* The higher the rick value the better the move */
                                /* This is  because the higher risk, the less enemy pieces can reach said location */
                                if(moveTempRisk > risk){
                                    risk = moveTempRisk;
                                    bestMove = move;
                                }
                            }
                        }
                    }
                }
            }
            /* Return the best move */
            return bestMove;
        }

        public void getCorpMembers(Board board){
            super.corpSubordinates = board.getCorpPieces("AI_king");
        }
    }

    /** LEFT BISHOP CORP **/
    public static class KingBishopCorp extends AI{


        public KingBishopCorp(Piece corpCommander, Board board) {
            super(corpCommander, board);
            getCorpMembers(board);
            super.color = corpCommander.getColor();
        }

        @Override
        public MoveHandler calculateBestMove(Board board) {
            MoveHandler bestMove = null;
            double risk = 0;
            for(Piece piece : super.corpSubordinates){
                ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(board);
                for(MoveHandler move : pieceMoves){
                    List<Piece> enemyPieces = (super.color.equals("black")) ? board.getBlackPieces() : board.getWhitePieces();
                    for(Piece enemyPiece : enemyPieces){
                        for(MoveHandler enemyMoves : enemyPiece.determineMoves(board)){
                            if(move.getDestination() == enemyMoves.getDestination()){
                                ConquerSet riskSet = new ConquerSet(enemyPiece, piece);
                                double moveTempRisk = ((double)(6 - riskSet.getConquerSet() / 6));
                                if(moveTempRisk > risk){
                                    risk = moveTempRisk;
                                    bestMove = move;
                                }
                            }
                        }
                    }
                }
            }
            return bestMove;
        }

        public void getCorpMembers(Board board){
            super.corpSubordinates = board.getCorpPieces("AI_kingsBishop");
        }

    }

    /** RIGHT BISHOP CORP **/
    public static class QueensBishopCorp extends AI{


        public QueensBishopCorp(Piece corpCommander, Board board) {
            super(corpCommander, board);
            getCorpMembers(board);
            super.color = corpCommander.getColor();
        }

        @Override
        public MoveHandler calculateBestMove(Board board) {
            MoveHandler bestMove = null;
            double risk = 0;
            for(Piece piece : super.corpSubordinates){
                ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(board);
                for(MoveHandler move : pieceMoves){
                    List<Piece> enemyPieces = (super.color.equals("black")) ? board.getBlackPieces() : board.getWhitePieces();
                    for(Piece enemyPiece : enemyPieces){
                        for(MoveHandler enemyMoves : enemyPiece.determineMoves(board)){
                            if(move.getDestination() == enemyMoves.getDestination()){
                                ConquerSet riskSet = new ConquerSet(enemyPiece, piece);
                                double moveTempRisk = ((double)(6 - riskSet.getConquerSet() / 6));
                                if(moveTempRisk > risk){
                                    risk = moveTempRisk;
                                    bestMove = move;
                                }
                            }
                        }
                    }
                }
            }
            return bestMove;
        }

        public void getCorpMembers(Board board){
            super.corpSubordinates = board.getCorpPieces("AI_queensBishop");
        }
    }

}
