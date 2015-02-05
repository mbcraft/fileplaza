/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.mbcraft.fileplaza.ui.dialogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;
import javafx.util.Callback;
import it.mbcraft.fileplaza.ui.dialogs.DialogInputField.Type;

/**
 * Helper builder class for DialogInputField.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 * @param <B>
 */
public class DialogInputFieldBuilder<B extends DialogInputFieldBuilder<B>> extends ControlBuilder<B> implements Builder<DialogInputField> {
    private final Map<String,Object> properties = new HashMap<>();

    protected DialogInputFieldBuilder() {
    }

    /**
     * Creates and returns a DialogInputField builder object upon which 
 to set properties and eventually, create a DialogInputField for use with
 a Dialog.
     * 
     * @return An instance of this builder
     */
    public static DialogInputFieldBuilder create() {
        return new DialogInputFieldBuilder();
    }
    
    /**
     * Sets the label for the current input
     * 
     * @param LABEL The label for the current input
     * @return The current builder
     */
    public final DialogInputFieldBuilder label(final String LABEL) {
        properties.put("label", LABEL);
        return this;
    }
    
    /**
     * Sets the type of this input
     * 
     * @param TYPE The input type
     * @return The current builder
     */
    
    public final DialogInputFieldBuilder type(final Type TYPE) {
        properties.put("type", TYPE);
        return this;
    }
    
    /**
     * Sets the available value list for this input control (combobox or list)
     * 
     * @param VALUES The available value list
     * @return The current builder
     */
    
    public final DialogInputFieldBuilder valueList(final List<Object> VALUES) {
        properties.put("valueList", VALUES);
        return this;
    }
    
    /**
     * Sets the list cell factory for this input control (combobox or list)
     * 
     * @param CALLBACK The list cell factory callback
     * 
     * @return The current builder
     */
    public final DialogInputFieldBuilder listCellFactory(final Callback CALLBACK) {
        properties.put("cellFactory", CALLBACK);
        return this;
    }
    
    /**
     * Build the final input object.
     * 
     * @return The input object builded.
     */
    @Override
    public DialogInputField build() {
        final DialogInputField INPUT = new DialogInputField();
        
        for (String key : properties.keySet()) {
            switch (key) {
                case "type":
                    INPUT.setType((DialogInputField.Type) properties.get(key));
                    break;
                case "label":
                    INPUT.setLabel((String) properties.get(key));
                    break;
                case "valueList":
                    INPUT.setValueList((List<Object>) properties.get(key));
                    break;
                case "cellFactory":
                    INPUT.setCellFactory((Callback) properties.get(key));
                    break;
                case "defaultValue":
                    INPUT.setDefaultValue(properties.get(key));
                    break;
            }
        }
        
        return INPUT;
    }

    public DialogInputFieldBuilder defaultValue(Object defaultValue) {
        properties.put("defaultValue", defaultValue);
        return this;
    }
}
