package chess.game;

import javafx.scene.image.Image;

import java.util.*;

public abstract class Piece
{
    protected final int coordinates;
    protected final String color;
    protected final String pieceCorp;
    protected final String name;

    Piece(final int coordinates, final String color, final String pieceCorp, String name)
    {
        this.coordinates = coordinates;
        this.color = color;
        this.pieceCorp = pieceCorp;
        this.name = name;
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
    public abstract ArrayList<MoveHandler> determineAttacks(final Board board);

    /**
     * Knight Class
     */
    public static class Knight extends Piece
    {
        private final static int[] knightMoveOffset = {};
        //private final static int[] knightAttackOffset = {};

        Knight(final int coordinates, final String color, final String pieceCorp, String name)
        {
            super(coordinates, color, pieceCorp, name);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Knight(newCoordinates, this.color, this.pieceCorp, this.name);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            return null;
        }

        @Override
        public ArrayList<MoveHandler> determineAttacks(Board board) {
            return null;
        }
    }

    /**
     * King Class
     */
    public static class King extends Piece
    {
        private final static int[] kingMoveOffset = {};
        //private final static int[] kingAttackOffset = {};

        King(final int coordinates, final String color, final String pieceCorp, String name)
        {
            super(coordinates, color, pieceCorp, name);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new King(newCoordinates, this.color, this.pieceCorp, this.name);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            return null;
        }

        @Override
        public ArrayList<MoveHandler> determineAttacks(Board board) {
            return null;
        }
    }

    /**
     * Bishop Class
     */
    public static class Bishop extends Piece
    {
        private final static int[] bishopMoveOffset = {};
        //private final static int[] bishopAttackOffset = {};

        Bishop(final int coordinates, final String color, final String pieceCorp, String name)
        {
            super(coordinates, color, pieceCorp, name);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Bishop(newCoordinates, this.color, this.pieceCorp, this.name);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            return null;
        }

        @Override
        public ArrayList<MoveHandler> determineAttacks(Board board) {
            return null;
        }
    }

    /**
     * Queen Class
     */
    public static class Queen extends Piece
    {
        private final static int[] queenMoveOffset = {};
        //private final static int[] queenAttackOffset = {};

        Queen(final int coordinates, final String color, final String pieceCorp, String name)
        {
            super(coordinates, color, pieceCorp, name);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Queen(newCoordinates, this.color, this.pieceCorp, this.name);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            return null;
        }

        @Override
        public ArrayList<MoveHandler> determineAttacks(Board board) {
            return null;
        }
    }

    /**
     * Rook Class
     */
    public static class Rook extends Piece
    {
        private final static int[] rookMoveOffset = {-1,-7,-8,-9, 1, 7, 8, 9};
        private final static int[] rookAttackOffset = {-27,-24,-21,-18,-16,-14,-9,-8,-7,-3,-2,-1,1,2,3,7,8,9,14,16,18,21,24,27};


        Rook(final int coordinates, final String color, final String pieceCorp, String name)
        {
            super(coordinates, color, pieceCorp, name);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Rook(newCoordinates, this.color, this.pieceCorp, this.name);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {

            final ArrayList<MoveHandler> moves = new ArrayList<>();

            /*for(final int destinationOffset : rookMoveOffset){
                int destination = this.coordinates + destinationOffset;
                if(true ){
                    final Tile destinationTile = board.getTile(destination);

                    if(!destinationTile.isOccupied()){
                        moves.add(new MoveHandler());
                    }
                    else{
                        final Piece destinationPiece = destinationTile.getPiece();
                        final String destinationPieceColor = destinationPiece.getColor();

                        if(this.color.equals(destinationPieceColor)){
                            moves.add(new MoveHandler());
                        }
                    }
                }
            }*/
            return moves;
        }

        @Override
        public ArrayList<MoveHandler> determineAttacks(Board board) {

            final ArrayList<MoveHandler> attacks = new ArrayList<>();
            /*or(final int attackDestinationOffset : rookAttackOffset){
                int attackDestination = this.coordinates + attackDestinationOffset;
                if(true *//* Valid tile Coordinates *//*){
                    final Tile destinationTile = board.getTile(attackDestination);

                    if(destinationTile.isOccupied()){
                        final Piece destinationPiece = destinationTile.getPiece();
                        final PieceColor destinationPieceColor = destinationPiece.getColor();

                        if(this.color != destinationPieceColor){
                            attacks.add(new MoveHandler());
                        }
                    }
                }
            }*/
            return attacks;
        }
    }

    /**
     * Pawn Class
     */
    public static class Pawn extends Piece
    {
        private final static int[] pawnOffset = {};
        //private final static int[] pawnAttackOffset = {};

        Pawn(final int coordinates, final String color, final String pieceCorp, String name)
        {
            super(coordinates, color, pieceCorp, name);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Pawn(newCoordinates, this.color, this.pieceCorp, this.name);
        }


        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            return null;
        }

        @Override
        public ArrayList<MoveHandler> determineAttacks(Board board) {
            return null;
        }
    }


}
