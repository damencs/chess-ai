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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board
{

    private final Tile[][] tile = new Tile[8][8];
    private final List<Piece> whiteTeam;
    private final List<Piece> blackTeam;
    public ArrayList<Tile> chessBoard;
    private String AiTeamColor;
    private String PlayerColor;

    private static final Corp kingCorp = new Corp("king", true);
    private static final Corp kingsBishopCorp = new Corp("kingsBishop", true);
    private static final Corp queensBishopCorp = new Corp("queensBishop", true);

    private static final Corp AI_kingCorp = new Corp("AI_king", true);
    private static final Corp AI_kingsBishopCorp = new Corp("AI_kingsBishop", true);
    private static final Corp AI_queensBishopCorp = new Corp("AI_queensBishop", true);

    private Board(SetBoard setter){
        this.chessBoard = createBoard(setter);
        this.whiteTeam = getRemainingTeam(this.chessBoard, "white");
        this.blackTeam = getRemainingTeam(this.chessBoard, "black");
        setAICorpCommanders();
        getAiTeamColor();
    }

    private void setAICorpCommanders() {
        for(Piece piece : getAIPieces()){
            if(piece.getName().equals("Bishop")){
                switch (piece.getCorp().getCorpName()) {
                    case "AI_kingsBishop" -> AI_kingsBishopCorp.setCorpCommander(piece);
                    case "AI_queensBishop" -> AI_queensBishopCorp.setCorpCommander(piece);
                }
            }
            if(piece.getName().equals("King")){
                AI_kingCorp.setCorpCommander(piece);
            }
        }
    }

    public String getAiTeamColor(){
        if(getAIPieces().get(0).getColor().equals("white")){
            AiTeamColor = "white";
            PlayerColor = "black";
            return "white";
        }
        AiTeamColor = "black";
        PlayerColor = "white";
        return "black";
    }

    public void revertCommand(Corp corp){
        for(Piece piece : getCorpPieces(corp.getCorpName())){
            piece.setCorp(AI_kingCorp);
        }
    }

    public List<Piece> getBlackPieces() {
        return this.blackTeam;
    }

    public List<Piece> getWhitePieces() {
        return this.whiteTeam;
    }

    public int getBlackTeamScore(){
        int blackTeamScore = 0;
        for(Piece piece : blackTeam){
            blackTeamScore += piece.getPieceWeight();
        }
        return blackTeamScore;
    }

    public int getWhiteTeamScore(){
        int whiteTeamScore = 0;
        for(Piece piece : whiteTeam){
            whiteTeamScore += piece.getPieceWeight();
        }
        return whiteTeamScore;
    }

    public List<Piece> getAIPieces(){
        ArrayList<Piece> ai_Pieces = new ArrayList<>();
        for(Piece piece : getAlivePieces()){
                if(piece.getCorp().getCorpName().equals("AI_king") ||
                        piece.getCorp().getCorpName().equals("AI_kingsBishop") ||
                        piece.getCorp().getCorpName().equals("AI_queensBishop")){
                    ai_Pieces.add(piece);
                }
        }
        return ai_Pieces;
    }

    public List<Piece> getPlayerPieces(){
        ArrayList<Piece> playerPieces = new ArrayList<>();
        for(Piece piece : getAlivePieces()){
                if(piece.getCorp().getCorpName().equals("king") ||
                        piece.getCorp().getCorpName().equals("kingsBishop") ||
                        piece.getCorp().getCorpName().equals("queensBishop")){
                    playerPieces.add(piece);
                }
        }
        return playerPieces;
    }

    public ArrayList<Corp> getAICorps(){
        ArrayList<Corp> aiCorps = new ArrayList<>();
        aiCorps.add(AI_kingCorp);
        aiCorps.add(AI_kingsBishopCorp);
        aiCorps.add(AI_queensBishopCorp);
        return aiCorps;
    }

    public ArrayList<Piece> getAICorpCommanders(){
        ArrayList<Piece> aiCorps = new ArrayList<>();
        aiCorps.add(AI_kingsBishopCorp.getCorpCommander());
        aiCorps.add(AI_queensBishopCorp.getCorpCommander());
        return aiCorps;
    }

    public Piece getAIKing(){
        List<Piece> aiteam = (AiTeamColor.equals("white")) ? getWhitePieces() : getBlackPieces();
        for(Piece piece : aiteam){
            if(piece.getName().equals("King")){
                return piece;
            }
        }
        return null;
    }

    public Piece getPlayerKing(){
        List<Piece> playerTeam = (PlayerColor.equals("white")) ? getWhitePieces() : getBlackPieces();
        for(Piece piece : playerTeam){
            if(piece.getName().equals("King")){
                return piece;
            }
        }
        return null;
    }

    public ArrayList<Piece> getCorpPieces(String name){
        ArrayList<Piece> corpPieces = new ArrayList<>();
        for(Piece piece : getAIPieces()){
            if(piece.getCorp().getCorpName().equals(name)){
                corpPieces.add(piece);
            }
        }
        return corpPieces;
    }

    public ArrayList<Piece> getBlackCorpPieces(String name){
        ArrayList<Piece> corpPieces = new ArrayList<>();
        for(Piece piece : getBlackPieces()){
            if(piece.getCorp().getCorpName().equals(name)){
                corpPieces.add(piece);
            }
        }
        return corpPieces;
    }

    public ArrayList<Piece> getWhiteCorpPieces(String name){
        ArrayList<Piece> corpPieces = new ArrayList<>();
        for(Piece piece : getWhitePieces()){
            if(piece.getCorp().getCorpName().equals(name)){
                corpPieces.add(piece);
            }
        }
        return corpPieces;
    }

    //TODO Make corp lists

    public List<Piece> getAlivePieces(){
        return Stream.concat(this.whiteTeam.stream(), this.blackTeam.stream()).collect(Collectors.toList());
    }

    /* Check the Board for Remaining Team Piece */
    private List<Piece> getRemainingTeam(List<Tile> boardTiles, String teamColor){
        final List<Piece> alivePieces = new ArrayList<>();
        for(final Tile tile : boardTiles){
            if(tile.isOccupied()){
                final Piece piece = tile.getPiece();
                if(piece.getColor().equals(teamColor)){
                    alivePieces.add(piece);
                }
            }
        }
        return alivePieces;
    }

    private static ArrayList<Tile> createBoard(final SetBoard setter){
        final Tile[] tiles = new Tile[64];
        for(int i = 0; i < 64; i++){
            tiles[i] = Tile.createTiles(i, setter.setBoardPieces.get(i));
        }
        ArrayList<Tile> listTiles = new ArrayList<Tile>();
        Collections.addAll(listTiles, tiles);
        return listTiles;
    }

    /**
     * @param isPlayerTurn determines if player is white or black
     * @return the initial board state
     *
     * set Piece paramenters needed are as follows:
     *  1: Original Starting Coordinates
     *  2: Final Color of each piece
     *  3: Corp that individual piece is associated with
     *  3: Name of individual piece
     *  4: Offset Multiplier for single directional moves (1 is down : -1 is up)
     */
    public static Board createInitialBoard(boolean isPlayerTurn){
        final SetBoard setter = new SetBoard();
        String top = "black";
        String bottom = "white";
        if (!isPlayerTurn) {
            top = "white";
            bottom = "black";
        }

        /* AI Pieces on the top of the board */
        setter.setPiece( new Piece.Rook(0, top, AI_kingCorp, "Rook", 1,50, false));
        setter.setPiece( new Piece.Knight(1, top, AI_kingsBishopCorp, "Knight", 1,30, false));
        setter.setPiece( new Piece.Bishop(2, top, AI_kingsBishopCorp, "Bishop", 1,80, false));
        setter.setPiece( new Piece.King(3, top, AI_kingCorp, "King", 1,500, false));
        setter.setPiece( new Piece.Queen(4, top, AI_kingCorp, "Queen", 1,30, false));
        setter.setPiece( new Piece.Rook(7, top, AI_kingCorp, "Rook", 1,50, false));
        setter.setPiece( new Piece.Knight(6, top, AI_queensBishopCorp, "Knight", 1,30, false));
        setter.setPiece( new Piece.Bishop(5, top, AI_queensBishopCorp, "Bishop", 1,80, false));

        setter.setPiece(new Piece.Pawn(8, top, AI_kingsBishopCorp, "Pawn", 1,10, false));
        setter.setPiece(new Piece.Pawn(9, top, AI_kingsBishopCorp, "Pawn", 1,10, false));
        setter.setPiece(new Piece.Pawn(10, top, AI_kingsBishopCorp, "Pawn", 1,10, false));
        setter.setPiece(new Piece.Pawn(11, top, AI_kingCorp, "Pawn", 1,10, false));
        setter.setPiece(new Piece.Pawn(12, top, AI_kingCorp, "Pawn", 1,10, false));
        setter.setPiece( new Piece.Pawn(13, top, AI_queensBishopCorp, "Pawn", 1,10, false));
        setter.setPiece( new Piece.Pawn(14, top, AI_queensBishopCorp, "Pawn", 1,10, false));
        setter.setPiece( new Piece.Pawn(15, top, AI_queensBishopCorp, "Pawn", 1,10, false));

        /* Player Pieces on the bottom of the board */
        setter.setPiece( new Piece.Pawn(48, bottom, queensBishopCorp, "Pawn", -1,10, true));
        setter.setPiece( new Piece.Pawn(49, bottom, queensBishopCorp, "Pawn", -1,10, true));
        setter.setPiece( new Piece.Pawn(50, bottom, queensBishopCorp, "Pawn", -1,10, true));
        setter.setPiece( new Piece.Pawn(51, bottom, kingCorp, "Pawn", -1,10, true));
        setter.setPiece( new Piece.Pawn(52, bottom, kingCorp, "Pawn", -1,10, true));
        setter.setPiece( new Piece.Pawn(53, bottom, kingsBishopCorp, "Pawn", -1,10, true));
        setter.setPiece( new Piece.Pawn(54, bottom, kingsBishopCorp, "Pawn", -1,10, true));
        setter.setPiece( new Piece.Pawn(55, bottom, kingsBishopCorp, "Pawn", -1,10, true));

        setter.setPiece( new Piece.Rook(56, bottom, kingCorp, "Rook", -1,50, true));
        setter.setPiece( new Piece.Knight(57, bottom, queensBishopCorp, "Knight", -1,30, true));
        setter.setPiece( new Piece.Bishop(58, bottom, queensBishopCorp, "Bishop", -1,80, true));
        setter.setPiece( new Piece.King(59, bottom, kingCorp, "King", -1,500, true));
        setter.setPiece( new Piece.Queen(60, bottom, kingCorp, "Queen", -1,30, true));
        setter.setPiece( new Piece.Rook(63, bottom, kingCorp, "Rook", -1,50, true));
        setter.setPiece( new Piece.Knight(62, bottom, kingsBishopCorp, "Knight", -1,30, true));
        setter.setPiece( new Piece.Bishop(61, bottom, kingsBishopCorp, "Bishop", -1,80, true));

        return setter.build();
    }

    public Tile getTile(final int coordinate){
        return chessBoard.get(coordinate);
    }
    //* Returns the tile-array for the board. /*
    public Tile[][] getTiles()
    {
        return tile;
    }

    /**
     * Setter Class for setting the board up in the beginning
     * and after each move. Minimizes error and maximizes immutability
     */
    public static class SetBoard {

        Map<Integer, Piece> setBoardPieces;

        public SetBoard(){
            this.setBoardPieces = new HashMap<>();
        }

        public void setPiece(final Piece piece){
            this.setBoardPieces.put(piece.getCoordinates(), piece);
        }

        public Board build(){
            return new Board(this);
        }
    }

    public void resetCorpAvailability()
    {
        kingCorp.setCorpCommandAvailability(true);
        kingsBishopCorp.setCorpCommandAvailability(true);
        queensBishopCorp.setCorpCommandAvailability(true);

        AI_kingCorp.setCorpCommandAvailability(true);
        AI_kingsBishopCorp.setCorpCommandAvailability(true);
        AI_queensBishopCorp.setCorpCommandAvailability(true);
    }

}
