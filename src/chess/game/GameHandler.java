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
    Board board = new Board();
    boolean isPlayerTurn;

    public GameHandler()
    {
        randomizeStart();
        board.setupBoard(isPlayerTurn);
        board.printBoard();
    }

    /* Decide whether or not the player starts as white (which starts first in Chess). */
    private boolean randomizeStart()
    {
        return isPlayerTurn = (Math.random() < 0.5 ? false : true);
    }
}
