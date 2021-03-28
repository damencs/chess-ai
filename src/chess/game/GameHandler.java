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
    private Board board;
    private boolean isPlayerTurn;
    private boolean gameOver;

    public void setBoard(){
        board = Board.createInitialBoard(isPlayerTurn());
    }
    public void setBoard(Board board){ this.board = board;}
    /* Return instance of board*/
    public Board getBoard() { return board; }

    public Tile[][] getBoardTiles(){
        Tile[][] tiles = new Tile[8][8];
        int index = 0;
        for(int row = 0; row < tiles.length; row++){
            for(int column = 0; column < tiles[row].length; column++){
                tiles[row][column] = board.getTile(index);
                index++;
            }
        }
        return tiles;
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
