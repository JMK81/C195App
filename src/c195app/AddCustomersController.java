/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import static c195app.LoginController.lu;
import static c195app.dbConnection.citys;
import static c195app.dbConnection.countrys;
import static c195app.dbConnection.getCitys;
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

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class AddCustomersController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    MasterController myController;

    @Override
    public void setScreenParent(MasterController screenParent) {
        myController = screenParent;
    }
    Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        new SelectCustomer(city, citys);
        new SelectCustomer(country, countrys);
        //citys.setAll(getCitys());

        try {
            connection = dbConnection.getDataSource().getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(AddCustomersController.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }

    //bind fxml
    @FXML
    Label message;
    @FXML
    TextField customer, address, address2, phone, postalCode;
    @FXML
    ComboBox city, country;
    //bind methods
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

    Calendar calendar = Calendar.getInstance();
    java.sql.Date newDate = new java.sql.Date(calendar.getTime().getTime());
    Date d = Date.from(Instant.now());

    //timeStamp
    @FXML
    public void add() throws SQLException {
        int cityId = City.getMaxCityId();
        int countryId = Country.getMaxCountryId();
        int cusId = Customer.getMaxCustomerId();
        int addressId = dbConnection.getMaxAddressId();
        message.setText("");
        //in the combo box contains a object get object esls create an new country
        //if to test if the fields are filled in
        if (customer.getText().equals("") || address.getText().equals("")
                || address2.getText().equals("") || phone.getText().equals("") || postalCode.getText().equals("")
                || city.getEditor().getText().equals(city.getPlaceholder())
                || country.getEditor().getText().equals(city.getPlaceholder())) {
            message.setText("Please check the input values");

        } else {
            Country cusCountry;
            //if countryes contains no to make a cusCountry in in 
            if ((country.getSelectionModel().getSelectedIndex()) >= 0) {
                cusCountry = countrys.get(country.getSelectionModel().getSelectedIndex());
            } else {//TODO this used to work now sure what is goind on 
                cusCountry = new Country(countryId, country.getEditor().getText().toString(), d, lu.getName(), d, lu.getName());

                try {
                    countrys.add(cusCountry);

                    PreparedStatement statement = connection.prepareStatement(
                            "Insert into country(countryId, country, createDate, createdBy, lastUpdateBy)"
                            + "Values (?, ?, ?, ?, ?)");
                    statement.setInt(1, cusCountry.getCountryId());
                    statement.setString(2, cusCountry.getName());
                    statement.setDate(3, newDate);
                    statement.setString(4, lu.getName());
                    statement.setString(5, lu.getName());
                    statement.execute();
                    System.out.println("new country after insert" + cusCountry);

                    //check tabel
                } catch (SQLException ex) {
                    message.setText("There Is An Error Please Check your input and try again");
                    System.out.print("error createing country");
                    ex.printStackTrace();
                } catch (Exception es) {
                    System.out.println("what in the sam hill is going on here");
                    es.printStackTrace();
                }
                //fixme addcustomer controler the local user that signed on to the app in not being called properly
            }
            //in the combo box contains a object get object esls create an new country
            City cusCity;
            if (city.getSelectionModel().getSelectedIndex() >= 0) {
                cusCity = citys.get(city.getSelectionModel().getSelectedIndex());
            } else {//TODO this used to work now sure what is goind on 
                cusCity = new City(cityId, cusCountry.getCountryId(), city.getEditor().getText(), d, lu.getName(), d, lu.getName());
                citys.add(cusCity);
                System.out.println("customer city " + cusCity.getName() + " " + cusCity.getCityId());
                try {
                    PreparedStatement statement = connection.prepareStatement(
                            "Insert into city(cityId, city, countryId, createDate, createdBy, lastUpdateBy)"
                            + "Values(?, ?, ?, ?, ?, ?)");
                    statement.setInt(1, cusCity.getCityId());
                    statement.setString(2, cusCity.getName());
                    statement.setInt(3, cusCountry.getCountryId());
                    statement.setDate(4, newDate);
                    statement.setString(5, lu.getName());
                    statement.setString(6, lu.getName());

                    statement.executeUpdate();

                    //check table
                } catch (SQLException ex) {
                    message.setText("There Is An Error Please Check your input and try again");
                    ex.printStackTrace();
                }
            }

            String cusName = customer.getText();
            String cusAdd = address.getText();
            String cusAdd2 = address2.getText();
            String cusPhone = phone.getText();
            String cusPostal = postalCode.getText();

            try {   //city id is max  and country id dont work
//                Customer cus = new Customer(cusId, cusName, cusPhone, addressId, cusAdd, cusAdd2, cusPostal, cusCity.getCityId(),
//                        cusCountry.getCountryId(), cusCity.getName(), newDate, lu.getName(), newDate, lu.getName());

                PreparedStatement statement = connection.prepareStatement(
                        "Insert into customer(customerId, customerName, addressId, active, createDate, createdBy,  lastUpdateBy)"
                        + "Values (?, ?, ?, ?, ?, ?, ?)");
                PreparedStatement statement2 = connection.prepareStatement(
                        "Insert into address(addressId, address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)"
                        + "Values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
                statement.setInt(1, cusId);
                statement.setString(2, cusName);
                statement.setInt(3, addressId);
                statement.setString(4, "1");
                statement.setDate(5, newDate);
                statement.setString(6, lu.getName());
                statement.setString(7, lu.getName());

                statement2.setInt(1, addressId);
                statement2.setString(2, cusAdd);
                statement2.setString(3, cusAdd2);
                statement2.setInt(4, cusCity.getCityId());
                statement2.setString(5, cusPostal);
                statement2.setString(6, cusPhone);
                statement2.setDate(7, newDate);
                statement2.setString(8, lu.getName());
                statement2.setString(9, lu.getName());
                statement.executeUpdate();
                statement2.executeUpdate();

                CustomerController.customers.clear();
                CustomerController.customers = CustomerController.getCustomerData();//todo not sure if its is 
            } catch (SQLException ex) {
                message.setText("There Is An Error Conectin to the database Please Check your input and try again");
                ex.printStackTrace();
                System.out.println("add customere");

                ex.printStackTrace();
            } catch (Exception e) {
                message.setText("There is an Error with you data please try again");
                e.printStackTrace();

            }
            //set all fields defult
            if (message.getText().length() <= 0) {
                this.toDefault();
                myController.setScreen(SwitchScreens.home);
                //connection.close();
            }
        }
    }

    @FXML
    public void goBack() throws SQLException {
        this.toDefault();
        myController.setScreen(SwitchScreens.home);

    }

    private void toDefault() {
        customer.clear();
        address.clear();
        address2.clear();
        phone.clear();
        postalCode.clear();
        country.getEditor().clear();
        city.getEditor().clear();

    }

}
