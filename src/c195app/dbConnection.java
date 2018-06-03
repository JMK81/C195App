/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.FileInputStream;
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
import javafx.fxml.Initializable;

/**
 *
 * @author jonathankoerber
 */
public class dbConnection {

    public dbConnection() {
        initilizeCountrys();
        initilizeCity();
        getLocationData();
        getTimes();
        try {
            getUserData();
        } catch (SQLException e) {
            System.out.println("error getting user data");
            e.printStackTrace();
        }
    }
    private static final String DB_USERNAME = "db.username";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_URL = "db.url";
    private static Properties properties = null;
    private static MysqlDataSource dataSource;

    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/database.properties"));
            dataSource = new MysqlDataSource();
            dataSource.setUrl(properties.getProperty(DB_URL));
            dataSource.setUser(properties.getProperty(DB_USERNAME));
            dataSource.setPassword(properties.getProperty(DB_PASSWORD));

        } catch (IOException e1) {

        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }
    public static final ObservableList<User> users = FXCollections.observableArrayList();

    public static ObservableList<User> getUserData() throws SQLException {

        //there is one appoinment in the db should pull one file
        try (Connection connection = dbConnection.getDataSource().getConnection();) {
            PreparedStatement statement = connection.prepareStatement("Select userName FROM user");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("userName");

                User u = new User(userName);
                users.add(u);

            }

        } catch (SQLException ex) {
            System.out.println("Sql error get user data");

        } catch (Exception e) {
            System.out.println("Error try again");
            e.printStackTrace();
        }

        return users;
    }

    public static int getMaxAddressId() {

        int id = 1;
        try {
            Connection connection = dbConnection.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "Select MAX(addressId) as max From address"
            );
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                id = rs.getInt("max");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id + 1;
    }

    public static final ObservableList<Location> locations = FXCollections.observableArrayList();

    public static void getLocationData() {
        Location phoenix = new Location("America/Denver", "Phoenix");
        Location ny = new Location("America/New_York", "New York");
        Location london = new Location("Europe/London", "London");
        locations.addAll(phoenix, ny, london);

    }
    public static final ObservableList<MeetingLength> times = FXCollections.observableArrayList();

    public void getTimes() {
        MeetingLength qurter = new MeetingLength(15);//convert form seconds ot minnets 
        MeetingLength half = new MeetingLength(30);
        MeetingLength threeQurters = new MeetingLength(45);
        times.addAll(qurter, half, threeQurters);

    }
    public static final ObservableList<City> citys = FXCollections.observableArrayList();

    private static void initilizeCity() {

        try (Connection connection = dbConnection.getDataSource().getConnection();) {
            PreparedStatement statement = connection.prepareStatement("Select *  FROM city");

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int cityId = rs.getInt("cityId");
                String city = rs.getString("city");
                int countryId = rs.getInt("countryId");
                Date createDate = rs.getDate("createDate");
                String createdBy = rs.getString("createdBy");
                Date lastUpdate = rs.getDate("lastUpdate");
                String lastUpdateBy = rs.getString("lastUpdateBy");

                City c = new City(cityId, countryId, city, createDate, createdBy, lastUpdate, lastUpdateBy);

                citys.add(c);

            }

        } catch (SQLException ex) {
            System.out.println("Sql error citys");
            ex.printStackTrace();

        } catch (Exception e) {
            System.out.println("Error try again");
            e.printStackTrace();
        }
    }

    public static ObservableList<City> getCitys() {
        return citys;
    }
    public static final ObservableList<Country> countrys = FXCollections.observableArrayList();

    private static void initilizeCountrys() {

        try (Connection connection = dbConnection.getDataSource().getConnection();) {
            PreparedStatement statement = connection.prepareStatement("Select countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy FROM country");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss aa");
                int countryId = rs.getInt("countryId");
                String country = rs.getString("country");
                Date createDate = rs.getDate("createDate");
                formatter.format(createDate);
                Date lastUpdate = rs.getDate("lastUpdate");
                formatter.format(createDate);
                String createdBy = rs.getString("createdBy");
                String lastUpdateBy = rs.getString("lastUpdateBy");
                Country c = new Country(countryId, country, createDate, createdBy, lastUpdate, lastUpdateBy);
                countrys.add(c);

            }

        } catch (SQLException ex) {
            System.out.println("Sql error counrty");
            ex.printStackTrace();

        } catch (Exception e) {
            System.out.println("Error try again");
            e.printStackTrace();
        }

    }

    public static ObservableList<Country> getCountrys() {

        return countrys;
    }

}
