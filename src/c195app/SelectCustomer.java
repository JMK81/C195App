/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c195app;

import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author jonathankoerber
 */
public class SelectCustomer implements EventHandler<KeyEvent> {

    private ComboBox comboBox;
    private StringBuilder sb;
    private final ObservableList data;
    private boolean moveCaretToPos = false;
    private int caretPos;
    private Selectable slected;
//need to get an array into the object!!
    public  SelectCustomer(final ComboBox comboBox, ObservableList list) {
        
        this.comboBox = comboBox;
        sb = new StringBuilder();
        data = list;
        
        this.comboBox.setItems(list);
        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });
        this.comboBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                comboBox.show();
                
            }
        });
           this.comboBox.setOnKeyReleased(SelectCustomer.this);
    }

    @Override
    public void handle(KeyEvent event) {

        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }
    
        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

         ObservableList list = FXCollections.observableArrayList();
         for(Object obj : data){
             Selectable e = (Selectable) obj;
             String string = e.toString();
            
                if(string.toLowerCase().startsWith(comboBox.getEditor().getText().toLowerCase())) {
                    list.add(obj);
              }
//        Selectable selected = (Selectable)comboBox.getSelectionModel().getSelectedItem();//were string cast error
        
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
    }
 }
    private static final Logger LOG = Logger.getLogger(SelectCustomer.class.getName());
    private void moveCaret(int textLength) {
        if(caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }
public void setList(FilteredList fl){
    comboBox.setItems(fl);
}

    
}
