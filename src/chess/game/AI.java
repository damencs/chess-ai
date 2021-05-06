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

import java.io.IOException;
import java.util.*;

public class AI
{
    private Board board;
    private String aiTeamColor;

    private int kingVulnerableScore;

    private ArrayList<Integer> kingVulnerableDestinations;
    private ArrayList<Piece> AI_kingCorp;
    private ArrayList<Piece> AI_kingsBishopCorp;
    private ArrayList<Piece> AI_queensBishopCorp;

    public void AI_turn(Board board)
    {
        this.board = board;
        this.aiTeamColor = board.getAiTeamColor();

        /* Return the King's vulnerability score. */
        checkKingStatus(board);

        /* See if all commanders are still on the board. */
        checkCorpCommandersStatus();
        setupCorps();
    }

    /* Evaluate the board. */
    private int evaluateBoard(final Board board, final Board movedBoard)
    {
        /* Negative Score: AI Losing - Positive: Winning */
        return (aiTeamColor.equals("white")) ? ((board.getWhiteTeamScore() - board.getBlackTeamScore()) -
                (movedBoard.getWhiteTeamScore() - movedBoard.getBlackTeamScore())) :
                ((board.getBlackTeamScore() - board.getWhiteTeamScore()) - (movedBoard.getBlackTeamScore() - movedBoard.getWhiteTeamScore()));
    }

    public MoveHandler getAiMove() throws IOException
    {
        int kingScore = Integer.MIN_VALUE;
        int kingBishopScore = Integer.MIN_VALUE;
        int queenBishopScore = Integer.MIN_VALUE;

        HashMap<Integer, MoveHandler> kingMoves = determineBestCorpMove(AI_kingCorp);
        if (board.getAIKingCorpAvailability())
        {
            if (Collections.max(kingMoves.keySet()) != null)
            {
                if (Collections.max(kingMoves.keySet()) > kingScore)
                {
                    kingScore = Collections.max(kingMoves.keySet());
                }
            }
        }

        HashMap<Integer, MoveHandler> kingBishopMoves = determineBestCorpMove(AI_kingsBishopCorp);
        if (board.getAIKingBishopAvailability())
        {
            if (Collections.max(kingBishopMoves.keySet()) != null)
            {
                if (Collections.max(kingBishopMoves.keySet()) > kingBishopScore)
                {
                    kingBishopScore = Collections.max(kingBishopMoves.keySet());
                }
            }
        }

        HashMap<Integer, MoveHandler> queenBishopMoves = determineBestCorpMove(AI_queensBishopCorp);
        if (board.getAIQueensBishopAvailability())
        {
            if (Collections.max(queenBishopMoves.keySet()) != null)
            {
                if (Collections.max(queenBishopMoves.keySet()) > queenBishopScore)
                {
                    queenBishopScore = Collections.max(queenBishopMoves.keySet());
                }
            }
        }

        if (board.getAIKingCorpAvailability())
        {
            if (kingScore >= kingBishopScore && kingScore >= queenBishopScore)
            {
                board.switchAIKingCorpCommand();
                return kingMoves.get(kingScore);
            }
        }

        /* Return Best King Bishop Corp Move */
        if (kingBishopScore >= kingScore && kingBishopScore >= queenBishopScore)
        {
            board.switchAIKingBishopCorpCommand();
            return kingBishopMoves.get(kingBishopScore);
        }

        /* Return Best Queen Bishop Core Move */
        if (queenBishopScore >= kingScore && queenBishopScore >= kingBishopScore)
        {
            board.switchAIQueenBishopCorpCommand();
            return queenBishopMoves.get(queenBishopScore);
        }

        return null;
    }

    private HashMap<Integer, MoveHandler> determineBestCorpMove(ArrayList<Piece> corp) throws IOException
    {
        HashMap<Integer, MoveHandler> moveList = new HashMap<>();

        for (Piece piece : corp)
        {
            ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(board);

            int movescore;

            for (MoveHandler moves: pieceMoves)
            {
                final Board boardHolder = board;
                board = moves.temporaryExecuteMove();

                /* Move Score = (AI Team Score - Player Team Score) + Center Control Score - King's Vulnerability */
                movescore = evaluateBoard(board, boardHolder);
                movescore += centerControlStatus(board);

                /* If King's Vulnerable Locations are in range, increase location importance. */
                if (kingVulnerableDestinations.contains(moves.getDestination()))
                {
                    movescore += 75;
                }

                /* Prioritize King Protection */
                if (moves.getMovingPiece().getName().equals("King"))
                {
                    movescore -= 50;
                }

                board = moves.unexecuteMove();

                Random random = new Random();
                int randomInt = random.nextInt(5 + 5);

                if (moveList.containsKey(movescore))
                {
                    moveList.put(movescore + randomInt, moves);
                }
                else
                {
                    moveList.put(movescore + randomInt, moves);
                }
            }
        }
        return moveList;
    }

    /* Calculate King's Vulnerability Score: If score > 30%, pieces need to move to protect the King. */
    private int checkKingStatus(final Board board)
    {
        kingVulnerableDestinations = new ArrayList<>();
        kingVulnerableScore = 0;

        Piece king = board.getAIKing();
        List<Piece> playerTeam = (aiTeamColor.equals("white")) ? board.getBlackPieces() : board.getWhitePieces();

        for (Piece piece : playerTeam)
        {
            ConquerSet vulnerabilityScore = new ConquerSet(piece, king);
            for (MoveHandler enemyPieceMoves : piece.determineMoves(board))
            {
                if (enemyPieceMoves.getDestination() == king.getCoordinates())
                {
                    kingVulnerableDestinations.add(piece.getCoordinates());

                    double value = ((double)(6 - vulnerabilityScore.getConquerSet()) / 6);

                    kingVulnerableScore += (int) value;
                }
            }
        }
        return kingVulnerableScore;
    }

    /* Center Control Score: If negative, AI doesn't have control. */
    private int centerControlStatus(final Board board)
    {
        int playerControl = 0;
        int aiControl = 0;

        for (Piece piece : board.getPlayerPieces())
        {
            if (piece.getCoordinates() >= 25 && piece.getCoordinates() <= 30 || piece.getCoordinates() >= 33 && piece.getCoordinates() <= 38)
            {
                playerControl += 15;
            }
        }

        for(Piece piece : board.getAIPieces())
        {
            if (piece.getCoordinates() >= 25 && piece.getCoordinates() <= 30 || piece.getCoordinates() >= 33 && piece.getCoordinates() <= 38)
            {
                aiControl += 15;
            }
        }

        return aiControl - playerControl;
    }

    /* Delegate Corp Commander Status to King if Captured. */
    private void checkCorpCommandersStatus()
    {
        ArrayList<Piece> commanders = board.getAICorpCommanders();

        for (Piece piece : commanders)
        {
            if (piece.getCaptureStatus())
            {
                board.revertCommand(piece.getCorp());
            }
        }
    }

    private void setupCorps()
    {
        AI_kingCorp = (aiTeamColor.equals("white")) ? board.getWhiteCorpPieces("AI_king") : board.getBlackCorpPieces("AI_king");
        AI_kingsBishopCorp = (aiTeamColor.equals("white")) ? board.getWhiteCorpPieces("AI_kingsBishop") : board.getBlackCorpPieces("AI_kingsBishop");
        AI_queensBishopCorp = (aiTeamColor.equals("white")) ? board.getWhiteCorpPieces("AI_queensBishop") : board.getBlackCorpPieces("AI_queensBishop");
    }

    public String getAiTeamColor()
    {
        return aiTeamColor;
    }
}