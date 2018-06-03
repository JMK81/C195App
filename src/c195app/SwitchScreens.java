/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.sql.SQLException;
import java.util.Locale;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author jonathankoerber
 */
public class SwitchScreens extends Application {

    public static String home = "home";
    public static String homeFile = "Home.fxml";
    public static String appointment = "appointment";
    public static String appointmentFile = "Appointment.fxml";
    public static String calender = "calender";
    public static String calenderFile = "Calender.fxml";
    public static String login = "login";
    public static String loginFile = "Login.fxml";
    public static String addCustomer = "addCustomer";
    public static String addCustomerFile = "AddCustomers.fxml";
    public static String customer = "customer";
    public static String customerFile = "Customer.fxml";
    public static String updateAppointment = "updateAppontment";
    public static String updateAppointmentFile = "UpdateAppointment.fxml";
    public static String updateCustomer = "updateCustomer";
    public static String updateCustomerFile = "UpdateCustomer.fxml";
    public static String calendarByLocation = "CalenderByLocation";
    public static String calendarByLocationFile = "CalenderByLocation.fxml";
    public static String userSchedule = "userSchedule";
    public static String userScheduleFile = "UserSchedule.fxml";
    public static String appointmentByType = "appointmentByType";
    public static String appointmentByTypeFile = "AppointmentByType.fxml";
    
    
    
    

    @Override
    public void start(Stage primaryStage) throws SQLException {
        
        Locale locale = Locale.getDefault();
        dbConnection c = new dbConnection();
        MasterController mainContainer = new MasterController();
        mainContainer.loadScreen(SwitchScreens.home, SwitchScreens.homeFile, locale);
        mainContainer.loadScreen(SwitchScreens.addCustomer, SwitchScreens.addCustomerFile, locale);
        mainContainer.loadScreen(SwitchScreens.appointment, SwitchScreens.appointmentFile, locale);
        mainContainer.loadScreen(SwitchScreens.calender, SwitchScreens.calenderFile, locale);
        mainContainer.loadScreen(SwitchScreens.customer, SwitchScreens.customerFile, locale);
        mainContainer.loadScreen(SwitchScreens.login, SwitchScreens.loginFile, locale);
        mainContainer.loadScreen(SwitchScreens.addCustomer, SwitchScreens.addCustomerFile, locale);
        mainContainer.loadScreen(SwitchScreens.calendarByLocation, SwitchScreens.calendarByLocationFile, locale);
        mainContainer.loadScreen(SwitchScreens.userSchedule, SwitchScreens.userScheduleFile, locale);
        mainContainer.loadScreen(SwitchScreens.appointmentByType, SwitchScreens.appointmentByTypeFile, locale);
        
        mainContainer.setScreen(SwitchScreens.login);

        //get appointment
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root, 1200, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
