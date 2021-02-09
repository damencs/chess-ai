package chess.gui.controllers;

import chess.game.Board;
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

public class gui_controller implements Initializable {

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
    private StackPane gameboardPane;

    /**
     * These are temporary place holders Just to get the GUI set up.
     * Once we get the backend setup we can just reference the game handler
     * to change/set/check board. So the boardArray
     * will be removed once implementation is finished.
     */

    private int[][] boardArray = {
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0}};

    private GridPane boardGrid = new GridPane();
    private GridPane piecesGrid = new GridPane();
    private ImageView[][] boardImg = new ImageView[boardArray.length][boardArray[0].length];
    private ImageView[][] gamestate = new ImageView[boardArray.length][boardArray[0].length];
    private String imagePath = "chess/gui/images/";

    private Image ksuGold = new Image(imagePath + "ffc629.png", 20,20,true, true);
    private Image ksuGrey = new Image(imagePath + "878a89.png", 20,20,true, true);
    private Image whitePawn = new Image(imagePath + "PawnW.png", 20,20,true, true);
    private Image whiteBishop = new Image(imagePath + "BishopWhite.png", 20,20,true, true);
    private Image whiteRook = new Image(imagePath + "RookW.png", 20,20,true, true);
    private Image whiteKnight = new Image(imagePath + "knightW.png", 20,20,true, true);
    private Image whiteKing = new Image(imagePath + "KingW.png", 20,20,true, true);
    private Image whiteQueen = new Image(imagePath + "QueenW.png", 20,20,true, true);
    private Image blank = new Image(imagePath + "blank.png", 20,20,true, true);

    private Image blackPawn = new Image(imagePath + "PawnB.png", 20,20,true, true);
    private Image blackBishop = new Image(imagePath + "BishopBlack.png", 20,20,true, true);
    private Image blackRook = new Image(imagePath + "RookB.png", 20,20,true, true);
    private Image blackKnight = new Image(imagePath + "knightB.png", 20,20,true, true);
    private Image blackKing = new Image(imagePath + "KingB.png", 20,20,true, true);
    private Image blackQueen = new Image(imagePath + "QueenB.png", 20,20,true, true);
    private Board current = new Board();

    /** *************************************************************************************************** **/

    public gui_controller() throws Exception {

    }

    @FXML
    void playDefault(ActionEvent event) throws IOException {
        tabs.getSelectionModel().select(gameTab);
        onOpenDialog(event);
    }

    @FXML
    void onOpenDialog(ActionEvent event) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(getClass().getResource("/chess/gui/fxml/coinflip.fxml"));
        Parent parent = fxmlloader.load();
        coinflip_controller teamController = fxmlloader.getController();
        Scene scene = new Scene(parent, 250, 145);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        current = new Board();
        current.setupBoard(teamController.getVariable());
        displayPieces();
    }

    @FXML
    void quit(ActionEvent event) {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    void displayBoard(){
        boardGrid.setHgap(2);
        boardGrid.setVgap(2);
        for(int row = 0; row < boardArray.length; row++){
            for(int column = 0; column < boardArray[0].length; column++){
                if(boardArray[row][column] == 0){
                    boardImg[row][column] = new ImageView(ksuGold);
                }else{
                    boardImg[row][column] = new ImageView(ksuGrey);
                }
                boardImg[row][column].setFitWidth(71.25);
                boardImg[row][column].setFitHeight(64.25);
                boardGrid.add(boardImg[row][column], row, column);
            }
        }
        gameboardPane.getChildren().add(boardGrid);
    }

    /**
     * Once again a lot of this will be changed once we get the handler
     * up and running for the piece class
     */
    void displayPieces(){
        gameboardPane.getChildren().clear();
        piecesGrid.getChildren().clear();
        Tile[][] currentGameSetArray = current.getBoard();
        displayBoard();
        piecesGrid.setVgap(2);
        piecesGrid.setHgap(2);
        for(int row = 0; row < currentGameSetArray.length; row++){
            for(int column = 0; column < currentGameSetArray[0].length; column++){
                if(currentGameSetArray[row][column] == null){
                    gamestate[row][column] = new ImageView(blank);
                }else{
                    String pieceName = currentGameSetArray[row][column].getPiece().getName();
                    String pieceColor = currentGameSetArray[row][column].getColor();
                    switch(pieceName){
                        case "King":
                            if(pieceColor == "white"){
                                gamestate[row][column] = new ImageView(whiteKing);
                            }else{
                                gamestate[row][column] = new ImageView(blackKing);
                            }
                            break;
                        case "Queen":
                            if(pieceColor == "white"){
                                gamestate[row][column] = new ImageView(whiteQueen);
                            }else{
                                gamestate[row][column] = new ImageView(blackQueen);
                            }
                            break;
                        case "Rook":
                            if(pieceColor == "white"){
                                gamestate[row][column] = new ImageView(whiteRook);
                            }else{
                                gamestate[row][column] = new ImageView(blackRook);
                            }
                            break;
                        case "Bishop":
                            if(pieceColor == "white"){
                                gamestate[row][column] = new ImageView(whiteBishop);
                            }else{
                                gamestate[row][column] = new ImageView(blackBishop);
                            }
                            break;
                        case "Knight":
                            if(pieceColor == "white"){
                                gamestate[row][column] = new ImageView(whiteKnight);
                            }else{
                                gamestate[row][column] = new ImageView(blackKnight);
                            }
                            break;
                        case "Pawn":
                            if(pieceColor == "white"){
                                gamestate[row][column] = new ImageView(whitePawn);
                            }else{
                                gamestate[row][column] = new ImageView(blackPawn);
                            }
                            break;
                        default:
                            gamestate[row][column] = new ImageView(blank);
                            break;
                    }
                }
                gamestate[row][column].setFitWidth(71.25);
                gamestate[row][column].setFitHeight(64.25);
                piecesGrid.add(gamestate[row][column], column, row);
            }
            System.out.println();
        }
        gameboardPane.getChildren().add(piecesGrid);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayBoard();
    }
}
