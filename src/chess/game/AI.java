package chess.game;
import java.util.ArrayList;


public class AI {
    static Piece AICommander;



    //Returns empty squares around king
    static ArrayList getEmptySquares(Board board)
    {
        ArrayList<Integer> emptySquares = new ArrayList<Integer>();
        return null;
    }
    //retreat move
    static AIMove retreatMove(Board board)
    {
        AIMove retreatMove = null;
        int commanderRow = AICommander.coordinates;

        return null;
    }

    //returns threats
    static ArrayList getThreats()
    {
        ArrayList<AIMove> threats = new ArrayList<AIMove>();


        return threats;
    }

    //rates moves and selects the best one
    static AIMove defendCommander()
    {

        return null;
    }

    //returns arraylist of cells near commander that can attack
    static ArrayList squaresAttacking(Board board)
    {
        ArrayList<Integer> dangerSquares = AICommander.getSurroundingTiles(board);


        return dangerSquares;
    }
}/**/