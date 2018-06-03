/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import static c195app.CalenderController.appointments;
import static c195app.CustomerController.customers;
import static c195app.dbConnection.locations;
import static c195app.dbConnection.times;
import static c195app.dbConnection.users;
import static c195app.LoginController.getLocalUser;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author jonathankoerber
 */
public class AppointmentController implements Initializable, ControlledScreen {

    /**
     * Initializes the controller class.
     */
    MasterController myController;

    @Override
    public void setScreenParent(MasterController screenParent) {
        myController = screenParent;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        topic t5 = new topic("New Customer");
        topic t1 = new topic("New Project");
        topic t2 = new topic("New Customer Check in");
        topic t3 = new topic("Problems with Past Projects");
        topic t4 = new topic("Other");

        titles.setAll(t5, t1, t2, t3, t4);
        new SelectCustomer(selectCustomer, customers);
        new SelectCustomer(selectUser, users);
        new SelectCustomer(selectLocation, locations);
        new SelectCustomer(selectMeetingLength, times);
        new SelectCustomer(meetingTitle, titles);
        
        selectTimeSlot.setVisible(true);

    }
    //initalize eliments for fxml

    public static final ObservableList<topic> titles = FXCollections.observableArrayList();

    public static ObservableList<topic> getTopics() {
        return titles;
    }

    public class topic implements Selectable {

        String topic;

        public topic(String tapic) {
            this.topic = tapic;
        }

        @Override
        public String toString() {
            return topic;
        }

        @Override
        public String getName() {
            return topic;
        }
    }
    @FXML
    Label message, timeMessage, startTimeMessage;
    @FXML
    TextArea meetingDiscription;
    @FXML
    DatePicker date;
    @FXML
    ComboBox selectCustomer, meetingTitle, selectUser, selectLocation, selectMeetingLength, selectTimeSlot;
    @FXML
    Button selectTimeButton;

    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm aa");

    /**
     * pull data from textFields, TextArea, and DatePicker checks to Make sure
     * the data good no over lapping appointments and add an appointment object
     * to the data base
     *
     * @param e1 button press
     */
    //local variables
    MeetingLength ml;
    topic tp;
    Location l;
    User u;
    Customer c;
    TimeSlot t;
    LocalDate d;
    LocalTime localTimeStart;
    LocalDate local;
    TimeSlot timeSlot;

