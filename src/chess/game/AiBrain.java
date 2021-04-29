package chess.game;

import java.io.IOException;
import java.util.*;

public class AiBrain {
    private Board board;
    private int kingVulnerableScore;
    private String aiTeamColor;
    private ArrayList<Integer> kingVulnerableDestinations;
    private ArrayList<Piece> AI_kingCorp;
    private ArrayList<Piece> AI_kingsBishopCorp;
    private ArrayList<Piece> AI_queensBishopCorp;
    private final String kingCorpString = "";
    private final String kingBishopCorpString = "";
    private final String queenBishopCorpString = "";

    public void AI_turn(Board board) throws IOException {
        this.board = board;
        aiTeamColor = board.getAiTeamColor();
        checkKingStatus(board); // Get the kings vulnerability score
        checkCorpCommandersStatus(); // See if all commanders are still on the board
        setupCorps();
    }

    // Evaluate board
    private int evaluateBoard(final Board board, final Board movedBoard){
        // Negative score if AI is losing. Positive Score if winning
        return (aiTeamColor.equals("white")) ? ((board.getWhiteTeamScore() - board.getBlackTeamScore()) -
                (movedBoard.getWhiteTeamScore() - movedBoard.getBlackTeamScore())) :
                ((board.getBlackTeamScore() - board.getWhiteTeamScore()) - (movedBoard.getBlackTeamScore() - movedBoard.getWhiteTeamScore()));
    }

    public MoveHandler executeKingCorp() throws IOException {
        return determineBestMove(AI_kingCorp);
    }
    public MoveHandler executeKingBishopCorp() throws IOException {
        return determineBestMove(AI_kingsBishopCorp);
    }
    public MoveHandler executeQueensBishopCorp() throws IOException {
        return determineBestMove(AI_queensBishopCorp);
    }

    private MoveHandler determineBestMove(ArrayList<Piece> corp) throws IOException {
        HashMap<Integer, MoveHandler> moveList = new HashMap<>();
        ArrayList<Integer> moveListKeys = new ArrayList<>();

        for(Piece piece : corp){
            ArrayList<MoveHandler> pieceMoves  = piece.determineMoves(board);
            if(piece.getName().equals("King")){
                System.out.println(pieceMoves.size());
            }
            int movescore;
            for(MoveHandler moves: pieceMoves){
                final Board boardHolder = board;
                board = moves.temporaryExecuteMove();

                // Move Score is equal to (AI Team Score - Player Team Score) + Center Control Score - Kings Vulnerability
                movescore = evaluateBoard(board, boardHolder);
                movescore += centerControlStatus(board);

                // If the kings vulnerable locations are in range, increase location importance
                if(kingVulnerableDestinations.contains(moves.getDestination())){
                    movescore += 75;
                }
                // Make moving the king have to be more important
                if(moves.getMovingPiece().getName().equals("King")){
                    movescore -= 50;
                }


                //movescore += KingImmediateSurrounding();

                board = moves.unexecuteMove();

                Random random = new Random();
                int randomInt = random.nextInt(5 + 5);
               if(moveList.containsKey(movescore)){
                   moveList.put(movescore + randomInt, moves);
                   moveListKeys.add(movescore);
               }
               else{
                   moveList.put(movescore + randomInt, moves);
                   moveListKeys.add(movescore);
               }
            }
        }
        int bestMoves;
        try{
            if(Collections.max(moveList.keySet()) != null){
                bestMoves = Collections.max(moveList.keySet());
                return moveList.get(bestMoves);
            }
        }catch(Exception e){
            return null;
        }
        return null;
    }

    // Calculate Kings Vulnerability Score
    // If score is > 30% pieces need to move to protect
    private int checkKingStatus(final Board board){
        kingVulnerableDestinations = new ArrayList<>();
        Piece king = board.getAIKing();
        kingVulnerableScore = 0;
        List<Piece> playerTeam = (aiTeamColor.equals("white")) ? board.getBlackPieces() : board.getWhitePieces();
        for(Piece piece : playerTeam){
            ConquerSet vulnerabilityScore = new ConquerSet(piece, king);
            for(MoveHandler enemyPieceMoves : piece.determineMoves(board)){
                if(enemyPieceMoves.getDestination() == king.getCoordinates()){
                    kingVulnerableDestinations.add(piece.getCoordinates());
                    double value = ((double)(6 - vulnerabilityScore.getConquerSet()) / 6);
                    kingVulnerableScore += (int) value;
                }
            }
        }
        return kingVulnerableScore;
    }

    // Score for center control
    // If the score is negative, the AI does not have control
    private int centerControlStatus(final Board board){
        int playerControl = 0;
        int aiControl = 0;
        for(Piece piece : board.getPlayerPieces()){
            if(piece.getCoordinates() >= 25 && piece.getCoordinates() <= 30 ||
                    piece.getCoordinates() >= 33 && piece.getCoordinates() <= 38){
                playerControl += 15;
            }
        }
        for(Piece piece : board.getAIPieces()){
            if(piece.getCoordinates() >= 25 && piece.getCoordinates() <= 30 ||
                    piece.getCoordinates() >= 33 && piece.getCoordinates() <= 38){
                aiControl += 15;
            }
        }
        return aiControl - playerControl;
    }

    // Check is Bishop Commanders where captured during players last turn
    // If so revert the command of the pieces back to the king
    private void checkCorpCommandersStatus(){
        ArrayList<Piece> commanders = board.getAICorpCommanders();
        for(Piece piece : commanders){
            if(piece.getCaptureStatus()){
                board.revertCommand(piece.getCorp());
                System.out.println("Command of Corp reverted to king");
            }
        }
    }

    private int KingImmediateSurrounding(){
        int[] offsets = {-1,-9,-8,-7, 1, 9, 8, 7};
        Piece king = board.getAIKing();
        int kingSafetyScore = 0;
        for(int offset : offsets){
            if(king.getCoordinates() + offset >= 0 && king.getCoordinates() + offset <= 63){
                if(!board.getTile(king.getCoordinates() + offset).isOccupied()){
                    kingSafetyScore = kingSafetyScore - 50;
                }
            }
        }
        return kingSafetyScore;
    }

    // King may delegate subordinates to be commanded by either of bishops
    private void delegateKingCorpPieceToBishopCorp(Piece pieceToDelegate){

    }

    private void setupCorps(){
        AI_kingCorp = (aiTeamColor.equals("white")) ? board.getWhiteCorpPieces("AI_king") :
                board.getBlackCorpPieces("AI_king");
        AI_kingsBishopCorp = (aiTeamColor.equals("white")) ? board.getWhiteCorpPieces("AI_kingsBishop") :
                board.getBlackCorpPieces("AI_kingsBishop");
        AI_queensBishopCorp = (aiTeamColor.equals("white")) ? board.getWhiteCorpPieces("AI_queensBishop") :
                board.getBlackCorpPieces("AI_queensBishop");
    }

    public String getAiTeamColor(){
        return aiTeamColor;
    }


}
