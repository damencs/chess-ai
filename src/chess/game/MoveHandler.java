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

public abstract class MoveHandler
{
    final Board board;
    final Piece movingPiece;
    final int destination;

    private MoveHandler(final Board board, final Piece movingPiece, final int destination){
        this.board = board;
        this.movingPiece = movingPiece;
        this.destination = destination;
    }

    public int getDestination() {return this.destination; }

    public abstract Board executeMove();

    public static final class Move extends MoveHandler
    {
        private Move(Board board, Piece movingPiece, int destination) {
            super(board, movingPiece, destination);
        }

        @Override
        public Board executeMove() {
            Board.SetBoard setBoardMove = new Board.SetBoard();
            for(Piece piece : this.board.getAlivePieces()){
                if(!movingPiece.equals(piece)){
                    setBoardMove.setPiece(piece);
                }
            }
            setBoardMove.setPiece(movingPiece.movePiece(destination));
            return setBoardMove.build();
        }
    }
}
