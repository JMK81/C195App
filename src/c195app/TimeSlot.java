/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jonathankoerber
 */
public class TimeSlot implements Selectable{
   LocalDateTime start;
   LocalDateTime end;
   SimpleStringProperty period;
  public TimeSlot(LocalDateTime start, LocalDateTime end) {
          this.start =(start);
          this.end = (end);
          this.period=new SimpleStringProperty(getName());
  }
 
  
  @Override
  public String getName(){
      
      String period = (start.toString() +" - "+end.toString());
      return period;
  }
  @Override
  public String toString(){
      return this.getName();
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
   
 
         
    
}
