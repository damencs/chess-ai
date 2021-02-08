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

public class Board
{
    private Tile tiles[][] = new Tile[8][8];
    private String topColor = "black";
    private String bottomColor = "white";

    /* Set the board up based on who starts first. */
    public void setupBoard(boolean isPlayerFirst)
    {
        /* Piece Orientation Validator */
        if (!isPlayerFirst)
        {
            topColor = "white";
            bottomColor = "black";
        }

        /* PAWN Initializations */
        for (int column = 0; column <= 7; column++)
        {
            tiles[1][column] = new Tile(Piece.PAWN, topColor);
            tiles[6][column] = new Tile(Piece.PAWN, bottomColor);
        }

        /* ROOK Initializations */
        for (int column = 0; column <= 7; column += 7)
        {
            tiles[0][column] = new Tile(Piece.ROOK, topColor);
            tiles[7][column] = new Tile(Piece.ROOK, bottomColor);
        }

        /* KNIGHT Initializations */
        for (int column = 1; column <= 6; column += 5)
        {
            tiles[0][column] = new Tile(Piece.KNIGHT, topColor);
            tiles[7][column] = new Tile(Piece.KNIGHT, bottomColor);
        }

        /* BISHOP Initializations */
        for (int column = 2; column <= 5; column++)
        {
            tiles[0][column] = new Tile(Piece.BISHOP, topColor);
            tiles[7][column] = new Tile(Piece.BISHOP, bottomColor);
        }

        /* KING & QUEEN Initializations */
        tiles[0][3] = new Tile(Piece.KING, topColor);
        tiles[0][4] = new Tile(Piece.QUEEN, topColor);
        tiles[7][4] = new Tile(Piece.KING, bottomColor);
        tiles[7][3] = new Tile(Piece.QUEEN, bottomColor);
    }

    /* Resets all board-related data. */
    public void resetBoard()
    {
        tiles = new Tile[8][8];
    }

    /* Returns the tile-array for the board. */
    public Tile[][] getBoard()
    {
        return tiles;
    }

    /* Print text-based current tile occupancy into console. */
    public void printBoard()
    {
        System.out.println("-- Current Board --");

        for (int row = 0; row < 8; row++)
        {
            for (int column = 0; column < 8; column++)
            {
                try
                {
                    System.out.print(tiles[row][column].getPiece().getSymbol() + " ");
                }
                catch (NullPointerException ex) {}
            }
            System.out.println();
        }
    }
}
