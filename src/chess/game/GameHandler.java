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
    boolean isPlayerTurn;

    public GameHandler()
    {

    }
    /* Return instance of board*/
    public Board getBoard()
    {
        return board;
    }

    /* Return whether or not it is the player's turn. */
    public boolean isPlayerTurn()
    {
        return isPlayerTurn;
    }

    /* Update the player turn within the GameHandler itself. */
    public void updatePlayerTurn(boolean update)
    {
        isPlayerTurn = update;
    }
}
