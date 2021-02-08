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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public enum Piece
{
    KING("K", "King", "king.png"),
    QUEEN("Q", "Queen", "queen.png"),
    ROOK("R", "Rook", "rook.png"),
    BISHOP("B", "Bishop", "bishop.png"),
    KNIGHT("KN", "Knight", "knight.png"),
    PAWN("P", "Pawn", "pawn.png");

    private String symbol;
    private String name;
    private String fileName;

    Piece(String symbol, String name, String fileName)
    {
        this.symbol = symbol;
        this.name = name;
        this.fileName = fileName;
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
    public Image getImage(String color) throws FileNotFoundException
    {
        return new Image(new FileInputStream("gui/images/" + color + fileName));
    }
}