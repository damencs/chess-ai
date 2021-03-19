
package chess.game;

public class AIMove
{
    Piece pieceToMOve;
    Piece attackedPiece;
    int oldPos;
    int newPos;
    boolean isAttackMove;
    double moveBenefit;

    AIMove(Piece pieceToMOve, Piece attackedPiece, int oldPos, int newPos, boolean isAttackMove, double moveBenefit)
    {
        this.pieceToMOve = pieceToMOve;
        this.attackedPiece = attackedPiece;
        this.oldPos = oldPos;
        this.newPos = newPos;
        this.isAttackMove = isAttackMove;
        this.moveBenefit = moveBenefit;

    }
}
