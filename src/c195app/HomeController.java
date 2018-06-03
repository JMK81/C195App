/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class HomeController implements Initializable, ControlledScreen {

     MasterController myController;
    Locale locale = Locale.getDefault();
    @Override
    public void setScreenParent(MasterController screenParent){
        myController = screenParent;
    }
    /**
     * Initializes the controller class.
     * @param 
     */
    @FXML 
    public void addCustomers(ActionEvent e){
       
        myController.setScreen(SwitchScreens.addCustomer);
    }
    @FXML
     public void viewCustomers(ActionEvent e1){
         
         myController.setScreen(SwitchScreens.customer);
     }
     @FXML 
     public void addAppointment(ActionEvent e2){
        
        myController.setScreen(SwitchScreens.appointment);
     }
     @FXML
     public void viewCallender(ActionEvent e3){
        
        myController.setScreen(SwitchScreens.calender);
         
         
     }
     //view reports. selections in the drop menu 
     public void viewUserSchedule(ActionEvent e4){
          myController.setScreen(SwitchScreens.userSchedule);
     }
     public void viewApptByOffice(ActionEvent e5){
          myController.setScreen(SwitchScreens.calendarByLocation);
     }
     public void viewTypes(ActionEvent e6){
         
         myController.setScreen(SwitchScreens.appointmentByType);
     }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
    }    
    
}
