/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jonathankoerber
 */
public  class Appointment {

   
    int appointmentId;
    SimpleStringProperty customer, appointmentTitle, description, location, contact, createdBy;
    Period appointment;
    LocalDateTime start;
    LocalDateTime end;
    LocalDateTime createDate;
    Date lastUpdate;
    public Appointment(int appointmentId, String customer, String appointmentTitle, String description, 
            String location, String contact, LocalDateTime start, LocalDateTime end, 
            LocalDateTime createDate, String createdBy){
        this.appointmentId = (appointmentId);
        this.customer = new SimpleStringProperty(customer);
        this.appointmentTitle = new SimpleStringProperty(appointmentTitle);
        this.description = new SimpleStringProperty(description);
        this.location =new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(contact);
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = new SimpleStringProperty(createdBy);
        this.lastUpdate = lastUpdate;
    
    }
    static public int getMaxAppointmentId(){
        int id = 1;
        try{
        Connection connection = dbConnection.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "Select MAX(appointmentId) as max From appointment"
                );
               
                ResultSet rs = statement.executeQuery();
                if(rs.next()) id = rs.getInt("max");
              
                        }
        catch(SQLException e){
            e.printStackTrace();
           System.out.print("sql error get next appointment id"+id);
        }
        
        return id+1;
    }
    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
    
    public void setAppointmentId(int appId){
        appointmentId = appId;
    }
    public int getApointmentId(){
        return appointmentId;
    }
    public String getCustomer(){
        return customer.get();
    }
    
    public String getAppointmentTitle(){
        return appointmentTitle.get();
    }
    
    public String getDescriotion(){
        return description.get();
    }
   
    public String getLocation(){
        return location.get();
    }
   
    public String getContact(){
        return contact.get();
    }
    public void setCreateDate(LocalDateTime c){
        createDate = c;
    }
    public LocalDateTime getCreateDate(){
        return createDate;
    }
  
    public String getCreatedBy(){
        return createdBy.get();
    }
    public void setLastUpdate(Date d){
        lastUpdate = d;
    }
    public Date getLastUpdate(){
        return lastUpdate;
    }
}