/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jonathankoerber
 */
public class Year {
    String year;
    HashMap<String, appointmentMonth> years;    
    public Year(Date d){
        this.setYear(d);
        years = new HashMap();
    }
    private void setYear(Date d){
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        year = ""+y;
    }
    public String getYear(){
        return year;
    }
    
    public void setYears(appointmentMonth m){
        String month = m.getMonth();
        years.put(month, m);
    }
    public HashMap getMonthInYears(){
        return years;
    }
}
