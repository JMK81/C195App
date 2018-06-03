/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class CustomerController implements Initializable, ControlledScreen {

    MasterController myController;

    /**
     * Initializes the controller class.
     *
     */
    public static ObservableList<Customer> customers = FXCollections.observableArrayList();
    @FXML
    TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> customerPhoneCol;
    @FXML
    private TableColumn<Customer, String> customerCityCol;
    @FXML
    private TableColumn<Customer, String> customerAddressCol;
    @FXML
    Label message;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        customerNameCol.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        customerCityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        try {
            //customers.setAll(getCustomerData());
            customerTableView.setItems(getCustomerData());
        } catch (SQLException e) {
            System.out.println("SQL error getCustomerData ");
            e.printStackTrace();
        }
    }

    @Override
    public void setScreenParent(MasterController screenParent) {
        myController = screenParent;
    }
    public static void upDateCustomer(Customer c){
        for(Customer cus : customers){
            if(cus.getCustomerId() == c.getCustomerId()){
                cus = c;
                
            }}
                
    }

    public static ObservableList<Customer> getCustomerData() throws SQLException {
        try (Connection connection = dbConnection.getDataSource().getConnection();) {
            //data to creat an observable list based where the year matches
            //create a view that contains all fields for calender display

            PreparedStatement statement = connection.prepareStatement(" Select customerId, customerName,\n"
                    + "address.phone, customer.addressId, address, address2, postalCode, \n"
                    + "customer.addressId, city.cityId, city, city.countryId, customer.createDate, customer.createdBy, customer.lastUpdate, \n"
                    + "customer.lastUpdateBy from customer, address, city\n"
                    + "where customer.addressId = address.addressId AND address.cityId = city.cityId");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss aa");
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                String phone = rs.getString("address.phone");
                int addressId = rs.getInt("addressId");
                String address = rs.getString("address");
                String address2 = rs.getString("address2");
                String postalCode = rs.getString("postalCode");
                int cityId = rs.getInt("city.cityId");
                int countryId = rs.getInt("city.countryId");
                String city = rs.getString("city");
                Date createDate = rs.getTimestamp("createDate");
                formatter.format(createDate);
                String createdBy = rs.getString("createdBy");
                formatter.format(createDate);
                Date lastUpdate = rs.getTimestamp("lastUpdate");
                formatter.format(lastUpdate);
                String lastUpdateBy = rs.getString("lastUpdateBy");

                Customer customer = new Customer(customerId, customerName, phone, addressId, address, address2,
                        postalCode, cityId, countryId, city, createDate, createdBy, lastUpdate, lastUpdateBy);

                customers.add(customer);
                
            }
            
        } catch (SQLException ex) {
            System.out.println("Sql error get customer data");
            ex.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error try again");
            e.printStackTrace();
        }
        return customers;

    }

    public static void addCustomer(Customer c) {
        customers.add(c);
        System.out.println("customer being added to customerslist");
    }

    @FXML
    public void goBack(ActionEvent event) {
        myController.setScreen(SwitchScreens.home);
    }

    @FXML
    public void upDate(ActionEvent event) throws IOException {
          Customer sc;
        try{
    sc = (Customer) customerTableView.getSelectionModel().getSelectedItem();
        
    Stage stage = new Stage();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateCustomer.fxml"));
    Parent root = (Parent) loader.load();
    UpdateCustomerController controller = (UpdateCustomerController) loader.getController();
    controller.setValues(sc);
    stage.setScene(new Scene(root));
    stage.setTitle("Update Costomer");
    stage.setResizable(false);
    stage.showAndWait();
    stage.toFront();}
        catch(NullPointerException e){
            message.setText("To Update a Customer please first select form the list");
        }
    
    }

}
