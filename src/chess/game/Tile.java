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

public class Tile
{
    private Piece piece;
    private String occupyingColor;

    Tile(Piece piece, String occupyingColor)
    {
        this.piece = piece;
        this.occupyingColor = occupyingColor;
    }

    /**
     * @return the piece within a tile
     */
    public Piece getPiece()
    {
        return piece;
    }

    /**
     * @return the color occupying the tile
     */
    public String getColor()
    {
        return occupyingColor;
    }

    /**
     * @setter - set the color of the tile
     * Used if a piece consumes another
     */
    public void setColor(String color)
    {
        this.occupyingColor = color;
    }
}
