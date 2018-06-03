               /*
 * when data is pulled form the db made into obj to get put into the appointments
*
 */
package c195app;

import static c195app.dbConnection.countrys;
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
public final class Customer implements Selectable{

    
    @Override
    public String getName(){
        return this.getCustomerName();
    }
    @Override
            public String toString(){
                return this.getCustomerName();
            }
    int customerId;
    SimpleStringProperty customerName, phone, address, address2, postalCode,  city;
    int addressId;
    int cityId;
    int countryid;
    Date createDate, lastUpdated;
    String lastUpdatedBy, createdBy;
    
    public Customer(int customerId, String customerName, String phone, int addressId, String address, 
            String address2, String postalCode, int cityId, int countryid, String city, Date createDate,
            String createdBy, Date lastUpdated, String lastUpdatedBy){
        this.setCustomerId(customerId);
        this.customerName = new SimpleStringProperty(customerName);
        this.phone = new SimpleStringProperty(phone);
        this.setAddressId(addressId);
        this.address = new SimpleStringProperty(address);
        this.address2 = new SimpleStringProperty(address2);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.setCityId(cityId);
        this.setCountryId(countryid);
        this.city = new SimpleStringProperty(city);
        this.setCreateDate(createDate);
        this.setCreatedBy(createdBy);
        this.setLastUpdated(lastUpdated);
        this.setLastUpdatedBy(lastUpdatedBy);
    }
    public static int getMaxCustomerId(){
        int id = 1;
        try{
        Connection connection = dbConnection.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "Select MAX(customerId) as max From customer"
                );
                
                ResultSet rs = statement.executeQuery();
                if(rs.next()) id = rs.getInt("max");
                
                        }
        catch(SQLException e){
            e.printStackTrace();
           
        }
        
        return id+1;
    }

    public int getCountryId() {
        return countryid;
    }
  
    public void setCountryId(int country) {
        this.countryid = country;
    }
    private void setCustomerId(int i){
        customerId = i;
    }
    public int getCustomerId(){
        return customerId;
    }
    public String getCustomerName(){
        return customerName.get();
    }
    
    public String getPhone(){
        return phone.get();
    }
    private void setAddressId(int i){
     
        addressId = i;
    }
    public int getAddressId(){
        return addressId;
    }
    public String getAddress(){
         return address.get();
    }
    public String getAddress2(){
         return address2.get();
    }
    
    public String getPostalCode(){
        return postalCode.get();
    }
    private void setCityId(int s){
        cityId = s;
    }
    public int getCityId(){
        return cityId;
    }
    
    public String getCity(){
        return city.get();
    }
    
    private void setCreateDate(Date d){
        createDate = d;
    }
    
    public Date getCreateDate(){
        return createDate;
    }
    private void setCreatedBy(String u){
        createdBy = u;
    }
    public String getCreatedBy(){
        return createdBy;
    }
    private void setLastUpdated(Date d){
        lastUpdated = d;
    }
    public Date getLastUpdated(){
        return lastUpdated;
    }
    private  void setLastUpdatedBy(String s){
        lastUpdatedBy = s;
    }
    public String getLastUpdatedBy(){
        return lastUpdatedBy;
    }
    //getter to supply to the table view
    
}
