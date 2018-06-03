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
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class CalenderController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    MasterController myController;
    /**
     *
     * @param screenParent
     */
    LocalDate date = LocalDate.now();

    //table columns to be have months add id to ]
    public static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    @FXML
    TableView<Appointment> tv;
    @FXML
    Label heading;
    @FXML
    private TableColumn<Appointment, Date> startCol;
    @FXML
    private TableColumn<Appointment, Date> endCol;
    @FXML
    private TableColumn<Appointment, Customer> customerCol;
    @FXML
    private TableColumn<Appointment, User> contactCol;
    @FXML
    private TableColumn<Appointment, String> locationCol;
    @FXML
    Button nextWeek, nextMonth, previousWeek, previousMonth;

    @Override
    public void setScreenParent(MasterController screenParent) {
        myController = screenParent;
    }

    @Override//localUser resourcebundle is that url used as file location??
    public void initialize(URL url, ResourceBundle rb) {

        startCol.setCellValueFactory(new PropertyValueFactory<Appointment, Date>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<Appointment, Date>("end"));
        customerCol.setCellValueFactory(new PropertyValueFactory<Appointment, Customer>("customer"));
        contactCol.setCellValueFactory(new PropertyValueFactory<Appointment, User>("contact"));
        locationCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        //adds data to the table view
        try {
            tv.setItems(getAppointmentData());
        } catch (SQLException e1) {
            System.out.println("sql date error getAppointmnents");
        }

    }

    public static ObservableList<Appointment> getAppointmentData() throws SQLException {

        //there is one appoinment in the db should pull one file
        try (Connection connection = dbConnection.getDataSource().getConnection();) {
            //data to creat an observable list based where the year matches
            //create a view that contains all fields for calender display

            PreparedStatement statement = connection.prepareStatement("Select appointmentId, customer.customerId,"
                    + "customerName, title, description, location, contact, start, end, appointment.createDate,\n"
                    + "appointment.createdBy, appointment.lastUpdate, appointment.lastUpdateBy from appointment, customer\n"
                    + "Where appointment.customerId = customer.customerId");

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {

                DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yy:MM:dd HH:mm");
                int appointmentId = rs.getInt("appointmentId");
                String customerName = rs.getString("customerName");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                User contact = new User(rs.getString("contact"));
                Timestamp appStart = rs.getTimestamp("start");
                ZonedDateTime zdtStart = ZonedDateTime.ofInstant(appStart.toInstant(), ZoneId.of("UTC"));
                LocalDateTime start = LocalDateTime.ofInstant(zdtStart.toInstant(), ZoneId.systemDefault());
                dateTimeformatter.format(start);
                Timestamp appEnd = rs.getTimestamp("end");
                ZonedDateTime zdtEnd = ZonedDateTime.ofInstant(appEnd.toInstant(), ZoneId.of("UTC"));
                LocalDateTime end = LocalDateTime.ofInstant(zdtEnd.toInstant(), ZoneId.systemDefault());
                dateTimeformatter.format(end);
                Timestamp create = rs.getTimestamp("appointment.createDate");
                ZonedDateTime cd = ZonedDateTime.ofInstant(create.toInstant(), ZoneId.of("UTC"));
                LocalDateTime createDate = LocalDateTime.ofInstant(zdtEnd.toInstant(), ZoneId.systemDefault());
                dateTimeformatter.format(createDate);
                User user = new User(rs.getString("appointment.createdBy"));
//                Timestamp last = rs.getTimestamp("appointment.lastUpdate");
//                ZonedDateTime l = last.toLocalDateTime().atZone(getLocalUser().getLocation().toZoneId());
//                Date lastUpdate = Date.from(l.toInstant());

                //dateTimeformatter.format(lastUpdate);
                Appointment appointment = new Appointment(appointmentId, customerName, title, description,
                        location, contact.getName(), start, end, createDate, user.getName());
                appointments.add(appointment);

            }
        } catch (SQLException ex) {
            System.out.println("Sql error");
            ex.printStackTrace();

        } catch (Exception e) {
            System.out.println("Error try again");
            e.printStackTrace();
        }

        return appointments;

    }

    public static void addAppt(Appointment appt) {
        appointments.add(appt);
    }

    @FXML
    public void goBack(ActionEvent event) {
        myController.setScreen(SwitchScreens.home);
        try {
            appointments.clear();
            appointments = getAppointmentData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void selectCustomer(ActionEvent e) {

        myController.setScreen(SwitchScreens.home);
    }

    @FXML
    public void upDate(ActionEvent event) throws IOException {
        Appointment sc = (Appointment) tv.getSelectionModel().getSelectedItem();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateAppointment.fxml"));
        Parent root2 = (Parent) loader.load();
        UpdateAppointmentController controller = (UpdateAppointmentController) loader.getController();
        controller.setValues(sc);
        stage.setScene(new Scene(root2));
        stage.setTitle("Update Appointment");
        stage.setResizable(false);
        stage.showAndWait();
        stage.toFront();

    }

    // method change display of week and month maybe limit view
    @FXML
    public void getNextMonth() {
        date = date.plusMonths(1);
        System.out.println("getNextMonth() month " + date.getMonth());
        try {
            appointments.clear();
            System.out.println("appointments: " + appointments);
            appointments = getAppointmentData();
            System.out.println("appointments: " + appointments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayMonth();

    }

    @FXML
    public void getPriviousMonth() {
        date = date.minusMonths(1);
        System.out.println("getNextMonth() month " + date.getMonth());
        try {
            appointments.clear();
            System.out.println("appointments: " + appointments);
            appointments = getAppointmentData();
            System.out.println("appointments: " + appointments);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayMonth();

    }

//get next end of week
    @FXML
    public void displayMonth() {
        nextWeek.setVisible(false);
        previousWeek.setVisible(false);
        nextMonth.setVisible(true);
        previousMonth.setVisible(true);

        //filter stream dates 
        appointments.stream()
                .filter(appt -> appt.getStart().getMonth().compareTo(date.getMonth()) != 0)
                .collect(Collectors.toList())
                .forEach(appt -> appointments.remove(appt));
        heading.setText("Appointments in " + date.getMonth());
    }

    LocalDate monday;
    LocalDate friday;
//need to get start and end of a given week and then filter the list removing any items that are not in the list

    @FXML
    public void displayWeek() {
        monday = date.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        friday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        nextWeek.setVisible(true);
        previousWeek.setVisible(true);
        nextMonth.setVisible(false);
        previousMonth.setVisible(false);

        appointments.removeIf((appt) -> (appt.getStart().toLocalDate().isBefore(monday) || appt.getStart().toLocalDate().isAfter(friday)));
        
        System.out.println("appoinmtment length: "+appointments.size());
        appointments.forEach((appt) -> System.out.println("appointment: "+appt.getStart()));
        heading.setText("Appointments between " + monday + " and " + friday);
    }

    @FXML//todo make date iterate and filter
    public void getNextWeek() {
        date = date.plusDays(7);
        
        try {
            appointments.clear();
            appointments = getAppointmentData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayWeek();
    }

    public void getPreviousWeek() {
        date = date.minusDays(7);
        System.out.println("monday: " + monday + " friday: " + friday);
        try {
            appointments.clear();
            appointments = getAppointmentData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        displayWeek();
    }
}
