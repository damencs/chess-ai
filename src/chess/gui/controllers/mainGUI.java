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
package chess.gui.controllers;

import chess.game.*;
import javafx.animation.PathTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class mainGUI implements Initializable
{
    /* TODO: Finish implementation of the rest of the buttons inside this GUI.
             - Find better pictures to utilize within the project. */

    @FXML
    private Pane gambitMenu;
    @FXML
    private Button playNowBtn;
    @FXML
    private Button watchGameBtn;
    @FXML
    private Button howToPlayBtn;
    @FXML
    private Label currentGameTime;
    @FXML
    private Button quitBtn;
    @FXML
    private TabPane tabs;
    @FXML
    private Tab newgameTab;
    @FXML
    private Tab gameTab;
    @FXML
    private Tab helpTab;
    @FXML
    private Tab rulesTab;
    @FXML
    private StackPane gameboardPane;
    @FXML
    private ImageView kingCCIMG;
    @FXML
    private ImageView bishopLCCIMG;
    @FXML
    private ImageView bishopRCCIMG;
    @FXML
    private Label kingCCStatus;
    @FXML
    private Label lBishopCCStatus;
    @FXML
    private Label rBishopCCStatus;
    @FXML
    private TextArea gameLog;

    /* Color Evaluation Grid for the Board */
    private final int[][] boardArray =
            {
                {0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0},
                {0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0},
                {0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0},
                {0,1,0,1,0,1,0,1},
                {1,0,1,0,1,0,1,0}
            };
    private final GridPane boardGrid = new GridPane();
    private final GridPane piecesGrid = new GridPane();
    private final ColorAdjust blackout = new ColorAdjust();
    private final ColorAdjust color = new ColorAdjust();
    private final Lighting lighting = new Lighting();
    private final Lighting lightingCommander = new Lighting();

    // Dimesion of each tile so that it can be referenced later
    private final Dimension tileSize = new Dimension(71, 64);

    private final ImageView[][] boardImg = new ImageView[boardArray.length][boardArray[0].length];
    private final ImageView[][] gamestate = new ImageView[boardArray.length][boardArray[0].length];

    private final String imagePath = "chess/gui/images/";
    private final Image GOLD = new Image(imagePath + "gold.png", 20,20,true, true);
    private final Image GREY = new Image(imagePath + "grey.png", 20,20,true, true);
    private final Image BLANK = new Image(imagePath + "blank.png", 20,20,true, true);
    private final Image SELECT = new Image(imagePath + "MoveSelect.gif", 20,20,true, true);
    private final Color availableColor = Color.rgb(123,255,123);
    private final Color unavailableColor = Color.rgb(255,97,97);
    private final Color capturedColor = Color.rgb(48,48,48);

    private boolean rBishopCaptured = false;
    private boolean lBishopCaptured = false;
    private boolean kingCaptured = false;

    private final char[] rowLetter = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    private GameHandler gameHandler = new GameHandler();
    private AiBrain aiBrain;

    /* Game Timer */
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    boolean gameTimerRunning = false;

    Timer gameTimer = new Timer();
    TimerTask gameTimerTask;

    public mainGUI()
    {
    }

    @FXML
    void playDefault(ActionEvent event) throws IOException
    {
        onOpenDialog();
        tabs.getSelectionModel().select(gameTab);
    }

    @FXML
    void endTurn() throws IOException {
        gameLog.appendText("\tPLAYER has ended their turn.\r\n");
        gameHandler.updatePlayerTurn(false);
        AI_Turn();
    }

    void AI_Turn() throws IOException {

        aiBrain.AI_turn(gameHandler.getBoard());

        executeAIMove(aiBrain.executeKingCorp());
        executeAIMove(aiBrain.executeKingBishopCorp());
        executeAIMove(aiBrain.executeQueensBishopCorp());

        for(Piece piece : gameHandler.getBoard().getAlivePieces()){
            piece.getCorp().setCorpCommandAvailability(true);
            if(piece.isPlayerPiece())
                switchCorpLabel(piece);
        }
        gameHandler.updatePlayerTurn(true);
        gameLog.appendText("\tAI has ended their turn.\r\n");
        displayPieces();
    }

    void executeAIMove(MoveHandler aiMoves) throws IOException {
        if(aiMoves != null){
            ArrayList<Piece> ai_pieces = (aiBrain.getAiTeamColor().equals("white"))
                    ? (ArrayList<Piece>) gameHandler.getBoard().getWhitePieces() : (ArrayList<Piece>) gameHandler.getBoard().getBlackPieces();

            for(Piece ai_piece : ai_pieces){
                ArrayList<MoveHandler> moves = ai_piece.determineMoves(gameHandler.getBoard());
                for(MoveHandler move : moves){
                    if(move.getDestination() == aiMoves.getDestination() && move.getMovingPiece().getName().equals(aiMoves.getMovingPiece().getName()) &&
                            move.getMovingPiece().getCoordinates() == aiMoves.getMovingPiece().getCoordinates()) {

                        gameHandler.setBoard(move.executeMove());

                        if(move.isGameOver()){
                            endGame(move);
                        }

                        if (!move.toString().equals("")) {
                            gameLog.appendText(move.toString() + "\r\n");
                        }
                        if (!move.toString().contains("FAIL")) {
                            gameLog.appendText("[AI] " + ai_piece.getColor().toUpperCase() + " " + ai_piece.getName() + ": "
                                    + posToString(ai_piece.getCoordinates()) + " -> " + posToString(move.getDestination()) + "\r\n");
                        }
                        else
                        {
                            gameLog.appendText("[AI] ATTEMPTED " + ai_piece.getColor().toUpperCase() + " " + ai_piece.getName() + ": "
                                    + posToString(ai_piece.getCoordinates()) + " -> " + posToString(move.getDestination()) + "\r\n");
                        }
                        displayPieces();
                    }
                }
            }
        }
    }

    @FXML
    void openRules(ActionEvent event) throws IOException
    {
        tabs.getSelectionModel().select(rulesTab);
    }

    @FXML
    void onOpenDialog() throws IOException
    {
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(getClass().getResource("/chess/gui/fxml/coinflip.fxml"));
        Parent parent = fxmlloader.load();
        coinflip teamController = fxmlloader.getController();

        Scene scene = new Scene(parent, 250, 145);
        scene.setFill(Color.TRANSPARENT);

        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        gameHandler = new GameHandler();
        gameHandler.updatePlayerTurn(teamController.getPlayerTurnChoice());
        gameHandler.setBoard();
        gameHandler.getBoard().resetCorpAvailability();
        aiBrain = new AiBrain();

        if (gameTimerRunning) {
            gameTimerTask.cancel();
            createTimerTask();
        } else {
            createTimerTask();
        }

        gameTimer.scheduleAtFixedRate(gameTimerTask, 1000,1000);

        /* Resets game log text since new game generated */
        gameLog.setText("");

        displayPieces();

        if(!gameHandler.isPlayerTurn()){
            AI_Turn();
        }
    }

    @FXML
    void quit(ActionEvent event)
    {
        if (gameTimerRunning) {
            gameTimerTask.cancel();
        }
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    /* Display the grid of the board. */
    void displayBoard()
    {
        boardGrid.setHgap(2);
        boardGrid.setVgap(2);

        for (int row = 0; row < boardArray.length; row++)
        {
            for (int column = 0; column < boardArray[0].length; column++)
            {
                boardImg[row][column] = new ImageView(boardArray[row][column] == 0 ? GOLD : GREY);
                boardImg[row][column].setFitHeight(tileSize.getHeight());
                boardImg[row][column].setFitWidth(tileSize.getWidth());
                boardGrid.add(boardImg[row][column], row, column);
            }
        }
        gameboardPane.getChildren().add(boardGrid);
    }

    /* Display Piece based off current board data. */
    void displayPieces()
    {
        gameboardPane.getChildren().clear();
        piecesGrid.getChildren().clear();
        Image king = (gameHandler.isPlayerTurn()) ? new Image("chess/gui/images/whiteKing.png") : new Image("chess/gui/images/blackKing.png");
        Image bishop = (gameHandler.isPlayerTurn()) ? new Image("chess/gui/images/whiteBishop.png") : new Image("chess/gui/images/blackBishop.png");
        kingCCIMG.setImage(king);
        bishopLCCIMG.setImage(bishop);
        bishopRCCIMG.setImage(bishop);

        kingCCIMG.setVisible(true);
        kingCCStatus.setVisible(true);
        bishopLCCIMG.setVisible(true);
        lBishopCCStatus.setVisible(true);
        bishopRCCIMG.setVisible(true);
        rBishopCCStatus.setVisible(true);

        Tile[][] tiles = gameHandler.getBoardTiles();
        displayBoard();

        piecesGrid.setVgap(2);
        piecesGrid.setHgap(2);

        for(int row = 0; row < tiles.length; row++) {
            for(int column = 0; column < tiles[0].length; column++) {
                if(!tiles[row][column].isOccupied()) {
                    gamestate[row][column] = new ImageView(BLANK);
                }
                else {
                    /* Creates a MouseEventListener that allows player interaction with Piece */
                    int vertical = row;
                    int horizontal = column;
                    Piece piece = tiles[row][column].getPiece();
                    gamestate[row][column] = new ImageView(piece.getImage());
                    // TODO: once ai is working, make listener for only player pieces
                    if(piece.getCorp().isCommandAvailable() && piece.isPlayerPiece()){
                        gamestate[row][column].setOnMouseDragged(mouseEvent -> { dragged(mouseEvent, gamestate[vertical][horizontal]); });
                        gamestate[row][column].setOnMouseReleased(mouseEvent -> {
                            try {
                                released(piece, gamestate[vertical][horizontal], vertical, horizontal);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        gamestate[row][column].setOnMousePressed(mouseEvent -> { pressed(piece); });
                    }
                    /* Grays out unavailable corps */
                    else if(!piece.getCorp().isCommandAvailable() && piece.isPlayerPiece())
                    {
                        if(piece.getColor() == "black")
                            blackout.setBrightness(0.3);
                        else
                            blackout.setBrightness(-0.5);

                        gamestate[vertical][horizontal].setEffect(blackout);
                        gamestate[vertical][horizontal].setCache(true);
                        gamestate[vertical][horizontal].setCacheHint(CacheHint.SPEED);
                    }
                }
                gamestate[row][column].setFitWidth(tileSize.getWidth());
                gamestate[row][column].setFitHeight(tileSize.getHeight());
                piecesGrid.add(gamestate[row][column], column, row);

            }
        }
        gameboardPane.getChildren().add(piecesGrid);
    }

    /** -------- Following classes are for Mouse Event Listeners ----------- **/
    private void pressed (Piece piece)
    {
        /* Visually show possible moves on Board */
        ArrayList<MoveHandler> moves = piece.determineMoves(gameHandler.getBoard());
        for (MoveHandler move : moves)
        {
            int row = move.getDestination() % 8;
            int column = move.getDestination() / 8;

            ImageView Selection = new ImageView(SELECT);
            Selection.setFitWidth(tileSize.getWidth());
            Selection.setFitHeight(tileSize.getHeight());

            boardGrid.add(Selection, row, column);
        }

        /* Changes color of Corp while dragging */
        ArrayList<Piece> corpPieces;
        if (piece.getColor().equals("black"))
            corpPieces = gameHandler.getBoard().getBlackCorpPieces(piece.getCorp().getCorpName());
        else
            corpPieces = gameHandler.getBoard().getWhiteCorpPieces(piece.getCorp().getCorpName());

        for (Piece pieces : corpPieces)
        {
            if (pieces.getName().equals("Bishop") || pieces.getName().equals("King")) {
                int column = pieces.getCoordinates() % 8;
                int row = pieces.getCoordinates() / 8;
                ImageView pieceImage = gamestate[row][column];
                color.setBrightness(0.9);

                lightingCommander.setDiffuseConstant(1.0);
                lightingCommander.setSpecularConstant(0.0);
                lightingCommander.setSpecularExponent(0.0);
                lightingCommander.setSurfaceScale(0.0);
                lightingCommander.setLight(new Light.Distant(45, 45, unavailableColor));

                lightingCommander.setContentInput(color);
                pieceImage.setEffect(lightingCommander);
            } else {
                int column1 = pieces.getCoordinates() % 8;
                int row1 = pieces.getCoordinates() / 8;
                ImageView pieceImage1 = gamestate[row1][column1];
                color.setBrightness(0.9);

                lighting.setDiffuseConstant(1.0);
                lighting.setSpecularConstant(0.0);
                lighting.setSpecularExponent(0.0);
                lighting.setSurfaceScale(0.0);
                lighting.setLight(new Light.Distant(45, 45, availableColor));

                lighting.setContentInput(color);
                pieceImage1.setEffect(lighting);
            }
        }
    }

    private void dragged(MouseEvent event, ImageView image)
    {
        image.setX(event.getX() - (float)tileSize.width/2);
        image.setY(event.getY() - (float)tileSize.height/2);
        draw(image);
    }

    private void draw(ImageView image)
    {
        image.setTranslateX(image.getX());
        image.setTranslateY(image.getY());
    }

    private void released(Piece piece, ImageView image, int vertical, int horizontal) throws IOException {
        ArrayList<MoveHandler> moves = piece.determineMoves(gameHandler.getBoard());
        int moveX = Math.round((float)image.getX() / tileSize.width);
        int moveY = Math.round((float)image.getY() / tileSize.height);

        /* Determine if the coordinate the space is trying to move to is a valid move */
        int destinationCoordinates = (8 * (vertical+moveY)) + (horizontal+moveX);
        for(MoveHandler move : moves){
            if(move.getDestination() ==  destinationCoordinates)
            {
                gameHandler.setBoard(move.executeMove());
                if (!move.toString().equals(""))
                {
                    gameLog.appendText(move.toString() + "\r\n");
                }
                if (!move.toString().contains("FAIL"))
                {
                    gameLog.appendText("[PL] " + piece.getColor().toUpperCase() + " " + piece.getName() + ": " + posToString(piece.getCoordinates()) + " -> " + posToString(move.getDestination()) + "\r\n");
                }
                else
                {
                    gameLog.appendText("[PL] ATTEMPTED " + piece.getColor().toUpperCase() + " " + piece.getName() + ": " + posToString(piece.getCoordinates()) + " -> " + posToString(move.getDestination()) + "\r\n");
                }

                piece.getCorp().switchCorpCommandAvailablity();
                switchCorpLabel(piece);
                if(move.isGameOver()){
                    endGame(move);
                }
            }
        }
        displayPieces();
    }
    /** ---------------------------------------------------------------------- **/
    /** ---------------------------------------------------------------------- **/

    private void endGame(MoveHandler move) throws IOException {
        FXMLLoader GameOverfxmlloader = new FXMLLoader();
        GameOverfxmlloader.setLocation(getClass().getResource("/chess/gui/fxml/gameover.fxml"));
        Parent parent = GameOverfxmlloader.load();
        GameOver gameOver = GameOverfxmlloader.getController();
        gameOver.setWinner(move.getWinner());
        Scene gameOverScene = new Scene(parent, 390, 205);
        gameOverScene.setFill(Color.TRANSPARENT);

        Stage gameOverStage = new Stage();
        gameOverStage.initStyle(StageStyle.TRANSPARENT);
        gameOverStage.initModality(Modality.APPLICATION_MODAL);
        gameOverStage.setScene(gameOverScene);
        gameOverStage.showAndWait();

        if(gameOver.getPlayerChoice() > 0){
            if (gameTimerRunning) {
                gameTimerTask.cancel();
            }
            Stage stage = (Stage) quitBtn.getScene().getWindow();
            stage.close();
        }else{
            onOpenDialog();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displayBoard();
    }

    private String posToString(int value)
    {
        return rowLetter[value % 8] + String.valueOf((value / 8) + 1);
    }

    private void checkCaptured(){
        ArrayList<Piece> pieces = (ArrayList<Piece>) gameHandler.getBoard().getPlayerPieces();
        rBishopCaptured = true;
        lBishopCaptured = true;
        kingCaptured = true;
        for(Piece piece : pieces){
            if(piece.getName() == "Bishop"){
                if(piece.getCorp().getCorpName() == "kingsBishop"){
                    rBishopCaptured = false;
                }
                if(piece.getCorp().getCorpName() == "queensBishop"){
                    lBishopCaptured = false;

                }
            }
            if(piece.getName() == "King"){
                kingCaptured = false;
            }
        }

    }
    private void switchCorpLabel(Piece piece)
    {
        boolean available = piece.getCorp().isCommandAvailable();
        checkCaptured();
        switch (piece.getCorp().getCorpName()) {
            case "king" -> {
                if (!available) {
                    kingCCStatus.setText("Unavailable");
                    kingCCStatus.setTextFill(unavailableColor);
                } else {
                    kingCCStatus.setText("Available");
                    kingCCStatus.setTextFill(availableColor);
                }
            }
            case "kingsBishop" -> {
                if(!available) {
                    rBishopCCStatus.setText("Unavailable");
                    rBishopCCStatus.setTextFill(unavailableColor);
                }
                else{
                    rBishopCCStatus.setText("Available");
                    rBishopCCStatus.setTextFill(availableColor);
                }
            }
            case "queensBishop" -> {
                if(!available) {
                    lBishopCCStatus.setText("Unavailable");
                    lBishopCCStatus.setTextFill(unavailableColor);
                }
                else{
                    lBishopCCStatus.setText("Available");
                    lBishopCCStatus.setTextFill(availableColor);
                }
            }
        }

        if(kingCaptured) {
            kingCCStatus.setText("Captured");
            kingCCStatus.setTextFill(capturedColor);
        }
        if(rBishopCaptured){
            rBishopCCStatus.setText("Captured");
            rBishopCCStatus.setTextFill(capturedColor);
        }
        if(lBishopCaptured){
            lBishopCCStatus.setText("Captured");
            lBishopCCStatus.setTextFill(capturedColor);
        }
    }

    private void resetTimer()
    {
        hours = 0;
        minutes = 0;
        seconds = 0;
        gameTimerRunning = false;
        currentGameTime.setText("00:00:00");
    }

    private void createTimerTask()
    {
        resetTimer();
        gameTimerTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        String outputText = "";

                        seconds++;
                        if (seconds >= 60) {
                            seconds = 0;
                            minutes++;
                        }

                        if (minutes >= 60) {
                            minutes = 0;
                            hours++;
                        }

                        if (hours > 0) {
                            if (hours < 10) {
                                outputText = "0" + hours + ":";
                            } else {
                                outputText = hours + ":";
                            }
                        }

                        if (minutes > 0) {
                            if (hours > 0) {
                                outputText += minutes + ":";
                            } else {
                                outputText = "00:" + minutes + ":";
                            }
                        }

                        if (hours == 0 && minutes == 0) {
                            if (seconds < 10) {
                                outputText = "00:00:0" + seconds;
                            } else {
                                outputText = "00:00:" + seconds;
                            }
                        } else {
                            if (seconds < 10) {
                                outputText += "0" + seconds;
                            } else {
                                outputText += seconds;
                            }
                        }

                        currentGameTime.setText(outputText);

                        if (!gameTimerRunning) {
                            gameTimerRunning = true;
                        }
                    }
                });
            }
        };
    }
}
