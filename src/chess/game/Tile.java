package chess.game;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {
    protected final int coordinates;

    private Tile(int coordinates){ this.coordinates = coordinates; }

    private static final Map<Integer, EmptyTile> EMPTY_TILE_MAP = createEmptyTiles();

    public abstract boolean isOccupied();
    public abstract Piece getPiece();

    public int getCoordinates(){return coordinates;}

    /* TODO: Add immutability for java flow */
    private static Map<Integer, EmptyTile> createEmptyTiles()
    {
        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
        for(int tileNumber = 0; tileNumber < 64; tileNumber++)
        {
            emptyTileMap.put(tileNumber, new EmptyTile(tileNumber));
        }
        return Collections.unmodifiableMap(emptyTileMap);
    }

    /**
     *
     * @param coordinates for the tiles location
     * @param piece if the tile is occupied (otherwise null)
     * @return the Tile being created.
     */
    public static Tile createTiles(final int coordinates, final Piece piece)
    {
        return piece != null ? new OccupiedTile(coordinates, piece) : EMPTY_TILE_MAP.get(coordinates);
    }

    /**
     *  Create a Empty Tile Object
     *  */
    public static final class EmptyTile extends Tile
    {
        EmptyTile(final int coordinate){ super(coordinate); }

        @Override
        public boolean isOccupied() {
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }

    }

    /**
     *  Create an Occupied tile object
     *  */
    public static final class OccupiedTile extends Tile
    {
        private final Piece occupyingPiece;

        OccupiedTile(final int coordinate, Piece occupyingPiece)
        {
            super(coordinate);
            this.occupyingPiece = occupyingPiece;
        }

        @Override
        public boolean isOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return occupyingPiece;
        }

    }
}
