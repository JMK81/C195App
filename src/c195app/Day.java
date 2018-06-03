/*
 * contains 
 */
package c195app;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author jonathankoerber
 */
  
public class Day{
    Date date;
    String day;
    ObservableList<Appointment> appointments;
    
    public Day(Date ldt){
        this.setDayOfWeek(ldt);
        this.setDate(ldt);
        appointments = FXCollections.observableArrayList();
    }
    
   private void setDayOfWeek(Date d){
       String day;
       Calendar c = Calendar.getInstance();
        c.setTime(d);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
       
       switch(dayOfWeek){
           case 2: day = "mon";
           break;
           case 3: day = "tues";
           break;
           case 4: day = "wed";
           break;
           case 5: day = "thurs";
           break;
           case 6: day = "fri";
           break;
        }
   }
   public String getDay(){
       return day;
   }
   private void setDate(Date d){
       date=d;
   }    
   public Date getDate(){
       return date;
   }
}


