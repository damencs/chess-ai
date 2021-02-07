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

    private int[][] currentGameSetArray = {
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,1,0,1,0,1,0,1},
            {1,0,1,0,1,0,1,0}};

    private ImageView[][] boardImg = new ImageView[boardArray.length][boardArray[0].length];
    private Image ksuGold = new Image("chess/Images/ffc629.png", 20,20,true, true);
    private Image ksuGrey = new Image("chess/Images/878a89.png", 20,20,true, true);


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


        Scene scene = new Scene(parent, 250, 145);
        scene.setFill(Color.TRANSPARENT);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void quit(ActionEvent event) {
        Stage stage = (Stage) quitBtn.getScene().getWindow();
        stage.close();
    }

    void displayBoard(){
        GridPane boardGrid = new GridPane();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayBoard();
    }
}
