/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jonathankoerber
 */
public class appointmentMonth {
    ObservableList<Day> months;
    String month;
    String year;
 
    public appointmentMonth(Date d){
        this.setMonth(d);
        months = FXCollections.observableArrayList();
    }
     
    private void setMonth(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int m = c.get(Calendar.MONTH);
        int y = c.get(Calendar.YEAR);
        year = ""+y;
        switch(m){
                case 0: month = "jan";
                break;
                case 1: month = "feb";
                break;
                case 2: month = "mar";
                break;
                case 3: month = "april";
                break;
                case 4: month = "may";
                break;
                case 5: month = "june";
                break;
                case 6: month = "july";
                break; 
                case 7: month = "aug";
                break;
                case 8: month = "sep";
                break;
                case 9: month = "oct";
                break;
                case 10: month = "nov";
                break;
                case 11: month = "dec";
                break;
        }
    }
        public String getMonth(){
            return month;
        }
  
    
}
