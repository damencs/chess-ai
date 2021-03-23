package chess.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Master AI CLASS that each commanding distributed AI will be a piece of
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
