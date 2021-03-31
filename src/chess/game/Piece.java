package chess.game;

import javafx.scene.image.Image;

import java.util.*;

public abstract class Piece
{
    protected final int coordinates;
    protected final String color;
    protected final Corp pieceCorp;
    protected final String name;
    protected final int offsetMultiplier;
    protected final int pieceWeight;
    protected final boolean isPlayerPiece;

    Piece(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean isPlayerPiece)
    {
        this.coordinates = coordinates;
        this.color = color;
        this.pieceCorp = pieceCorp;
        this.name = name;
        this.offsetMultiplier = offsetMultiplier;
        this.pieceWeight = pieceWeight;
        this.isPlayerPiece = isPlayerPiece;
    }

    public int getCoordinates(){ return this.coordinates; }
    public String getColor(){ return this.color; }
    public String getName(){ return this.name; }
    public Boolean isPlayerPiece() { return this.isPlayerPiece; }
    public Corp getCorp(){return pieceCorp; }
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
        private final static int[] KNIGHT_OFFSET = {-1,-9,-8,-7, 1, 9, 8, 7};
        private final Stack<Tile> queueStack = new Stack<>();
        private final ArrayList<Integer> validMovesCoordinates = new ArrayList<>();
        private final ArrayList<Tile> visitedTiles = new ArrayList<>();

