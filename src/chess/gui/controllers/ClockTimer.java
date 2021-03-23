package chess.gui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.Timer;
import java.util.TimerTask;

/* Track total game time elapsed*/
public class ClockTimer  {

    private Label currentGameTime;

    private Button watchGameBtn;

    private Button playNowBtn;

    int hh = 00;
    int mm = 00;
    int ss = 00;

    Timer timer = new Timer();

    /* Increase seconds, then minutes..etc. */
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            ss++;
            if(ss > 60) {
                ss = 0;
                mm++;
            }

            if(mm > 60) {
                mm = 0;
                hh++;
            }
            update();
        }
    };

    /* Start timer */
    public void start() {
        timer.scheduleAtFixedRate(task, 1000,1000);
    }

    /* Stop timer */
    public void stop() {
        timer.cancel();
    }

    public String toString() {
        return hh + ":" + mm + ":" + ss;
    }

    public void update(){
        currentGameTime.setText(timer.toString());
    }

    private void onCLickStop(ActionEvent event) {
        currentGameTime.setText(toString());
        playNowBtn.setOnAction(e -> {
            start();
            update();
        });
        watchGameBtn.setOnAction(e -> {
            stop();
            update();
        });
    }

    public String getCurrentGameTime() { return toString(); }

    public void initialize() {
        getCurrentGameTime();
    }
}