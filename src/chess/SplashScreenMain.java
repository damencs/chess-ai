package chess;

import chess.controllers.SplashScreenControl;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class SplashScreenMain extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    public SplashScreenMain(){
    }

    @Override
    public void init() throws Exception{

        // Load Splashscreen
        Parent root1 = FXMLLoader.load(getClass().getResource("SplashScreen.fxml"));
        scene = new Scene(root1); // Build the scene
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preloaderStage = primaryStage;

        // set preloader scene and show stage
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        preloaderStage.show();
    }

    public void handleApplicationNotification(Preloader.PreloaderNotification info){
        // Handle notification in main application

        if(info instanceof PreloaderNotification){
           SplashScreenControl.label.setText("Loading " + ((ProgressNotification) info).getProgress() + "!");
        }

    }

    public void handleStateChangeNotification(Preloader.StateChangeNotification info){
        StateChangeNotification.Type type = info.getType();
        switch (type){
            case BEFORE_START:
                // call after Main init and before main start is called
                System.out.println("BEFORE_START");
                preloaderStage.hide(); // Hide main program to show preloader
                break;
        }
    }
}