        Knight(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Knight(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            ArrayList<MoveHandler> moves = new ArrayList<>();
            int row = (int) Math.floor((double) this.coordinates/8);
            int column = this.coordinates % 8;
            findValidTiles(board, this.coordinates, row, column);

            for(int destinationCoord : validMovesCoordinates){
                moves.add(new MoveHandler.Move(board, this, destinationCoord));
            }
            return moves;
        }

        /**
         * TODO: Need to fix minor issue of allowed tiles
         * @param board needed for location of all current pieces
         * @param tileCoordinates location of tile being passed through recursive
         * @param initRow initial row of the knight
         * @param initCol initial column of the knight
         */
        private void findValidTiles(Board board, int tileCoordinates, int initRow, int initCol){

            if(valid(tileCoordinates)){
                for(int offset : KNIGHT_OFFSET){
                    if(valid(tileCoordinates + offset)){
                        if(!board.getTile(tileCoordinates + offset).isOccupied()){
                            if(!visitedTiles.contains(board.getTile(tileCoordinates + offset))){
                                queueStack.add(board.getTile(tileCoordinates + offset));
                            }
                        }else if(board.getTile(tileCoordinates+offset).isOccupied()){
                            if(!board.getTile(tileCoordinates + offset).getPiece().getColor().equals(this.color)){
                                if(!visitedTiles.contains(board.getTile(tileCoordinates + offset))){
                                    queueStack.add(board.getTile(tileCoordinates + offset));}}}}}
            }

            while(!queueStack.isEmpty()){
                Tile temp = queueStack.pop();
                visitedTiles.add(temp);
                int destRow = (int) Math.floor((double) temp.getCoordinates()/8);
                int destColumn = temp.getCoordinates() % 8;
                if((initRow - destRow > 5 || initRow - destRow < -5) || (initCol - destColumn > 5 || initCol - destColumn < -5)){return;}
                else if(!validMovesCoordinates.contains(temp.getCoordinates())){
                    validMovesCoordinates.add(temp.getCoordinates());
                    findValidTiles(board, temp.getCoordinates(), initRow, initCol);}}
        }

        private boolean valid(int coordinate){
            boolean safe = false;
            if(coordinate >= 0 && coordinate <= 63){
                safe = true;
            }
            return safe;
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
        private final static int[] KING_OFFSET = {-1,-9,-8,-7, 1, 9, 8, 7};
        private final Stack<Tile> queueStack = new Stack<>();
        private final ArrayList<Integer> validMovesCoordinates = new ArrayList<>();
        private final ArrayList<Tile> visitedTiles = new ArrayList<>();

        King(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new King(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            ArrayList<MoveHandler> moves = new ArrayList<MoveHandler>();
            int row = (int) Math.floor(this.coordinates/8);
            int column = this.coordinates % 8;
            findValidTiles(board, this.coordinates, row, column);

            for(int destinationCoord : validMovesCoordinates){
                moves.add(new MoveHandler.Move(board, this, destinationCoord));
            }
            return moves;
        }

        /**
         * TODO: Need to fix minor issue of allowed tiles
         * @param board needed for location of all current pieces
         * @param tileCoordinates location of tile being passed through recursive
         * @param initRow initial row of the knight
         * @param initCol initial column of the knight
         */
        private void findValidTiles(Board board, int tileCoordinates, int initRow, int initCol){

            if(valid(tileCoordinates)){
                for(int offset : KING_OFFSET){
                    if(valid(tileCoordinates + offset)){
                        if(!board.getTile(tileCoordinates + offset).isOccupied()){
                            if(!visitedTiles.contains(board.getTile(tileCoordinates + offset))){
                                queueStack.add(board.getTile(tileCoordinates + offset)); }}
                        else if(board.getTile(tileCoordinates+offset).isOccupied()){
                            if(!board.getTile(tileCoordinates + offset).getPiece().getColor().equals(this.color)){
                                if(!visitedTiles.contains(board.getTile(tileCoordinates + offset))){
                                    queueStack.add(board.getTile(tileCoordinates + offset));}}}}}
            }

            while(!queueStack.isEmpty()){
                Tile temp = queueStack.pop();
                visitedTiles.add(temp);
                int destRow = (int) Math.floor(temp.getCoordinates()/8);
                int destColumn = temp.getCoordinates() % 8;
                if((initRow - destRow > 3 || initRow - destRow < -3) || (initCol - destColumn > 3 || initCol - destColumn < -3)){return;}
                else if(!validMovesCoordinates.contains(temp.getCoordinates())){
                    validMovesCoordinates.add(temp.getCoordinates());
                    findValidTiles(board, temp.getCoordinates(), initRow, initCol);}}
        }

        private boolean valid(int coordinate){
            boolean safe = false;
            if(coordinate >= 0 && coordinate <= 63){
                safe = true;
            }
            return safe;
        }

    }

    /**
     * Bishop Class
     */
    public static class Bishop extends Piece
    {
        private final static int[] BISHOP_OFFSET = {7,8,9};

        Bishop(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Bishop(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            ArrayList<MoveHandler> moves = new ArrayList<>();
            for( int offset : BISHOP_OFFSET){
                //created this variable to hold the calculations for the offset and multiplier
                int calculatedOffset = this.offsetMultiplier*offset;


                //fixes the way the pieces interact with the edge of the board.
                if(this.coordinates %8 ==7 && calculatedOffset == 7)
                    continue;
                if(this.coordinates %8 ==0 && calculatedOffset == 9)
                    continue;

                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);
                if(valid(offsetDestination)){
                    if(offsetDestination > 0 && offsetDestination < 63 && !board.getTile(offsetDestination).isOccupied()){
                        moves.add(new MoveHandler.Move(board, this, offsetDestination));
                    }else if(offsetDestination > 0 && offsetDestination < 63 && board.getTile(offsetDestination).isOccupied()){
                        if(!board.getTile(offsetDestination).getPiece().getColor().equals(this.getColor())){
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
                    }
                }
            }
            return moves;
        }

        private boolean valid(int coordinate){
            boolean safe = false;
            if(coordinate >= 0 && coordinate <= 63 && coordinate != this.coordinates){
                safe = true;
            }
            return safe;
        }

    }

    /**
     * Queen Class
     * : Can move any direction
     * : Does not have to move in a straight line
     * : Cannot jump or pass through occupied tiles
     */
    public static class Queen extends Piece
    {
        private final static int[] QUEEN_OFFSET = {-1,-9,-8,-7, 1, 9, 8, 7};
        private final Stack<Tile> queueStack = new Stack<>();
        private final ArrayList<Integer> validMovesCoordinates = new ArrayList<>();
        private final ArrayList<Tile> visitedTiles = new ArrayList<>();

        Queen(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Queen(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {
            ArrayList<MoveHandler> moves = new ArrayList<MoveHandler>();
            int row = (int) Math.floor(this.coordinates/8);
            int column = this.coordinates % 8;
            findValidTiles(board, this.coordinates, row, column);

            for(int destinationCoord : validMovesCoordinates){
                moves.add(new MoveHandler.Move(board, this, destinationCoord));
            }
            return moves;
        }

        /**
         * TODO: Need to fix minor issue of allowed tiles
         * @param board needed for location of all current pieces
         * @param tileCoordinates location of tile being passed through recursive
         * @param initRow initial row of the knight
         * @param initCol initial column of the knight
         */
        private void findValidTiles(Board board, int tileCoordinates, int initRow, int initCol){

            if(valid(tileCoordinates)){
                for(int offset : QUEEN_OFFSET){
                    if(valid(tileCoordinates + offset)){
                        if(!board.getTile(tileCoordinates + offset).isOccupied()){
                            if(!visitedTiles.contains(board.getTile(tileCoordinates + offset))){
                                queueStack.add(board.getTile(tileCoordinates + offset)); }}
                        else if(board.getTile(tileCoordinates+offset).isOccupied()){
                            if(!board.getTile(tileCoordinates + offset).getPiece().getColor().equals(this.color)){
                                if(!visitedTiles.contains(board.getTile(tileCoordinates + offset))){
                                    queueStack.add(board.getTile(tileCoordinates + offset));}}}}}
            }

            while(!queueStack.isEmpty()){
                Tile temp = queueStack.pop();
                visitedTiles.add(temp);
                int destRow = (int) Math.floor(temp.getCoordinates()/8);
                int destColumn = temp.getCoordinates() % 8;
                if((initRow - destRow > 3 || initRow - destRow < -3) || (initCol - destColumn > 3 || initCol - destColumn < -3)){return;}
                else if(!validMovesCoordinates.contains(temp.getCoordinates())){
                    validMovesCoordinates.add(temp.getCoordinates());
                    findValidTiles(board, temp.getCoordinates(), initRow, initCol);}}
        }

        private boolean valid(int coordinate){
            boolean safe = false;
            if(coordinate >= 0 && coordinate <= 63){
                safe = true;
            }
            return safe;
        }

    }

    /**
     * Rook Class
     */
    public static class Rook extends Piece
    {
        private final static int[] ROOK_OFFSET = {-1,-7,-8,-9, 1, 7, 8, 9};
        private final static int[] ROOK_ATK_OFFSET = {-27,-24,-21,-18,-16,-14,-3,-2,2,3,14,16,18,21,24,27};


        Rook(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Rook(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board) {

            ArrayList<MoveHandler> moves = new ArrayList<>();
            for(int offset : ROOK_OFFSET){
                //created this variable to hold the calculations for the offset and multiplier
                int calculatedOffset = this.offsetMultiplier*offset;

                int offsetDestination = this.coordinates + (calculatedOffset);
                //fixes the way the pieces interact with the edge of the board.

                if(this.coordinates %8 ==7 && calculatedOffset == 1)
                    continue;
                if(this.coordinates %8 ==7 && calculatedOffset == -7)
                    continue;
                if(this.coordinates %8 ==7 && calculatedOffset == 9)
                    continue;
                if(this.coordinates %8 ==0 && calculatedOffset == -1)
                    continue;
                if(this.coordinates %8 ==0 && calculatedOffset == 7)
                    continue;
                if(this.coordinates %8 ==0 && calculatedOffset == -9)
                    continue;

                if(offsetDestination > 0 && offsetDestination < 63){
                    if(board.getTile(offsetDestination).isOccupied()){
                        if(!board.getTile(offsetDestination).getPiece().getColor().equals(this.color)){
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
                    }else if(!board.getTile(offsetDestination).isOccupied()){
                        moves.add(new MoveHandler.Move(board, this, offsetDestination));
                    }
                }
            }
            for(int offset : ROOK_ATK_OFFSET){
                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);
                if(offsetDestination > 0 && offsetDestination < 63){
                    if(board.getTile(offsetDestination).isOccupied()){
                        if(!board.getTile(offsetDestination).getPiece().getColor().equals(this.color)){
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
                    }
                }
            }
            return moves;
        }


        private boolean valid(int coordinate){
            boolean safe = false;
            if(coordinate >= 0 && coordinate <= 63 && coordinate != this.coordinates){
                safe = true;
            }
            return safe;
        }
    }

    /**
     * Pawn Class
     */
    public static class Pawn extends Piece
    {
        // TODO: Fix column exemption
        private final static int[] PAWN_OFFSET = {7,8,9};

        Pawn(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates) {
            return new Pawn(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }


        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            ArrayList<MoveHandler> moves = new ArrayList<>();

            for( int offset : PAWN_OFFSET){
                //created this variable to hold the calculations for the offset and multiplier
                int calculatedOffset = this.offsetMultiplier*offset;


                int offsetDestination = this.coordinates + (calculatedOffset);

                //fixes the way the pieces interact with the edge of the board.
                if(this.coordinates %8 ==7 && calculatedOffset == 7)
                    continue;
                if(this.coordinates %8 ==0 && calculatedOffset == 9)
                    continue;

                if(valid(offsetDestination)){

                    if(offsetDestination > 0 && offsetDestination < 63 && !board.getTile(offsetDestination).isOccupied()){
                        moves.add(new MoveHandler.Move(board, this, offsetDestination));
                    }else if(offsetDestination > 0 && offsetDestination < 63 && board.getTile(offsetDestination).isOccupied()){
                        if(!board.getTile(offsetDestination).getPiece().getColor().equals(this.getColor())){
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
                    }
                }
            }
            return moves;
        }
        private boolean valid(int coordinate){
            boolean safe = false;

                if (coordinate >= 0 && coordinate <= 63 && coordinate != this.coordinates) {

                    safe = true;
                }

            return safe;
        }
    }


}
