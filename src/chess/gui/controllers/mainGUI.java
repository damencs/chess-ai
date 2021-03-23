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
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    // Dimesion of each tile so that it can be referenced later
    private final Dimension tileSize = new Dimension(71, 64);

    private final ImageView[][] boardImg = new ImageView[boardArray.length][boardArray[0].length];
    private final ImageView[][] gamestate = new ImageView[boardArray.length][boardArray[0].length];

    private final String imagePath = "chess/gui/images/";
    private final Image GOLD = new Image(imagePath + "gold.png", 20,20,true, true);
    private final Image GREY = new Image(imagePath + "grey.png", 20,20,true, true);
    private final Image BLANK = new Image(imagePath + "blank.png", 20,20,true, true);

    private GameHandler gameHandler = new GameHandler();

    /* Game Timer */
    int hours = 0;
    int minutes = 0;
    int seconds = 0;

    Timer gameTimer = new Timer();
    TimerTask gameTimerTask = new TimerTask() {
        @Override
        public void run() {
            Platform.runLater(new Runnable()
            {
                @Override
                public void run()
                {
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
                }
            });
        }
    };

    public mainGUI()
    {
    }

    @FXML
    void playDefault(ActionEvent event) throws IOException
    {
        onOpenDialog(event);
        tabs.getSelectionModel().select(gameTab);
    }

    @FXML
    void openRules(ActionEvent event) throws IOException
    {
        tabs.getSelectionModel().select(rulesTab);
    }

    @FXML
    void onOpenDialog(ActionEvent event) throws IOException
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

        gameTimer.scheduleAtFixedRate(gameTimerTask, 1000,1000);

        displayPieces();

    }

    @FXML
    void quit(ActionEvent event)
    {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    /* Display the grid of the board. */
    void displayBoard()
    {
        boardGrid.setHgap(2);
        boardGrid.setVgap(2);

        for (int row = 0; row < boardArray.length; row++) {
            for (int column = 0; column < boardArray[0].length; column++) {
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
                    gamestate[row][column].setOnMouseDragged(mouseEvent -> { dragged(mouseEvent, gamestate[vertical][horizontal]); });
                    gamestate[row][column].setOnMouseReleased(mouseEvent -> {
                        try {
                            released(piece, gamestate[vertical][horizontal], vertical, horizontal);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                gamestate[row][column].setFitWidth(tileSize.getWidth());
                gamestate[row][column].setFitHeight(tileSize.getHeight());
                piecesGrid.add(gamestate[row][column], column, row);

            }
        }
        gameboardPane.getChildren().add(piecesGrid);
    }

    /** -------- Following classes are for Mouse Event Listeners ----------- **/
    private void dragged(MouseEvent event , ImageView image)
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
            if(move.getDestination() ==  destinationCoordinates){
                gameHandler.setBoard(move.executeMove());
            }
        }
        /*if(moveX != 0 || moveY != 0)
        {
            MoveHandler.Move moveHandler = new MoveHandler.Move(gameHandler.getBoard(), piece, destinationCoordinates);
            gameHandler.setBoard(moveHandler.executeMove());
        }*/
        displayPieces();

    }
    /** ---------------------------------------------------------------------- **/
    /** ---------------------------------------------------------------------- **/

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displayBoard();
    }

    public Label getKingCCStatus() {
        return kingCCStatus;
    }

    public void setKingCCStatus(Label kingCCStatus) {
        this.kingCCStatus = kingCCStatus;
    }

    public Label getlBishopCCStatus() {
        return lBishopCCStatus;
    }

    public void setlBishopCCStatus(Label lBishopCCStatus) {
        this.lBishopCCStatus = lBishopCCStatus;
    }

    public Label getrBishopCCStatus() {
        return rBishopCCStatus;
    }

    public void setrBishopCCStatus(Label rBishopCCStatus) {
        this.rBishopCCStatus = rBishopCCStatus;
    }
}
