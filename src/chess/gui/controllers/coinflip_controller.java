package chess.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Random;

public class coinflip_controller{

    private Boolean sourceID = false;

    @FXML
    private ImageView playerWhite;

    @FXML
    private ImageView playerBlack;

    @FXML
    private ImageView playerRand;

    public void imageClicked(MouseEvent event){
        String source = event.getPickResult().getIntersectedNode().getId();
        switch (source){
            case "playerWhite":
                sourceID = true;
                break;
            case "playerBlack":
                sourceID = false;
                break;
            case "playerRand":
                Random randomNum = new Random();
                int playersTeam = randomNum.nextInt(2);
                if(playersTeam == 1){
                    sourceID = true;
                }else{
                    sourceID = false;
                }
                break;
        }
        /**
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
         **/
        closeStage(event);
    }

    public Boolean getVariable(){
        return sourceID;
    }

    private void closeStage(MouseEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
