package chess.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.Random;

public class coinflip_controller{

    @FXML
    private ImageView playerWhite;

    @FXML
    private ImageView playerBlack;

    @FXML
    private ImageView playerRand;

    public void imageClicked(MouseEvent event){
        String sourceID = event.getPickResult().getIntersectedNode().getId();
        if(sourceID.equals("playerWhite")){
            System.out.println(sourceID);
        }
        if(sourceID.equals("playerBlack")){
            System.out.println(sourceID);
        }
        if(sourceID.equals("playerRand")){
            Random randomNum = new Random();
            int playersTeam = randomNum.nextInt(2);
            if(playersTeam == 1){
                System.out.println("playerWhite");
            }else{
                System.out.println("playerBlack");
            }
        }
        closeStage(event);
    }

    private void closeStage(MouseEvent event) {
        Node source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
