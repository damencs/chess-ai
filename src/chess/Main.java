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
package chess;

import chess.game.GameHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application
{
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("gui/fxml/main.fxml"));

        primaryStage.setTitle("Chess");
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        GameHandler gameHandler = new GameHandler();
        launch(args);
    }
}