    @FXML
    public void add(ActionEvent e1) {
        //checkif combo box contains an object get object esls set to null
        //fist check if the date pickes is not null and before now():{)>
        //maybe do an if statement and retrun start and end!!
        timeSlot = ((selectTimeSlot.getSelectionModel().getSelectedIndex()) >= 0)
                ? timeSlots.get(selectTimeSlot.getSelectionModel().getSelectedIndex()) : null;
        if (this.isComplete() && timeSlot != null) {

            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                int id = Appointment.getMaxAppointmentId();
                int cusId = c.getCustomerId();
                String title = tp.getName();
                String description = meetingDiscription.getText();
                String location = l.getName();
                String url = " ";
                String contact = u.getUserName();
                ZonedDateTime zdtStart = ZonedDateTime.of(timeSlot.getStart().toLocalDate(), timeSlot.getStart().toLocalTime(), ZoneId.systemDefault());
                Timestamp start = Timestamp.from(zdtStart.withZoneSameInstant(ZoneId.of("UTC")).toInstant());
                ZonedDateTime zdtEnd = ZonedDateTime.of(timeSlot.getEnd().toLocalDate(), timeSlot.getEnd().toLocalTime(), ZoneId.systemDefault());
                Timestamp end = Timestamp.from(zdtEnd.toInstant());
                ZonedDateTime createDate = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("UTC"));
                Timestamp newDate = Timestamp.from(createDate.toInstant());
                LocalUser localUser = getLocalUser();//both create by and updateby
                Connection connection = dbConnection.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "Insert into appointment(appointmentId, customerId, title, description, location, contact,"
                        + "url, start, end, createDate, createdBy, lastUpdateBy)"
                        + "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
//                "Insert into country(countryId, country, createDate, createdBy, lastUpdateBy)"
                //+"Values (?, ?, ?, ?, ?)");
                statement.setInt(1, id);
                statement.setInt(2, cusId);
                statement.setString(3, title);
                statement.setString(4, description);
                statement.setString(5, location);
                statement.setString(6, contact);
                statement.setString(7, url);
                statement.setTimestamp(8, start);
                statement.setTimestamp(9, end);
                statement.setTimestamp(10, newDate);
                statement.setString(11, localUser.getName());
                statement.setString(12, localUser.getName());
                statement.execute();

                Appointment appointment = new Appointment(id, c.getName(), title, description,
                        location, contact, timeSlot.getStart(), timeSlot.getEnd(), createDate.toLocalDateTime(), contact);
                appointments.add(appointment);
            } catch (SQLException ex) {
                message.setText("There Is An Error Please Check your input and try again");
                ex.printStackTrace();
            } catch (Exception e) {
                System.out.print("%%%%%%%%");
                //todo pop up menu that lets user know that the appointment has not men added
                e.printStackTrace();

            }

        }

        if (message.getText().length() <= 0) {
            this.toDefault();
            myController.setScreen(SwitchScreens.home);
        }

    }

    private boolean isComplete() {
        boolean complete = true;
        message.setText("");
        boolean pd;
        try {
            local = date.getValue();
            LocalDate now = LocalDate.now();
            pd = local.isBefore(now);
            //test monday through friday
            if (local.getDayOfWeek().getValue() == 6 || local.getDayOfWeek().getValue() == 7) {
                date.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                message.setText("Please select a durring the week");
            }
        } catch (Exception e) {
            pd = false;
            date.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            e.printStackTrace();
        }//test if date is today of after
        ml = ((selectMeetingLength.getSelectionModel().getSelectedIndex()) >= 0)
                ? times.get(selectMeetingLength.getSelectionModel().getSelectedIndex()) : null;
        l = ((selectLocation.getSelectionModel().getSelectedIndex()) >= 0)
                ? locations.get(selectLocation.getSelectionModel().getSelectedIndex()) : null;
        u = ((selectUser.getSelectionModel().getSelectedIndex()) >= 0)
                ? users.get(selectUser.getSelectionModel().getSelectedIndex()) : null;
        c = ((selectCustomer.getSelectionModel().getSelectedIndex()) >= 0)
                ? customers.get(selectCustomer.getSelectionModel().getSelectedIndex()) : null;
        tp = ((meetingTitle.getSelectionModel().getSelectedIndex()) >= 0)
                ? titles.get(meetingTitle.getSelectionModel().getSelectedIndex()) : null;
        if (pd) {
            message.setText("Please select a date in the future");
            date.setStyle("-fx-border-color: red;");
            complete = false;
            date.setPromptText("Please pick a date");
        }

        if (c == null) {
            selectCustomer.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            complete = false;
        }

        if (u == null) {
            selectUser.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            complete = false;
        }
        if (ml == null) {
            selectMeetingLength.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            complete = false;
        }
        if (l == null) {
            selectLocation.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            complete = false;
        }
        if (tp == null) {
            meetingTitle.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            complete = false;
        }
        //test appointment avalible

        return complete;
    }

    private void toDefault() {

        selectTimeSlot.getEditor().clear();
        selectTimeSlot.setVisible(false);
        selectTimeButton.setVisible(true);
        timeSlots.clear();
        message.setText("");
        meetingDiscription.clear();
        meetingTitle.getEditor().clear();
        selectCustomer.getEditor().clear();
        selectUser.getEditor().clear();
        selectLocation.getEditor().clear();
        selectMeetingLength.getEditor().clear();
        timeMessage.setText("");
        startTimeMessage.setText("");
        date.getEditor().clear();
    }
    SelectCustomer filteredTime;

    @FXML
    public void selectTimeSlot() {

        if (this.isComplete()) {
            selectTimeSlot.setVisible(true);
            selectTimeButton.setVisible(false);
            getTimeSlots();
            isConflict(u.getName());
            filteredTime = new SelectCustomer(selectTimeSlot, timeSlots);
        } else {
            timeMessage.setText("Please fill out the all the fields in the form first");
        }
    }

