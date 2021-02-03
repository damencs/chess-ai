package chess;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.sun.javafx.application.LauncherImpl.launchApplication;

// Chess Board Examples
// https://javabook.bloomu.edu/code-html/Chapter6/CheckerBoard.html
// https://codereview.stackexchange.com/questions/187925/javafx-checkers-game
// https://github.com/RamyaPayyavula/CheckerBoard


public class Main extends Application {

//    private final int COUNT_LIMIT = 500000;
//
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
        //launchApplication(Main.class, SplashScreenMain.class, args);

        launch(args);
    }
}
