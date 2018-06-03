/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import static c195app.CalenderController.appointments;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class LoginController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    MasterController myController;

    //initializes fxml ellements
    @FXML
    Label userName;
    @FXML
    Label greating;
    @FXML
    Label password;
    @FXML
    TextField userNameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Label message;
    private ResourceBundle bundle;
    @FXML
    Button loginBtn;
    Connection connection;
    static LocalUser lu;
    String headding;
    String message1;
    String message2;
    String message3;

    private final static Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private static FileHandler fh = null;
    //int localTimeOffSet;

    public static void logUserSingIn(LocalUser lu) {
        try {
            fh = new FileHandler("src/logFile.txt", false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        Date local = new Date();

        Logger l = Logger.getLogger("");
        fh.setFormatter(new SimpleFormatter());
        l.addHandler(fh);
        l.setLevel(Level.CONFIG);
        LOGGER.log(Level.SEVERE, "User " + lu.getUserName() + " logged in " + lu.getLocation().toString()
                + " at " + local + " " + local.toInstant().atOffset(ZoneOffset.UTC));

    }

    public static LocalUser getLocalUser() {

        return lu;
        //TODO make a pop up that makes local users appointments in the first 15 min
    }
    public static final ObservableList<Appointment> localAppointments = FXCollections.observableArrayList();

    //check user name and password against db return user object
    public void login(ActionEvent event) throws SQLException, IOException {
        //get a hashmap of username password pairs
        String user = userNameField.getText();
        StringBuffer userPassword = new StringBuffer(passwordField.getText());
        StringBuffer ps = new StringBuffer();
        //test if the userName and password is not blank
        //todo test isn
        if (user.equals("" )||userPassword.equals("")) {
            message.setText("User Name or Password has been left blank");
        } else {

            PreparedStatement statement = connection.prepareStatement("SELECT password FROM user where userName =?");
            statement.setString(1, user);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                ps.append(rs.getString("password"));

            }
        
        //make sure that the field is not empty and the username and pass word are corect
        //dose the stringBuffer get tuned to a bashowrd
        if ((userPassword.toString().equals(ps.toString()))) {
            myController.setScreen(SwitchScreens.home);
            TimeZone tz = TimeZone.getDefault();
            //todo just get the appointments for the next 20 min
            lu = new LocalUser(user, tz);
            logUserSingIn(lu);
            ArrayList<Appointment> list = new ArrayList();

            CalenderController.appointments.stream()
                    .filter((appt) -> appt.getContact().equals(lu.getName())
                    && (appt.getStart().isAfter(LocalDateTime.now()) && appt.getStart().isBefore(LocalDateTime.now())))
                    .collect(Collectors.toList())
                    .forEach(appt -> list.add(appt));

            headding = "Hello " + lu.getName() + " you have " + list.size() + "appointment";
            message1 = list.size()>=1 ?"you have an appointment with "+ list.get(0).getCustomer()+" at "+list.get(0).getStart():"";
            message2 = list.size()>=2 ?"you have an appointment with "+ list.get(1).getCustomer()+" at "+list.get(1).getStart():"";
            message3 = list.size()>=3 ?"you have an appointment with "+ list.get(2).getCustomer()+" at "+list.get(2).getStart():"";
            
            Stage stage = new Stage();
            //throws IOException

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ErrorPop.fxml"));
            Parent root2 = (Parent) loader.load();
            FXMLErrorPopController controller = (FXMLErrorPopController) loader.getController();
            controller.setPopText(headding, message1, message2, message2);
            stage.setScene(new Scene(root2));
            stage.setTitle("Update Appointment");
            stage.setResizable(false);
            stage.showAndWait();
            stage.toFront();
        } else {
            message.setText("User name or password do not match");
        }
        //emties that userPassord variable
        userPassword.append("");
        ps.append("");
        passwordField.clear();
        }
    }

    //use set text in errorpop
    public void setValues() {

    }

    @Override
    public void setScreenParent(MasterController screenParent) {
        myController = screenParent;
    }
//resourceBundle rb passed to the initialize() will change the labels on the login page!! and set the 
// localizaton and the timeOffSet

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            connection = dbConnection.getDataSource().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bundle = rb;
        greating.setText(bundle.getString("greating"));
        userName.setText(bundle.getString("userName"));
        password.setText(bundle.getString("password"));
        loginBtn.setText(bundle.getString("login"));

    }

}
