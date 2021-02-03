package chess;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/*
    Name: Tyra Buadoo
    Date: 2-3-2021
    Desc: A Chess AI variant game for Senior Project
*/

/**
 * Learning Material:
 *
 * Branches, Commit, Pull Request, Merge in IntelliJ IDEA and GitHub: Example with Code- https://www.youtube.com/watch?v=jEN3D9EN7ss
 * Chess Board Examples
        https://javabook.bloomu.edu/code-html/Chapter6/CheckerBoard.html
        https://codereview.stackexchange.com/questions/187925/javafx-checkers-game
        https://github.com/RamyaPayyavula/CheckerBoard// Chess Board Examples
        https://javabook.bloomu.edu/code-html/Chapter6/CheckerBoard.html
        https://codereview.stackexchange.com/questions/187925/javafx-checkers-game
        https://github.com/RamyaPayyavula/CheckerBoard

    How to Install Java FX for IntelliJ
        Setup JavaFX 11, 13, 14 or 15 on IntelliJ - Quick and Easy (2020)- https://www.youtube.com/watch?v=exTe8Bvtrg0
        (Best explanation so follow this on intead)

        How to Setup IntelliJ 2020.2.2 for JavaFX 15, JDK 15, Scene Builder 11.0.0 on Windows 10 x64- https://www.youtube.com/watch?v=RyTn2zGO0e0
        (When it adds the path in VM options [if you don't see VM options, click on "modify options" and add it], use the command
        that this user uses since they are using Windows and the command in the video above is for Mac).

    1 - Setup JavaFX and Scene Builder with IntelliJ IDEA on Windows 10- https://www.youtube.com/watch?v=ElKRqK87YMs
    (How to include scene builder to this IDE)
 *
 * **/


public class Main extends Application {

//    private final int COUNT_LIMIT = 500000;
//    @Override
//    public void init()throws Exception{
//        for(int i = 0; i < COUNT_LIMIT; i++){
//            double progress = (100 * i) /COUNT_LIMIT;
//            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
//        }
//    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene mainScene = new Scene(root);
        mainScene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }


    public static void main(String[] args) {

        // Launch Preloader before main
        //LauncherImpl.launchApplication(Main.class, SplashScreenMain.class, args);

        launch(args);
    }

}
