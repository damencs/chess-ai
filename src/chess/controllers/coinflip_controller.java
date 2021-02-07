package chess.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Random;

public class coinflip_controller{

    private String sourceID = null;

    @FXML
    private ImageView playerWhite;

    @FXML
    private ImageView playerBlack;

    @FXML
    private ImageView playerRand;

    public void imageClicked(MouseEvent event){
        String source = event.getPickResult().getIntersectedNode().getId();
        if(source.equals("playerWhite")){
            sourceID = "playerWhite";
        }
        if(source.equals("playerBlack")){
            sourceID = "playerBlack";
        }
        if(source.equals("playerRand")){
            Random randomNum = new Random();
            int playersTeam = randomNum.nextInt(2);
            if(playersTeam == 1){
                sourceID = "playerWhite";
            }else{
                sourceID = "playerBlack";
            }
        }
        closeStage(event);
    }

    public String getVariable(){
        return sourceID;
    }

    private void closeStage(MouseEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
