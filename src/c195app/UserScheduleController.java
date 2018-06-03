/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import static c195app.CalenderController.appointments;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class UserScheduleController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    MasterController myController;
    /**  
     *
     * @param screenParent
     */
    LocalDateTime date = LocalDateTime.now();
    int calender = date.getYear();
    Month month = date.getMonth();
     
    //table columns to be have months add id to ]
    
    @FXML
    TableView<Appointment> tv;
    @FXML
    private TableColumn<Appointment, Date> startCol;
    @FXML
    private TableColumn<Appointment, Date> endCol; 
    @FXML
    private TableColumn<Appointment, Customer> customerCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    private TableColumn<Appointment, String> userCol;
    @FXML
    TextField userSearch;
    
    @Override
    public void setScreenParent(MasterController screenParent){
        myController = screenParent;
    }
     @Override//localUser resourcebundle is that url used as file location??
    public void initialize(URL url, ResourceBundle rb) {
     
         startCol.setCellValueFactory(new PropertyValueFactory<Appointment, Date>("start"));
         endCol.setCellValueFactory(new PropertyValueFactory<Appointment, Date>("end"));
         customerCol.setCellValueFactory(new PropertyValueFactory<Appointment, Customer>("customer"));
         locationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
         userCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contact"));
         //adds data to the table view
       tv.setItems(appointments);
         
       FilteredList<Appointment> filteredData = new FilteredList<>(appointments, p3 -> true);
        userSearch.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            filteredData.setPredicate(appt -> {
                // If filter text is empty, display all parts.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }                     
                        if (newValue == null || newValue.isEmpty()) {
                            return true;
                        }
// Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (appt.getContact().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
               }
                return false; // Does not match.
            });
     
        SortedList<Appointment> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tv.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tv.setItems(sortedData);
        });
                
    }
  
                
    
    
    @FXML
     public void goBack(ActionEvent event){
       myController.setScreen(SwitchScreens.home);
    }
   
}
