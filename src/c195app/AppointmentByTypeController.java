/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class AppointmentByTypeController implements Initializable, ControlledScreen {

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
    private static final ObservableList<type> pair = FXCollections.observableArrayList();
    @FXML
    TableView<type> tv;
    @FXML
    private TableColumn<type, String> apptType;
    @FXML
    private TableColumn<type, Integer> numberOfAppt; 
    
    
    @Override
    public void setScreenParent(MasterController screenParent){
        myController = screenParent;
    }
     @Override//localUser resourcebundle is that url used as file location??
    public void initialize(URL url, ResourceBundle rb) {
     
         apptType.setCellValueFactory(new PropertyValueFactory<type, String>("type"));
         numberOfAppt.setCellValueFactory(new PropertyValueFactory<type, Integer>("number"));
         
         //adds data to the table view
         try{tv.setItems(getAppointmentData());}
         catch(SQLException e1){
             System.out.println("sql date error getAppointmnents");
         }
       
    }
    
    public static ObservableList<type> getAppointmentData()throws SQLException {
        //Todo number dont display
        //there is one appoinment in the db should pull one file
        try (Connection connection = dbConnection.getDataSource().getConnection();){ 
            //data to creat an observable list based where the year matches
            //create a view that contains all fields for calender display

            PreparedStatement statement = connection.prepareStatement(
                    "select title , count(*) as count from appointment group by title having count >0"
            );
           
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {
                
                int number = rs.getInt("count");
                String name = rs.getString("title");
                

                type t = new type(name, number);
                //(1, "name", "title", "description", "location", "user", "")
                pair.add(t);
                
                        
            }
        } catch (SQLException ex) {
            System.out.println("Sql error");
            ex.printStackTrace();
           
        } catch (Exception e) {
            System.out.println("Error try again");
            e.printStackTrace();
        } 
        
        return pair;
        
    }
   
    
    @FXML
     public void goBack(ActionEvent event){
       myController.setScreen(SwitchScreens.home);
    }
     
   
 // method change display of week and month maybe limit view
    
    
}
