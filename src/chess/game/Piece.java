package chess.game;

import javafx.scene.image.Image;

import java.util.*;

public abstract class Piece
{
    protected final int coordinates;
    protected final String color;
    protected final String pieceCorp;
    protected final String name;
    protected final int offsetMultiplier;

    Piece(final int coordinates, final String color, final String pieceCorp, String name, int offsetMultiplier)
    {
        this.coordinates = coordinates;
        this.color = color;
        this.pieceCorp = pieceCorp;
        this.name = name;
        this.offsetMultiplier = offsetMultiplier;
    }

    public int getCoordinates(){ return this.coordinates; }
    public String getColor(){ return this.color; }
    public String getName(){ return this.name; }
    public Image getImage()
    {
        return new Image("chess/gui/images/" + color + name + ".png", 60, 60, false, false);
    }

    public abstract Piece movePiece(int newCoordinates);
    public abstract ArrayList<MoveHandler> determineMoves(final Board board);

    /**
     * Knight Class
     */
    public static class Knight extends Piece
    {
        private final static int[] KNIGHT_OFFSET = {};

        Knight(final int coordinates, final String color, final String pieceCorp, String name, int offsetMultiplier)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Knight(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            return null;
        }

    }

    /**
     * King Class
     * : Can move any direction
     * : Does not have to move in a straight line
     * : Cannot jump or pass through occupied tiles
     */
    public static class King extends Piece
    {
        private final static int[] KING_OFFSET = {};

        King(final int coordinates, final String color, final String pieceCorp, String name, int offsetMultiplier)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new King(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            ArrayList<MoveHandler> moves = new ArrayList<>();
            return moves;
        }

    }

    /**
     * Bishop Class
     */
    public static class Bishop extends Piece
    {
        private final static int[] BISHOP_OFFSET = {7,8,9};

        Bishop(final int coordinates, final String color, final String pieceCorp, String name, int offsetMultiplier)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Bishop(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            ArrayList<MoveHandler> moves = new ArrayList<>();
            for( int offset : BISHOP_OFFSET){
                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);
                if(offsetDestination > 0 && offsetDestination < 63){
                    moves.add(new MoveHandler.Move(board, this, offsetDestination));
                }
            }
            return moves;
        }

    }

    /**
     * Queen Class
     */
    public static class Queen extends Piece
    {
        private final static int[] queenMoveOffset = {};

        Queen(final int coordinates, final String color, final String pieceCorp, String name, int offsetMultiplier)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Queen(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            return null;
        }

    }

    /**
     * Rook Class
     */
    public static class Rook extends Piece
    {
        private final static int[] ROOK_OFFSET = {-1,-7,-8,-9, 1, 7, 8, 9};
        private final static int[] ROOK_ATK_OFFSET = {-27,-24,-21,-18,-16,-14,-9,-8,-7,-3,-2,-1,1,2,3,7,8,9,14,16,18,21,24,27};


        Rook(final int coordinates, final String color, final String pieceCorp, String name, int offsetMultiplier)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Rook(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {

            ArrayList<MoveHandler> moves = new ArrayList<>();
            for(int offset : ROOK_OFFSET){
                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);
                if(offsetDestination > 0 && offsetDestination < 63){
                    moves.add(new MoveHandler.Move(board, this, offsetDestination));
                }
            }
            for(int offset : ROOK_ATK_OFFSET){
                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);
                if(offsetDestination > 0 && offsetDestination < 63){
                    if(board.getTile(offsetDestination).isOccupied()){
                        moves.add(new MoveHandler.Move(board, this, offsetDestination));
                    }
                }
            }
            return moves;
        }
    }

    /**
     * Pawn Class
     */
    public static class Pawn extends Piece
    {
        private final static int[] PAWN_OFFSET = {7,8,9};

        Pawn(final int coordinates, final String color, final String pieceCorp, String name, int offsetMultiplier)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Pawn(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier);
        }


        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            ArrayList<MoveHandler> moves = new ArrayList<MoveHandler>();
            for(int offset : PAWN_OFFSET){
                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);
                if(offsetDestination > 0 && offsetDestination < 63){
                    moves.add(new MoveHandler.Move(board, this, offsetDestination));
                }
            }
            return moves;
        }
    }


}
