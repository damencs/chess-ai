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

    private Board board;

    boolean isPlayerTurn;

    public GameHandler()
    {

    }

    /**
     * TODO This method needs to be moved to a MoveHandler class
     * @param movedPiece
     * @param destination
     * @param originCoordinates
     */
    public void move(Piece movedPiece, int destination, int originCoordinates){
        Tile movingTile = board.getTile(originCoordinates);
        Tile destinationTile = board.getTile(destination);

        if(!destinationTile.isOccupied()){
            Board.SetBoard setBoardMove = new Board.SetBoard();
            for(Piece piece : this.board.getAlivePieces()){
                if(!movedPiece.equals(piece)){
                    setBoardMove.setPiece(piece);
                }
            }
            setBoardMove.setPiece(movedPiece.movePiece(destination));
            this.board = setBoardMove.build();
        }else if(!destinationTile.getPiece().getColor().equals(movedPiece.getColor())){
            ConquerSet conquerSet = new ConquerSet(movedPiece, destinationTile.getPiece());
            int diceRoll = (int)(Math.random()*6+1);
            System.out.println(diceRoll + " ? " + conquerSet.getConquerSet());
            if(diceRoll > conquerSet.getConquerSet()){

                if(destinationTile.getPiece().getColor().equals("white")){
                    board.getWhitePieces().remove(destinationTile.getPiece());
                }else{
                    board.getBlackPieces().remove(destinationTile.getPiece());
                }

                Board.SetBoard setBoardMove = new Board.SetBoard();
                for(Piece piece : this.board.getAlivePieces()){
                    if(!movedPiece.equals(piece)){
                        setBoardMove.setPiece(piece);
                    }
                }
                setBoardMove.setPiece(movedPiece.movePiece(destination));
                this.board = setBoardMove.build();
            }
        }
    }

    public void setBoard(){
        board = Board.createInitialBoard(isPlayerTurn());

    }
    /* Return instance of board*/
    public Board getBoard()
    { return board; }

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

    public void setBoard(Board board){ this.board = board;}

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

    /* TODO: - Implement AI That will make its moves as soon as player ends turn */

}
