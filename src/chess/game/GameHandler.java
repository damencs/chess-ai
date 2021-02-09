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

public class GameHandler
{
    /* TODO: - Track the starting game time and compare it to current to display game time. */

    Board board = new Board();

    public GameHandler()
    {
    }

    /* Returns the tile-array for the board. */
    public Board getBoard()
    {
        return board;
    }

    /* Resets the board being used within the current game. */
    public void resetBoard()
    {
        board.reset();
    }
}
