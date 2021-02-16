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

import chess.game.GameHandler;
import chess.game.Piece;
import chess.game.Tile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    /* Color Evaluation Grid for the Board */
    private int[][] boardArray =
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

    private final ImageView[][] boardImg = new ImageView[boardArray.length][boardArray[0].length];
    private final ImageView[][] gamestate = new ImageView[boardArray.length][boardArray[0].length];

    private final String imagePath = "chess/gui/images/";
    private final Image GOLD = new Image(imagePath + "gold.png", 20,20,true, true);
    private final Image GREY = new Image(imagePath + "grey.png", 20,20,true, true);
    private final Image BLANK = new Image(imagePath + "blank.png", 20,20,true, true);

    private GameHandler gameHandler = new GameHandler();

    public mainGUI()
    {
    }

    @FXML
    void playDefault(ActionEvent event) throws IOException
    {
        tabs.getSelectionModel().select(gameTab);
        onOpenDialog(event);
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
        gameHandler.getBoard().setup(gameHandler.isPlayerTurn());

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

        for (int row = 0; row < boardArray.length; row++)
        {
            for (int column = 0; column < boardArray[0].length; column++)
            {
                boardImg[row][column] = new ImageView(boardArray[row][column] == 0 ? GOLD : GREY);
                boardImg[row][column].setFitWidth(71.25);
                boardImg[row][column].setFitHeight(64.25);
                boardGrid.add(boardImg[row][column], row, column);
            }
        }

        gameboardPane.getChildren().add(boardGrid);
    }

    /* Display Pieces based off current board data. */
    void displayPieces()
    {
        gameboardPane.getChildren().clear();
        piecesGrid.getChildren().clear();

        Tile[][] currentGameSetArray = gameHandler.getBoard().getTiles();
        displayBoard();

        piecesGrid.setVgap(2);
        piecesGrid.setHgap(2);

        for(int row = 0; row < currentGameSetArray.length; row++)
        {
            for(int column = 0; column < currentGameSetArray[0].length; column++)
            {
                if(currentGameSetArray[row][column] == null)
                {
                    gamestate[row][column] = new ImageView(BLANK);
                }
                else
                {
                    Piece piece = currentGameSetArray[row][column].getPiece();
                    gamestate[row][column] = new ImageView(piece.getImage(currentGameSetArray[row][column].getColor()));
                }

                gamestate[row][column].setFitWidth(71.25);
                gamestate[row][column].setFitHeight(64.25);
                piecesGrid.add(gamestate[row][column], column, row);
            }
        }
        gameboardPane.getChildren().add(piecesGrid);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        displayBoard();
    }
}
