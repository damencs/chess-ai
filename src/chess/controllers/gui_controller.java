package chess.controllers;

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

    private int[][] boardArray = {
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0}};

    private int[][] playerBlack = {
            {2,1,0,0,0,0,7,8},
            {3,1,0,0,0,0,7,9},
            {4,1,0,0,0,0,7,10},
            {5,1,0,0,0,0,7,12},
            {6,1,0,0,0,0,7,11},
            {4,1,0,0,0,0,7,10},
            {3,1,0,0,0,0,7,9},
            {2,1,0,0,0,0,7,8}};
    private int[][] playerWhite = {
            {8,7,0,0,0,0,1,2},
            {9,7,0,0,0,0,1,3},
            {10,7,0,0,0,0,1,4},
            {12,7,0,0,0,0,1,5},
            {11,7,0,0,0,0,1,6},
            {10,7,0,0,0,0,1,4},
            {9,7,0,0,0,0,1,3},
            {8,7,0,0,0,0,1,2}};

    private GridPane boardGrid = new GridPane();
    private GridPane piecesGrid = new GridPane();
    private ImageView[][] boardImg = new ImageView[boardArray.length][boardArray[0].length];
    private ImageView[][] gamestate = new ImageView[boardArray.length][boardArray[0].length];

    private Image ksuGold = new Image("chess/Images/ffc629.png", 20,20,true, true);
    private Image ksuGrey = new Image("chess/Images/878a89.png", 20,20,true, true);
    private Image whitePawn = new Image("chess/Images/PawnW.png", 20,20,true, true);
    private Image whiteBishop = new Image("chess/Images/BishopWhite.png", 20,20,true, true);
    private Image whiteRook = new Image("chess/Images/RookW.png", 20,20,true, true);
    private Image whiteKnight = new Image("chess/Images/knightW.png", 20,20,true, true);
    private Image whiteKing = new Image("chess/Images/KingW.png", 20,20,true, true);
    private Image whiteQueen = new Image("chess/Images/QueenW.png", 20,20,true, true);
    private Image blank = new Image("chess/Images/blank.png", 20,20,true, true);

    private Image blackPawn = new Image("chess/Images/PawnB.png", 20,20,true, true);
    private Image blackBishop = new Image("chess/Images/BishopBlack.png", 20,20,true, true);
    private Image blackRook = new Image("chess/Images/RookB.png", 20,20,true, true);
    private Image blackKnight = new Image("chess/Images/knightB.png", 20,20,true, true);
    private Image blackKing = new Image("chess/Images/KingB.png", 20,20,true, true);
    private Image blackQueen = new Image("chess/Images/QueenB.png", 20,20,true, true);
    private String chosenTeam = "";


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
        fxmlloader.setLocation(getClass().getResource("/chess/fxml/coinflip.fxml"));
        Parent parent = fxmlloader.load();
        coinflip_controller teamController = fxmlloader.getController();
        Scene scene = new Scene(parent, 250, 145);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        chosenTeam = teamController.getVariable();
        System.out.println(chosenTeam);
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
                    boardImg[row][column].setFitWidth(71.25);
                    boardImg[row][column].setFitHeight(64.25);
                    boardGrid.add(boardImg[row][column], row, column);
                }else{
                    boardImg[row][column] = new ImageView(ksuGrey);
                    boardImg[row][column].setFitWidth(71.25);
                    boardImg[row][column].setFitHeight(64.25);
                    boardGrid.add(boardImg[row][column], row, column);
                }
            }
        }
        gameboardPane.getChildren().add(boardGrid);
    }

    void displayPieces(){
        gameboardPane.getChildren().clear();
        piecesGrid.getChildren().clear();
        int[][] currentGameSetArray = null;
        if(chosenTeam.equals("playerWhite")){
            currentGameSetArray = playerWhite;
        }else{
            currentGameSetArray = playerBlack;
        }
        displayBoard();
        piecesGrid.setVgap(2);
        piecesGrid.setHgap(2);
        for(int row = 0; row < currentGameSetArray.length; row++){
            for(int column = 0; column < currentGameSetArray[0].length; column++){
                switch(currentGameSetArray[row][column]){
                    case 1:
                        gamestate[row][column] = new ImageView(whitePawn);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 2:
                        gamestate[row][column] = new ImageView(whiteRook);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 3:
                        gamestate[row][column] = new ImageView(whiteKnight);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 4:
                        gamestate[row][column] = new ImageView(whiteBishop);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 5:
                        gamestate[row][column] = new ImageView(whiteKing);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 6:
                        gamestate[row][column] = new ImageView(whiteQueen);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 7:
                        gamestate[row][column] = new ImageView(blackPawn);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 8:
                        gamestate[row][column] = new ImageView(blackRook);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 9:
                        gamestate[row][column] = new ImageView(blackKnight);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 10:
                        gamestate[row][column] = new ImageView(blackBishop);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 11:
                        gamestate[row][column] = new ImageView(blackKing);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    case 12:
                        gamestate[row][column] = new ImageView(blackQueen);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                    default:
                        gamestate[row][column] = new ImageView(blank);
                        gamestate[row][column].setFitWidth(71.25);
                        gamestate[row][column].setFitHeight(64.25);
                        piecesGrid.add(gamestate[row][column], row, column);
                        break;
                }
            }
        }
        gameboardPane.getChildren().add(piecesGrid);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayBoard();
    }
}
