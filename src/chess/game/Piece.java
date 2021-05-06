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

import javafx.scene.image.Image;

import java.util.*;

public abstract class Piece
{
    protected final int coordinates;
    protected final String color;
    protected Corp pieceCorp;
    protected final String name;
    protected final int offsetMultiplier;
    protected final int pieceWeight;
    protected final boolean isPlayerPiece;
    protected boolean isCaptured = false;

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

    public int getCoordinates()
    {
        return this.coordinates;
    }

    public String getColor()
    {
        return this.color;
    }

    public String getName()
    {
        return this.name;
    }

    public Boolean isPlayerPiece()
    {
        return this.isPlayerPiece;
    }

    public Corp getCorp()
    {
        return pieceCorp;
    }

    public void setCorp(Corp corp)
    {
        this.pieceCorp = corp;
    }

    public int getPieceWeight()
    {
        return this.pieceWeight;
    }

    public Image getImage()
    {
        return new Image("chess/gui/images/" + color + name + ".png", 60, 60, false, false);
    }

    public boolean getCaptureStatus()
    {
        return isCaptured;
    }

    public void changeCaptureStatus()
    {
        isCaptured = !isCaptured;
    }

    public abstract Piece movePiece(int newCoordinates);
    public abstract ArrayList<MoveHandler> determineMoves(final Board board);

    private static int checkMoves(int initialCords, int destinationCords)
    {
        int initialX = initialCords % 8;
        int initialY = (int)Math.floor((double)initialCords / 8);
        int destinationX = destinationCords % 8;
        int destinationY = (int)Math.floor((double)destinationCords / 8);

        int dx = Math.abs(initialX - destinationX);
        int dy = Math.abs(initialY - destinationY);

        return Math.max(dx, dy);
    }

    /**
     * Knight Class
     */
    public static class Knight extends Piece
    {
        private final static int[] KNIGHT_OFFSET = { -1, -9, -8, -7, 1, 9, 8, 7 };
        private final Stack<Tile> queueStack = new Stack<>();
        private final ArrayList<Integer> validMovesCoordinates = new ArrayList<>();
        private final ArrayList<Tile> visitedTiles = new ArrayList<>();

