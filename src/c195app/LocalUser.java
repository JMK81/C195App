/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 *
 * @author jonathankoerber
 */
public class LocalUser extends User {
    private TimeZone location;
    
    public LocalUser(String userName, TimeZone zid){
        super.setUserName(userName);
        this.setLocation(zid);
    }
    private void setLocation(TimeZone l){
        location = l;
    }
    public TimeZone getLocation(){
        return location;
    }
   
}