//take user iput and add to a screen
    public static ObservableList<TimeSlot> setTimeSlots() {
        return timeSlots;
    }
    public static final ObservableList<TimeSlot> timeSlots = FXCollections.observableArrayList();

    public void getTimeSlots() {

        timeSlots.clear();
        Location l;
        MeetingLength ml = null;
        User u;
        LocalDate ld;
        l = locations.get(selectLocation.getSelectionModel().getSelectedIndex());
        ml = times.get(selectMeetingLength.getSelectionModel().getSelectedIndex());
        u = users.get(selectUser.getSelectionModel().getSelectedIndex());
        ld = date.getValue();

        LocalTime startHours = LocalTime.of(9, 0);
        ZonedDateTime dayStart = ZonedDateTime.of(ld, startHours, ZoneId.of(l.getTimeZone().toZoneId().getId()));//time zone of the locaiont
        ZonedDateTime start = ZonedDateTime.ofInstant(dayStart.toInstant(), ZoneId.systemDefault());
        Calendar cal = new GregorianCalendar();// creates calendar
        cal.set(ld.getYear(), ld.getMonthValue() - 1, ld.getDayOfMonth(), start.getHour(), start.getMinute(), 0);

        while (cal.getTime().toInstant().isBefore(start.plusHours(8).toInstant())) {//(cal.getTime().toInstant())) {//zondeddatetime
            LocalDateTime appointmentStart = LocalDateTime.ofInstant(cal.getTime().toInstant(), ZoneId.systemDefault());
            cal.add(Calendar.MINUTE, ml.getLength());
            LocalDateTime appointmentEnd = LocalDateTime.ofInstant(cal.getTime().toInstant(), ZoneId.systemDefault());
            TimeSlot ts = new TimeSlot(appointmentStart, appointmentEnd);
            timeSlots.add(ts);
            System.out.println(ts);

        }
        //isConflict(u.getName());

    }

    private ObservableList<TimeSlot> apptOnDate = FXCollections.observableArrayList();

    private void isConflict(String user) {
        try (Connection connection = dbConnection.getDataSource().getConnection();) {
            //data to creat an observable list based where the year matches
            //create a view that contains all fields for calender display
            //return array and search array
            Location l = locations.get(selectLocation.getSelectionModel().getSelectedIndex());
            LocalDate ld = date.getValue();
            LocalTime startHours = LocalTime.of(9, 0);
            ZonedDateTime dayStart = ZonedDateTime.of(ld, startHours, ZoneId.of(l.getTimeZone().toZoneId().getId()));//time zone of the locaiont
            ZonedDateTime start = ZonedDateTime.ofInstant(dayStart.toInstant(), ZoneId.of("UTC"));
            Timestamp timestamp = Timestamp.from(start.toInstant());
            Timestamp timestampEnd = Timestamp.from(start.plusHours(8).toInstant());
            PreparedStatement statement = connection.prepareStatement("Select start, end from appointment\n"
                    + "Where CONTACT = ? and start > ? and end < ?");
            statement.setString(1, user);
            statement.setTimestamp(2, timestamp);
            statement.setTimestamp(3, timestampEnd);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Timestamp tss = Timestamp.from(rs.getTimestamp("start").toInstant());
                ZonedDateTime zdtStart = ZonedDateTime.ofInstant(tss.toInstant(), ZoneId.of("UTC"));
                LocalDateTime tsStart = LocalDateTime.ofInstant(zdtStart.toInstant(), ZoneId.systemDefault());
                Timestamp tse = Timestamp.from(rs.getTimestamp("end").toInstant());
                ZonedDateTime zdtEnd = ZonedDateTime.ofInstant(tse.toInstant(), ZoneId.of("UTC"));
                LocalDateTime tsEnd = LocalDateTime.ofInstant(zdtEnd.toInstant(), ZoneId.systemDefault());
                TimeSlot ts = new TimeSlot(tsStart, tsEnd);
                System.out.println("there is a conflict with " + ts.getName());
                apptOnDate.add(ts);
            }

        } catch (SQLException ex) {
            System.out.println("Sql error conflicts");
        } catch (Exception e) {
            System.out.println("Error try again conflicts");
            e.printStackTrace();
        }
        System.out.println(apptOnDate + " apptOnDate list of coflites items");

        //timeSlots.stream().filter((timeSlot)->)
        // can you do this with stream ?? or set to a sorted list
//      for (TimeSlot pt : timeSlots) {
        for (TimeSlot appt : apptOnDate) {
            System.out.println("apptonDate appt: " + appt);
            timeSlots.removeIf((pt) -> (appt.getStart().isBefore(pt.getStart()) && appt.getEnd().isAfter(pt.getStart()))
                    || (pt.getStart().isBefore(appt.getEnd()) && pt.getEnd().isAfter(appt.getEnd()))
                    || (appt.getStart().isBefore(pt.getEnd()) && appt.getEnd().isAfter(pt.getEnd())));

        }
        timeSlots.forEach((slot) -> System.out.println("sotion supper crazy" + slot));
        // }
        System.out.println("start  check for conflicts");

        timeSlots.removeIf((pt) -> (pt.getStart().isBefore(LocalDateTime.now())));
        if (timeSlots.size() <= 0) {
            startTimeMessage.setText("There are no times avalible");
            selectTimeButton.setVisible(true);
            date.getEditor().clear();
        }
    }

    @FXML
    public void goBack(ActionEvent e2) {
        this.toDefault();
        myController.setScreen(SwitchScreens.home);
    }

}
