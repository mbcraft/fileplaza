/*
 *  Developed by MBCRAFT. Copyright © 2014-2015. All rights reserved.
 *  This file of source code is property of MBCRAFT (http://www.mbcraft.it). 
 *  Do not sell, do not remove this license note even if you edit this file.
 *  Do not use this source code to develop your own file manager application.
 *  You can reuse part or full files for your own project (eg javafx ui classes)
 *  but keep copyright in files, and please link http://www.mbcraft.it on your
 *  project website.
 *
 *  Thanks
 *
 *  - Marco Bagnaresi
 */

package it.mbcraft.fileplaza.ui.dialogs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javafx.util.Callback;

/**
 * This class rapresents an input for a Dialog.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DialogInputField {

    public enum Type { TEXT, STRING, INTEGER, FLOAT, COMBOBOX, LIST, CHECKBOX };
    
    private static final List<String> defLabels = Arrays.asList(
    "Please enter a description :",
    "Please enter an input value :",
    "Please enter an integer :",
    "Please enter a double :",
    "Please select a value :",
    "Please select zero or more values :",
    "Please choose if you want to enable this option :");
    
    private final HashMap<Type, String> defaultLabels = new HashMap<>();
    
    private static final Type DEFAULT_TYPE = Type.STRING;
    
    private Type myType = DEFAULT_TYPE;
    private String label = null;
    private List<Object> allowedValueList = null;
    private Callback listCellFactory = null;
    private Object defaultValue = null;
    
    public DialogInputField() {      
        // Refactor.
        int i = 0;
        for (Type t: Type.values()) {
            defaultLabels.put(t, defLabels.get(i));
            i++;
        }
    }
    
    public void setLabel(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
    
    public void setType(Type t) {
        myType = t;
    }
    
    public Type getType() {
        return myType;
    }
    
    public void setValueList(List<Object> values) {
        allowedValueList = values;
    }
    
    public List<Object> getValueList() {
        return allowedValueList;
    }
    
    public void setCellFactory(Callback callback) {
        listCellFactory = callback;
    }
    
    public Callback getCellFactory() {
        return listCellFactory;
    }
    
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public Object getDefaultValue() {
        return defaultValue;
    }
    
    public boolean isInputWithReadableValue() {
        return !(myType==Type.COMBOBOX || myType==Type.LIST);
    }

    public boolean isInputWithSelectionModel() {
        return (myType==Type.COMBOBOX || myType==Type.LIST);
    }
    
    
}
