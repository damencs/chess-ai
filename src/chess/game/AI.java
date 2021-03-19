package chess.game;

import java.util.ArrayList;

/**
 * Master AI CLASS that each commanding distributed AI will be a piece of
 */
public class AI {
    private final Piece corpCommander;
    private ArrayList<Piece> corpSubordinates;

    public AI(Piece corpCommander){
        this.corpCommander = corpCommander;
    }

    public void addSubordinates(Piece piece){
        if(!corpSubordinates.contains(piece)){
            corpSubordinates.add(piece);
        }
    }

    /** KING CORP **/
    public static class KingCorp extends AI{

        public KingCorp(Piece corpCommander) {
            super(corpCommander);
        }
    }

    /** LEFT BISHOP CORP **/
    public static class KingBishopCorp extends AI{

        public KingBishopCorp(Piece corpCommander) {
            super(corpCommander);
        }
    }

    /** RIGHT BISHOP CORP **/
    public static class QueensBishopCorp extends AI{

        public QueensBishopCorp(Piece corpCommander) {
            super(corpCommander);
        }
    }

}
