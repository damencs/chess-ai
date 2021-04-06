package chess.gui.controllers;

import chess.game.Piece;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DiceRoll
{
    @FXML
    private Box diceCube;
    @FXML
    private ImageView topPieceDiceRoll;
    @FXML
    private ImageView bottomPieceDiceRoll;

    private int diceRoll;
    private PhongMaterial diceMat = new PhongMaterial();
    private Image map = new Image("chess/gui/images/Dice_1.png");

    private void tossDice()
    {
        RotateTransition rollTransition = new RotateTransition(Duration.seconds(2), diceCube);
        rollTransition.setByAngle(3240);
        diceRoll = (int)(Math.random()*6+1);
        rollTransition.play();

        for (int i = 0; i <= 50; i++)
        {
            if (rollTransition.getCurrentTime() == Duration.seconds(2))
            {
                break;
            }
            map = new Image("chess/gui/images/Dice_" + (int)(Math.random() * 6 + 1) + ".png");
            diceMat.setSelfIlluminationMap(map);
            diceMat.setBumpMap(map);
            diceCube.setMaterial(diceMat);
        }

        rollTransition.setOnFinished(actionEvent ->
        {
            map = new Image("chess/gui/images/Dice_" + diceRoll + ".png");
            diceMat.setSelfIlluminationMap(map);
            diceMat.setBumpMap(map);
            diceCube.setMaterial(diceMat);

            PauseTransition delay = new PauseTransition(Duration.seconds(2));
            Stage stage = (Stage) diceCube.getScene().getWindow();
            delay.setOnFinished(event -> stage.close());
            delay.play();
        });
    }

    public void setAttAndDefPieces(Piece atkPiece , Piece defPiece)
    {
        topPieceDiceRoll.setImage(atkPiece.getImage());
        bottomPieceDiceRoll.setImage(defPiece.getImage());
    }

    public int getDiceNumber()
    {
        return diceRoll;
    }

    @FXML
    public void initialize()
    {
        tossDice();
    }
}
