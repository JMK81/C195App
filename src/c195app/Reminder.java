/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author jonathankoerber
 */
public class Reminder extends TimerTask {

    String customer;
    String location;
    LocalDateTime login;
    LocalDateTime start;

    public Reminder(Appointment appt, LocalDateTime lt) {
        this.start = appt.getStart();
        this.login = lt;
        this.customer = customer;
        this.location = location;
    }

    @Override
    public void run() {
        System.out.println("Start time:" + new Date());
        doSomeWork();
        System.out.println("End time:" + new Date());
    }

    // create alarm
    private void doSomeWork() {
       // try {
            //todo  
            //get mill diffrence between start and login - 15 minetes 
            //Pereoid d = start.minusMinutes(15) - login;
            //Thread.sleep(sleepTimer.);

            Stage reminderStage = new Stage();
            StackPane reminderPane = new StackPane();

            Label label = new Label("You appointment starting with " + customer + " at " + start + " in " + location);

            Button btn1 = new Button();
            btn1.setText("OK");
            GridPane grid = new GridPane();
            grid.add(label, 0, 0, 2, 1);
            grid.add(btn1, 0, 1);
            grid.setAlignment(Pos.BASELINE_CENTER);

            grid.getColumnConstraints().add(new ColumnConstraints(110));
            grid.getColumnConstraints().add(new ColumnConstraints(110));
            grid.getRowConstraints().add(new RowConstraints(50));
            grid.getRowConstraints().add(new RowConstraints(50));

            reminderPane.getChildren().add(grid);

            Scene sceneModify = new Scene(reminderPane, 275, 150);

            reminderStage.setScene(sceneModify);
            reminderStage.show();

            btn1.setOnAction((ActionEvent e3) -> {
                reminderStage.close();
            });

//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