        Knight(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates)
        {
            return new Knight(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            visitedTiles.clear();
            validMovesCoordinates.clear();

            ArrayList<MoveHandler> moves = new ArrayList<>();

            findValidTiles(board, this.coordinates);

            for (int destinationCoord : validMovesCoordinates)
            {
                moves.add(new MoveHandler.Move(board, this, destinationCoord));
            }

            return moves;
        }

        /**
         * TODO: Need to fix minor issue of allowed tiles
         * @param board needed for location of all current pieces
         * @param tileCoordinates location of tile being passed through recursive
         */
        private void findValidTiles(Board board, int tileCoordinates)
        {
            if (valid(tileCoordinates))
            {
                for (int offset : KNIGHT_OFFSET)
                {
                    if (valid(tileCoordinates + offset))
                    {
                        if (!board.getTile(tileCoordinates + offset).isOccupied())
                        {
                            if (!visitedTiles.contains(board.getTile(tileCoordinates + offset)))
                            {
                                queueStack.add(board.getTile(tileCoordinates + offset));
                            }
                        }

                        if (board.getTile(tileCoordinates+offset).isOccupied())
                        {
                            if (!board.getTile(tileCoordinates + offset).getPiece().getColor().equals(this.color))
                            {
                                if (!visitedTiles.contains(board.getTile(tileCoordinates + offset)))
                                {
                                    if (!validMovesCoordinates.contains(tileCoordinates + offset))
                                    {
                                        if (checkMoves(this.coordinates, tileCoordinates + offset) <= 5)
                                        {
                                            validMovesCoordinates.add(tileCoordinates + offset);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            while (!queueStack.isEmpty())
            {
                Tile temp = queueStack.pop();
                visitedTiles.add(temp);

                if (checkMoves(this.coordinates, temp.getCoordinates()) <= 5)
                {
                    if (!validMovesCoordinates.contains(temp.getCoordinates()))
                    {
                        validMovesCoordinates.add(temp.getCoordinates());
                        findValidTiles(board, temp.getCoordinates());
                    }
                }
            }
        }

        private boolean valid(int coordinate)
        {
            boolean safe = false;

            if (coordinate >= 0 && coordinate <= 63)
            {
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
        private final static int[] KING_OFFSET = { -1, -9, -8, -7, 1, 9, 8, 7 };
        private final Stack<Tile> queueStack = new Stack<>();
        private ArrayList<Integer> validMovesCoordinates= new ArrayList<>();
        private final ArrayList<Tile> visitedTiles = new ArrayList<>();

        King(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates)
        {
            return new King(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            visitedTiles.clear();
            validMovesCoordinates.clear();

            ArrayList<MoveHandler> moves = new ArrayList<MoveHandler>();

            findValidTiles(board, this.coordinates);

            for (int destinationCoord : validMovesCoordinates)
            {
                moves.add(new MoveHandler.Move(board, this, destinationCoord));
            }

            return moves;
        }

        /**
         * TODO: Need to fix minor issue of allowed tiles
         * @param board needed for location of all current pieces
         * @param tileCoordinates location of tile being passed through recursive
         */
        private void findValidTiles(Board board, int tileCoordinates)
        {
            if (valid(tileCoordinates))
            {
                for (int offset : KING_OFFSET)
                {
                    if (valid(tileCoordinates + offset))
                    {
                        if (!board.getTile(tileCoordinates + offset).isOccupied())
                        {
                            if (!visitedTiles.contains(board.getTile(tileCoordinates + offset)))
                            {
                                queueStack.add(board.getTile(tileCoordinates + offset));
                            }
                        }

                        if (board.getTile(tileCoordinates+offset).isOccupied())
                        {
                            if (!board.getTile(tileCoordinates + offset).getPiece().getColor().equals(this.color))
                            {
                                if (!visitedTiles.contains(board.getTile(tileCoordinates + offset)))
                                {
                                    if (!validMovesCoordinates.contains(tileCoordinates + offset))
                                    {
                                        if (checkMoves(this.coordinates, tileCoordinates + offset) <= 3)
                                        {
                                            validMovesCoordinates.add(tileCoordinates + offset);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            while (!queueStack.isEmpty())
            {
                Tile temp = queueStack.pop();
                visitedTiles.add(temp);

                if (checkMoves(this.coordinates, temp.getCoordinates()) <= 3)
                {
                    if (!validMovesCoordinates.contains(temp.getCoordinates()))
                    {
                        validMovesCoordinates.add(temp.getCoordinates());
                        findValidTiles(board, temp.getCoordinates());
                    }
                }
            }
        }

        private boolean valid(int coordinate)
        {
            boolean safe = false;

            if (coordinate >= 0 && coordinate <= 63)
            {
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
        private final static int[] BISHOP_OFFSET = { 7, 8, 9 };

        Bishop(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates)
        {
            return new Bishop(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            ArrayList<MoveHandler> moves = new ArrayList<>();

            for (int offset : BISHOP_OFFSET)
            {
                /* Hold calculations for offset and multiplier. */
                int calculatedOffset = this.offsetMultiplier*offset;

                /* Handles pieces interacting with edge of board. */
                if (this.coordinates % 8 == 7 && (calculatedOffset == 7 || calculatedOffset == -7))
                {
                    continue;
                }

                if (this.coordinates % 8 == 0 && (calculatedOffset == 9 || calculatedOffset == -9))
                {
                    continue;
                }

                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);

                if (valid(offsetDestination))
                {
                    if (offsetDestination > 0 && offsetDestination < 63 && !board.getTile(offsetDestination).isOccupied())
                    {
                        moves.add(new MoveHandler.Move(board, this, offsetDestination));
                    }
                    else if (offsetDestination > 0 && offsetDestination < 63 && board.getTile(offsetDestination).isOccupied())
                    {
                        if (!board.getTile(offsetDestination).getPiece().getColor().equals(this.getColor()))
                        {
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
                    }
                }
            }

            return moves;
        }

        private boolean valid(int coordinate)
        {
            boolean safe = false;

            if (coordinate >= 0 && coordinate <= 63 && coordinate != this.coordinates)
            {
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
        private final static int[] QUEEN_OFFSET = { -1, -9, -8, -7, 1, 9, 8, 7 };
        private final Stack<Tile> queueStack = new Stack<>();
        private ArrayList<Integer> validMovesCoordinates= new ArrayList<>();
        private final ArrayList<Tile> visitedTiles = new ArrayList<>();

        Queen(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates)
        {
            return new Queen(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            visitedTiles.clear();
            validMovesCoordinates.clear();

            ArrayList<MoveHandler> moves = new ArrayList<>();

            findValidTiles(board, this.coordinates);

            for (int destinationCoord : validMovesCoordinates)
            {
                moves.add(new MoveHandler.Move(board, this, destinationCoord));
            }

            return moves;
        }

        /**
         * TODO: Need to fix minor issue of allowed tiles
         * @param board needed for location of all current pieces
         * @param tileCoordinates location of tile being passed through recursive
         */
        private void findValidTiles(Board board, int tileCoordinates)
        {
            if (valid(tileCoordinates))
            {
                for (int offset : QUEEN_OFFSET)
                {
                    if (valid(tileCoordinates + offset))
                    {
                        if (!board.getTile(tileCoordinates + offset).isOccupied())
                        {
                            if (!visitedTiles.contains(board.getTile(tileCoordinates + offset)))
                            {
                                queueStack.add(board.getTile(tileCoordinates + offset));
                            }
                        }
                        else if (board.getTile(tileCoordinates+offset).isOccupied())
                        {
                            if (!board.getTile(tileCoordinates + offset).getPiece().getColor().equals(this.color))
                            {
                                if (!visitedTiles.contains(board.getTile(tileCoordinates + offset)))
                                {
                                    if (!validMovesCoordinates.contains(tileCoordinates + offset))
                                    {
                                        if (checkMoves(this.coordinates, tileCoordinates + offset) <= 3)
                                        {
                                            validMovesCoordinates.add(tileCoordinates + offset);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            while (!queueStack.isEmpty())
            {
                Tile temp = queueStack.pop();
                visitedTiles.add(temp);

                if (checkMoves(this.coordinates, temp.getCoordinates()) <= 3)
                {
                    if (!validMovesCoordinates.contains(temp.getCoordinates()))
                    {
                        validMovesCoordinates.add(temp.getCoordinates());
                        findValidTiles(board, temp.getCoordinates());
                    }
                }
            }
        }

        private boolean valid(int coordinate)
        {
            boolean safe = false;

            if (coordinate >= 0 && coordinate <= 63)
            {
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
        private final static int[] ROOK_OFFSET = { -1, -7, -8, -9, 1, 7, 8, 9 };
        private final static int[] ROOK_ATK_OFFSET = { -27, -24, -21, -18, -16, -14, -3, -2, 2, 3, 14, 16, 18, 21, 24, 27 };

        Rook(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates)
        {
            return new Rook(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            ArrayList<MoveHandler> moves = new ArrayList<>();

            for (int offset : ROOK_OFFSET)
            {
                /* Hold calculations for offset and multiplier. */
                int calculatedOffset = this.offsetMultiplier*offset;
                int offsetDestination = this.coordinates + (calculatedOffset);

                /* Handles pieces interacting with edge of board. */
                if (this.coordinates % 8 == 7 && (calculatedOffset == 1 || calculatedOffset == -7 || calculatedOffset == 9))
                {
                    continue;
                }

                if (this.coordinates % 8 == 0 && (calculatedOffset == -1 || calculatedOffset == 7 || calculatedOffset == -9))
                {
                    continue;
                }

                if (offsetDestination > 0 && offsetDestination < 63)
                {
                    if (board.getTile(offsetDestination).isOccupied())
                    {
                        if (!board.getTile(offsetDestination).getPiece().getColor().equals(this.color))
                        {
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
                    }
                    else if (!board.getTile(offsetDestination).isOccupied())
                    {
                        moves.add(new MoveHandler.Move(board, this, offsetDestination));
                    }
                }
            }

            for (int offset : ROOK_ATK_OFFSET)
            {
                int calculatedOffset = this.offsetMultiplier*offset;
                int offsetDestination = this.coordinates + (this.offsetMultiplier * offset);

                if ((this.coordinates % 8 == 6 || this.coordinates % 8 == 7 ) && (calculatedOffset == 27 || calculatedOffset == 18
                        || calculatedOffset == 3 || calculatedOffset == 2 || calculatedOffset == -14 || calculatedOffset == -21))
                {
                    continue;
                }

                if (this.coordinates % 8 == 2 && (calculatedOffset == 21 || calculatedOffset == -3 || calculatedOffset == -27))
                {
                    continue;
                }

                if (this.coordinates % 8 == 5 && (calculatedOffset == 27 || calculatedOffset == 3|| calculatedOffset == -21))
                {
                    continue;
                }

                if ((this.coordinates % 8 == 0 || this.coordinates % 8 == 1) && (calculatedOffset == 21 || calculatedOffset == 14
                        || calculatedOffset == -2 || calculatedOffset == -3 || calculatedOffset == -18 || calculatedOffset == -27 ))
                {
                    continue;
                }

                if (offsetDestination > 0 && offsetDestination < 63)
                {
                    if (board.getTile(offsetDestination).isOccupied())
                    {
                        if (!board.getTile(offsetDestination).getPiece().getColor().equals(this.color))
                        {
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
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
        private final static int[] PAWN_OFFSET = { 7, 8, 9 };

        Pawn(final int coordinates, final String color, final Corp pieceCorp, String name, int offsetMultiplier, int pieceWeight, boolean playerPiece)
        {
            super(coordinates, color, pieceCorp, name, offsetMultiplier, pieceWeight, playerPiece);
        }

        @Override
        public Piece movePiece(int newCoordinates)
        {
            return new Pawn(newCoordinates, this.color, this.pieceCorp, this.name, this.offsetMultiplier, this.pieceWeight, this.isPlayerPiece);
        }

        @Override
        public ArrayList<MoveHandler> determineMoves(Board board)
        {
            ArrayList<MoveHandler> moves = new ArrayList<>();

            for (int offset : PAWN_OFFSET)
            {
                /* Hold the calculations for the offset and multiplier. */
                int calculatedOffset = this.offsetMultiplier*offset;
                int offsetDestination = this.coordinates + (this.offsetMultiplier*offset);

                /* Handles pieces interacting with edge of board. */
                if ((this.coordinates % 8 == 7 && calculatedOffset == 9))
                {
                    continue;
                }

                if (this.coordinates % 8 == 0 && calculatedOffset == 7)
                {
                    continue;
                }

                if (valid(offsetDestination))
                {
                    if (offsetDestination > 0 && offsetDestination < 63 && !board.getTile(offsetDestination).isOccupied())
                    {
                        moves.add(new MoveHandler.Move(board, this, offsetDestination));
                    }
                    else if (offsetDestination > 0 && offsetDestination < 63 && board.getTile(offsetDestination).isOccupied())
                    {
                        if (!board.getTile(offsetDestination).getPiece().getColor().equals(this.getColor()))
                        {
                            moves.add(new MoveHandler.Move(board, this, offsetDestination));
                        }
                    }
                }
            }

            return moves;
        }

        private boolean valid(int coordinate)
        {
            boolean safe = false;

            if (coordinate >= 0 && coordinate <= 63 && coordinate != this.coordinates)
            {
                safe = true;
            }

            return safe;
        }
    }
}