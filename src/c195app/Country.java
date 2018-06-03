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
import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jonathankoerber
 */
public class Country implements Selectable{
    int cuntryId;
    SimpleStringProperty country;
    Date creatDate;
    String createBy;
    Date lastUpdate;
    String lastUpdatBy;

    public Country(int cuntryId, String country, Date creatDate, 
            String createBy, Date lastUpdate, String lastUpdatBy) {
        this.cuntryId = cuntryId;
        this.country = new SimpleStringProperty(country);
        this.creatDate = creatDate;
        this.createBy = createBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatBy = lastUpdatBy;
    }
static int getMaxCountryId(){
        int id = 1;
        try{
        Connection connection = dbConnection.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "Select MAX(countryId) as max From country"
                );
                System.out.print(id);
                ResultSet rs = statement.executeQuery();
                if(rs.next()) id = rs.getInt("max");
                System.out.print(id);
                        }
        catch(SQLException e){
            e.printStackTrace();
           System.out.print("getmextid");
        }
        
        return id+1;
    }
  
    @Override
    public String getName() {
        return this.country.get();
    }
    @Override
    public String toString(){
        return this.getName();
    }

    public int getCountryId() {
        return cuntryId;
    }

    public void setCuntryId(int cuntryId) {
        this.cuntryId = cuntryId;
    }

    public SimpleStringProperty getCountry() {
        return country;
    }

    public void setCountry(SimpleStringProperty country) {
        this.country = country;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdatBy() {
        return lastUpdatBy;
    }

    public void setLastUpdatBy(String lastUpdatBy) {
        this.lastUpdatBy = lastUpdatBy;
    }
  }
    
    
    

