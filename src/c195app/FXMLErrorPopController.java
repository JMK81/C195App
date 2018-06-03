/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author jonathankoerber
 */
public class FXMLErrorPopController implements Initializable {
    
    @FXML
    private Label headding, message1, message2, message3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    public void setPopText(String title, String m1, String m2, String m3){
        headding.setText(title);
        message1.setText(m1);
        message2.setText(m2);
        message3.setText(m3);
    }
    
    public void toDefault(){
        headding.setText("");
        message1.setText("");
        message2.setText("");
        message3.setText("");
    } 
    @FXML
    AnchorPane anchor;

    @FXML
    public void goBack() {
        this.toDefault();
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.close();
    }
    
}
