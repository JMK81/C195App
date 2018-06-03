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
public class City implements Selectable{
    int cityId;
    int countryId;
    SimpleStringProperty city;
    Date createDate;
    String createdBy;
    Date lastUpdate;
    String lastUpdateBy;

    public City(int cityId,  int countryId, String city, Date createDate, String createdBy, Date lastUpdate, String lastUpdateBy) {
        this.cityId = cityId;
        this.countryId = countryId;
        this.city = new SimpleStringProperty(city);
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }
static int getMaxCityId(){
        int id = 1;
        try{
        Connection connection = dbConnection.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "Select MAX(cityId) as max From city"
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

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCity() {
        return city.get();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    @Override
    public String getName() {
        return this.city.get();
    }
    @Override
    public String toString(){
        return this.getName();
    }
}
