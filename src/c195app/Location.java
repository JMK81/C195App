/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.util.TimeZone;

/**
 *
 * @author jonathankoerber
 */
public class Location implements Selectable{
    TimeZone zone;
    String location;
    
    
    public Location(String zone,  String location) {
        this.zone = TimeZone.getTimeZone(zone);
        this.location = location;
        
        
    }
 
    @Override
    public String getName() {
        return location;
    }
    @Override 
    public String toString(){
        return location;
    }

    public TimeZone getTimeZone() {
        return zone;
    }

    public void setTimeZoze(TimeZone timeZoze) {
        this.zone = timeZoze;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    
}
