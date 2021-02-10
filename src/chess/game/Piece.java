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

public enum Piece
{
    KING("K", "King"),
    QUEEN("Q", "Queen"),
    ROOK("R", "Rook"),
    BISHOP("B", "Bishop"),
    KNIGHT("KN", "Knight"),
    PAWN("P", "Pawn");

    private String symbol;
    private String name;

    Piece(String symbol, String name)
    {
        this.symbol = symbol;
        this.name = name;
    }

    /**
     * @return symbol of piece
     */
    public String getSymbol()
    {
        return symbol;
    }

    /**
     * @return name of piece
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return image of piece
     */
    public Image getImage(String color)
    {
        return new Image("chess/gui/images/" + color + name + ".png", 60, 60, false, false);
    }
}