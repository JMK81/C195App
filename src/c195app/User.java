/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

/**
 *
 * @author jonathankoerber
 */
public class User implements Selectable{
    
    String userName;
public User(String userName){
    this.setUserName(userName);   
}
public User(){}
public void setId(int i){
}

    @Override
    public String getName() {
        return userName;
    }
    @Override
    public String toString(){
        return userName;
    }

public void setUserName(String s){
    userName = s;
}
public String getUserName(){
    return userName;
}
}