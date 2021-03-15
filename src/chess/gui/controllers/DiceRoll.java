package chess.gui.controllers;

import chess.game.Piece;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.sg.prism.NGTriangleMesh;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DiceRoll {
    @FXML
    private Box diceCube;
    @FXML
    private ImageView topPieceDiceRoll;
    @FXML
    private ImageView bottomPieceDiceRoll;
    @FXML
    private MeshView meshTest;

    private int diceRoll;
    private PhongMaterial diceMat = new PhongMaterial();
    private Image map = new Image("chess/gui/images/Dice_4.png");

    public void prepareCube(){

        diceMat.setSelfIlluminationMap(map);
        diceMat.setBumpMap(map);
        diceCube.setMaterial(diceMat);

    }

    private void tossDice() {
        RotateTransition rollTransition = new RotateTransition(Duration.seconds(2), diceCube);
        rollTransition.setByAngle(3240);
        diceRoll = (int)(Math.random()*6+1);
        rollTransition.play();
        switch (diceRoll) {
            case 1 -> {
                map = new Image("chess/gui/images/Dice_1.png");
                diceMat.setSelfIlluminationMap(map);
                diceMat.setBumpMap(map);
                diceCube.setMaterial(diceMat);
            }
            case 2 -> {
                map = new Image("chess/gui/images/Dice_2.png");
                diceMat.setSelfIlluminationMap(map);
                diceMat.setBumpMap(map);
                diceCube.setMaterial(diceMat);
            }
            case 3 -> {
                map = new Image("chess/gui/images/Dice_3.png");
                diceMat.setSelfIlluminationMap(map);
                diceMat.setBumpMap(map);
                diceCube.setMaterial(diceMat);
            }
            case 4 -> {
                map = new Image("chess/gui/images/Dice_4.png");
                diceMat.setSelfIlluminationMap(map);
                diceMat.setBumpMap(map);
                diceCube.setMaterial(diceMat);
            }
            case 5 -> {
                map = new Image("chess/gui/images/Dice_5.png");
                diceMat.setSelfIlluminationMap(map);
                diceMat.setBumpMap(map);
                diceCube.setMaterial(diceMat);
            }
            case 6 -> {
                map = new Image("chess/gui/images/Dice_6.png");
                diceMat.setSelfIlluminationMap(map);
                diceMat.setBumpMap(map);
                diceCube.setMaterial(diceMat);
            }
        }

        rollTransition.setOnFinished(actionEvent -> {
            Stage stage = (Stage) diceCube.getScene().getWindow();
            stage.close();
        });
    }

    public void setAttAndDefPieces(Piece atkPiece , Piece defPiece){
        topPieceDiceRoll.setImage(atkPiece.getImage());
        bottomPieceDiceRoll.setImage(defPiece.getImage());
    }

    public int getDiceNumber(){ return diceRoll; }

    @FXML
    public void initialize() {
        prepareCube();
        tossDice();
    }
}
