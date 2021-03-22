package chess.game;

import java.util.ArrayList;

/**
 * Master AI CLASS that each commanding distributed AI will be a piece of
 */
public abstract class AI {
    private final Piece corpCommander;
    private Board board;
    private Corp corp;
    private ArrayList<Piece> corpSubordinates;

    public AI(Piece corpCommander, Board board){
        this.corpCommander = corpCommander;
        this.board = board;
    }
    public abstract MoveHandler calculateBestMove();


    /** KING CORP **/
    public static class KingCorp extends AI{

        public KingCorp(Piece corpCommander, Board board) {
            super(corpCommander, board);
            setCorpSubordinates();
        }

        public void setCorpSubordinates(){
            super.corpSubordinates = super.board.getCorpPieces("AI_king");
            super.corp = super.corpSubordinates.get(0).getCorp();
        }

        @Override
        public MoveHandler calculateBestMove() {
            MoveHandler bestMove = null;
            double leastRisk = 1;
            for(Piece piece : super.corpSubordinates){
                ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(super.board);
                for(MoveHandler move : pieceMoves){
                    if(move.getMoveRiskPercentage() < leastRisk){
                        bestMove = move;
                        leastRisk = move.getMoveRiskPercentage();
                    }
                }
            }
            return bestMove;
        }


    }

    /** LEFT BISHOP CORP **/
    public static class KingBishopCorp extends AI{

        public KingBishopCorp(Piece corpCommander, Board board) {
            super(corpCommander, board);
            setCorpSubordinates();
        }

        public void setCorpSubordinates(){
            super.corpSubordinates = super.board.getCorpPieces("AI_kingsBishop");
            super.corp = super.corpSubordinates.get(0).getCorp();
        }

        @Override
        public MoveHandler calculateBestMove() {
            MoveHandler bestMove = null;
            double leastRisk = 1;
            for(Piece piece : super.corpSubordinates){
                ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(super.board);
                for(MoveHandler move : pieceMoves){
                    if(move.getMoveRiskPercentage() < leastRisk){
                        bestMove = move;
                        leastRisk = move.getMoveRiskPercentage();
                    }
                }
            }
            return bestMove;
        }

        private void scanBoard(){
            /**
             * Looks at the enemy pieces moves, see if current move is in capture radius
             */
        }
    }

    /** RIGHT BISHOP CORP **/
    public static class QueensBishopCorp extends AI{

        public QueensBishopCorp(Piece corpCommander, Board board) {
            super(corpCommander, board);
            setCorpSubordinates();
        }

        public void setCorpSubordinates(){
            super.corpSubordinates = super.board.getCorpPieces("AI_queensBishop");
            super.corp = super.corpSubordinates.get(0).getCorp();
        }

        @Override
        public MoveHandler calculateBestMove() {
            MoveHandler bestMove = null;
            double leastRisk = 1;
            for(Piece piece : super.corpSubordinates){
                ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(super.board);
                for(MoveHandler move : pieceMoves){
                    if(move.getMoveRiskPercentage() < leastRisk){
                        bestMove = move;
                        leastRisk = move.getMoveRiskPercentage();
                    }
                }
            }
            return bestMove;
        }
    }

}
