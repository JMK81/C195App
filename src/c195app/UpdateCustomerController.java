/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import static c195app.LoginController.lu;
import static c195app.dbConnection.countrys;
import static c195app.dbConnection.citys;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class UpdateCustomerController implements Initializable {

    /**
     * Initializes the controller class.
     */
//   MasterController myController;
//    
//    @Override
//    public void setScreenParent(MasterController screenParent){
//        myController = screenParent;
//    }
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        new SelectCustomer(city, citys);
        new SelectCustomer(country, countrys);
        //citys.setAll(getCitys());
        try {
            connection = dbConnection.getDataSource().getConnection();
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AddCustomersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    Label message, updateLabel;
    @FXML
    TextField customer, address, address2, phone, postalCode;
    @FXML
    ComboBox city, country;
    String customerName;
    Date createDate;
    String createdBy;
    int addressId;
    int cusId;
    City cusCity;
    Country cusCountry;
    Customer customerUpdateValue;

    //customerToUpdate.set
    public void setValues(Customer customerToUpdate) {
        customerUpdateValue= customerToUpdate;
        createDate = customerToUpdate.getCreateDate();
        createdBy = customerToUpdate.getCreatedBy();
        customerName = customerToUpdate.getName();
        customer.setText(customerToUpdate.getCustomerName());
        address.setText(customerToUpdate.getAddress());
        address2.setText(customerToUpdate.getAddress2());
        phone.setText(customerToUpdate.getPhone());
        postalCode.setText(customerToUpdate.getPostalCode());
        addressId = customerToUpdate.getAddressId();
        cusId = customerToUpdate.getCustomerId();
        for (City c : citys) {
            if (c.getCityId() == customerToUpdate.getCityId()) {
                city.getEditor().setText(c.getName());
                //city.getEditor().setText(c.getName());
                cusCity = c;
            }
        }
        
        for (Country c : countrys) {
            if (c.getCountryId() == customerToUpdate.getCountryId()) {
                country.getEditor().setText(c.getName());
                cusCountry = c;
            }
        }
        //country.selectionModelProperty().setValue((Country) cusCountry);
        updateLabel.setText("Update " + customerToUpdate.getName() + "'s Record");

    }

    // the customer that is being updated
    //bind methods
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
    int cityId = City.getMaxCityId();
    int countryId = Country.getMaxCountryId();

    boolean hasChanged = false;
    //fixme localize date
    Calendar calendar = Calendar.getInstance();
    java.sql.Date newDate = new java.sql.Date(calendar.getTime().getTime());
    Date d = Date.from(Instant.now());
    //String newDate = formatter.format(d);
    

    //timeStamp
    @FXML
    public void upDate() throws SQLException {
        //in the combo box contains a object get object esls create an new country
        //if to test if the fields are filled in
        if (customer.getText().equals("") || address.getText().equals("")
                || address2.getText().equals("") || phone.getText().equals("") || postalCode.getText().equals("")
                || city.getEditor().getText().equals(city.getPlaceholder()) || city.getEditor().getText().equals("")
                || country.getEditor().getText().equals("")
                || country.getEditor().getText().equals(country.getPlaceholder())) {
            message.setText("Please check the input values");
        } else {
            //test to see if the values have changed from the starting text fields 
            //CustomerController.customers.remove(customerUpdateValue);
            //test if the an update new country obj needs to be made  
            if (!country.getEditor().getText().equals(cusCountry.getName())) {
                hasChanged = true;

                //if countrys list contains if it dose get the obj else make a cusCountry in in 
                if ((country.getSelectionModel().getSelectedIndex()) >= 0) {
                    cusCountry = countrys.get(country.getSelectionModel().getSelectedIndex());
                } else {
                    System.out.println("new country " + cusCountry);
                    Country newCusCountry = new Country(countryId, country.getEditor().getText(), d, lu.getName(), d, lu.getName());
                    cusCountry = newCusCountry;
                    countrys.add(newCusCountry);

                    try {
                        PreparedStatement statement = connection.prepareStatement(
                                "Insert into country(countryId, country, createDate, createdBy, lastUpdateBy)"
                                + "Values (?, ?, ?, ?, ?)");
                        statement.setInt(1, newCusCountry.getCountryId());
                        statement.setString(2, newCusCountry.getName());
                        statement.setDate(3, newDate);
                        statement.setString(4, lu.getName());
                        statement.setString(5, lu.getName());
                        System.out.println("new customer country " + newCusCountry);

                    } catch (SQLException ex) {
                        message.setText("There Is An Error Please Check your input and try again");
                        System.out.print("error createing country");
                        ex.printStackTrace();
                    }
                }
            }
            //test to see if the city object has changed

            if (!city.getEditor().getText().equals(cusCity.getName())) {
                hasChanged = true;
                //in the combo box contains a object get object esls create an new country

                if ((city.getSelectionModel().getSelectedIndex()) >= 0) {
                    cusCity = citys.get(city.getSelectionModel().getSelectedIndex());
                } else {
                    City newCusCity = new City(cityId, cusCountry.getCountryId(), city.getEditor().getText(), d, lu.getName(), d, lu.getName());
                    cusCity = newCusCity;
                    citys.add(newCusCity);
                    try {

                        PreparedStatement statement = connection.prepareStatement(
                                "Insert into city(cityId, city, countryId, createDate, createdBy, lastUpdateBy)"
                                + "Values(?, ?, ?, ?, ?, ?)");
                        statement.setInt(1, newCusCity.getCityId());
                        statement.setString(2, newCusCity.getName());
                        statement.setInt(3, cusCountry.getCountryId());
                        statement.setDate(4, newDate);
                        statement.setString(5, lu.getName());
                        statement.setString(6, lu.getName());

                        statement.executeUpdate();
                        System.out.println("new customer city " + cusCity);

                        System.out.println("a new city was created : " + cusCity.getName());

                    } catch (SQLException ex) {
                        message.setText("There Is An Error Please Check your input and try again");
                        System.out.print("error createing city");
                        ex.printStackTrace();
                    }
                }
            }//close city test
            
            String cusName = customer.getText();
            String cusAdd = address.getText();
            String cusAdd2 = address2.getText();
            String cusPhone = phone.getText();
            String cusPostal = postalCode.getText();

            try {
                System.out.println("UPDATE CUSTOMER TRY");
                PreparedStatement statement2 = connection.prepareStatement(
                        "Update address "
                        + "set address = ?, address2 = ?, cityId = ?, postalCode = ?, phone = ?, lastUpdateBy = ?"
                        + "Where addressId = ?");
                //addressId, address, address2, cityId, postalCode, phone, createDate, lastUpdate, createdBy, lastUpdateBy

                statement2.setString(1, cusAdd);
                statement2.setString(2, cusAdd2);
                statement2.setInt(3, cusCity.getCityId());
                statement2.setString(4, cusPostal);
                statement2.setString(5, cusPhone);
                statement2.setString(6, lu.getName());
                statement2.setInt(7, addressId);

                statement2.executeUpdate();

                if (!customerName.equals(customer.getText())) {
                    PreparedStatement statement = connection.prepareStatement(
                            "update  customer"
                            + " SET customerName = ?, lastUpdateBy = ?"
                            + "where customerId = ?");

                    statement.setString(1, cusName);
                    statement.setString(2, lu.getName());
                    statement.setInt(3, cusId);
                    statement.executeUpdate();
                    System.out.println("UPDATE CUSTOMER " + cusName);

                }

            } catch (SQLException ex) {
                message.setText("There Is An Error with the database Please Check your input and try again");
                ex.printStackTrace();
            } catch (Exception e) {
                message.setText("There is an Error with you data please try again");
                e.printStackTrace();

            }
            //todo 10/19 worki in the data base fine but dose not update in the array list. either figuer how to update the array or re initilize
            Customer customer = new Customer(cusId, customerName, phone.getText(), addressId, address.getText(), address2.getText(),
                    postalCode.getText(), cityId, countryId, cusCity.toString(), createDate, createdBy, newDate, lu.toString());
            //find the customer object that in the customerList and
            CustomerController.customers.clear();
            CustomerController.customers = CustomerController.getCustomerData();
            
            this.toDefault();
            Stage stage = (Stage) anchor.getScene().getWindow();
            stage.close();
        }
}
@FXML
    AnchorPane anchor;
    @FXML
        public void goBack() throws SQLException{
        this.toDefault();
     Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }
    public Customer setUpDateObject(Customer c){
        //customerToUpdate = c;
        this.setValues(c);
        return c;   
    }
    
     
    
    private void toDefault(){
        customer.clear();
        address.clear();
        address2.clear();
        phone.clear();
        postalCode.clear();
        country.getEditor().clear();
        city.getEditor().clear();

      
    }     
}
