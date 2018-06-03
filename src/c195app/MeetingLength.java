/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author jonathankoerber
 */
public class MeetingLength implements Selectable {
   
    
    int l;
    SimpleStringProperty ssp;
    MeetingLength(int l){
       this.setLength(l);
       this.ssp = new SimpleStringProperty(l +" Minnets");
    }
    @Override
    public String getName(){
        return ssp.get();
    }
    @Override
    public String toString(){
        return this.getName();
    }
    public void setLength(int l){
        this.l = l;
    }
    public int getLength(){
        return l;
    }
}
