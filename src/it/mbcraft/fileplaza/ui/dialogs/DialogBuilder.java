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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;
import it.mbcraft.fileplaza.ui.dialogs.Dialog.Type;

/**
 * This class is a builder for the dialogs.
 * 
 * @author Mark Heckler (mark.heckler@gmail.com, @MkHeck)
 * @param <B>
 */
public class DialogBuilder<B extends DialogBuilder<B>> extends ControlBuilder<B> implements Builder<Dialog> {
    private final HashMap<String, Object> properties = new HashMap<>();
    private final List<DialogButton> buttons = new ArrayList<>();
    private final List<DialogInputField> inputs = new ArrayList<>();
    private final List<String> stylesheets = new ArrayList<>();
 
    protected DialogBuilder() {
    }

    /**
     * Creates and returns a Dialog dialog box builder object upon which 
 to set properties and eventually, create a Dialog dialog box.
     * 
     * @return The builder
     */
    public static DialogBuilder create() {
        return new DialogBuilder();
    }

    /**
     * Public method used to add an input to a Dialog dialog.
     *  
     * @param INPUT A DialogInputField object
     * 
     * @return The current builder
     */
    public final DialogBuilder input(final DialogInputField INPUT) {
        inputs.add(INPUT);
        return this;
    }
    
    /**
     * Public method used to add a button to a Dialog dialog.
     * 
     * @param BUTTON A DialogButton object.
     * 
     * @return The current builder
     * 
     * @see DialogButton
     */
    public final DialogBuilder button(final DialogButton BUTTON) {
        buttons.add(BUTTON);
        return this;
    }

    /**
     * Public method used to add a button to a Dialog dialog.
     * 
     * 
     * @param DISPLAYTIME Sets the display time of the constructed dialog
     * 
     * @return The current builder
     * 
     * @see DialogButton
     */
    public final DialogBuilder displayTime(final int DISPLAYTIME) {
        properties.put("displayTime", DISPLAYTIME);
        return this;
    }
    
    /**
     * Sets the type of Dialog dialog box to build/display.
     * 
     * @param TYPE One of the supported types of dialogs.
     * @return The current builder
     * @see Dialog.Type
     */
    public final DialogBuilder type(final Dialog.Type TYPE) {
        properties.put("type", TYPE);
        return this;
    }

    /**
     * Sets the button alignment for the Dialog dialog box. Default is CENTER.
     * 
     * @param ALIGNBUTTONS Valid values are LEFT, RIGHT, and CENTER.
     * 
     * @return The current builder
     * 
     * @see ButtonAlignment
     */
    public final DialogBuilder buttonAlignment(final Dialog.ButtonAlignment ALIGNBUTTONS) {
        properties.put("alignbuttons", ALIGNBUTTONS);
        return this;
    }

    /**
     * Sets the text displayed within the Dialog dialog box. Word wrap 
     * ensures that all text is displayed.
     * 
     * @param MESSAGE String variable containing the text to display.
     * 
     * @return The current builder
     */
    public final DialogBuilder message(final String MESSAGE) {
        properties.put("message", MESSAGE);
        return this;
    }

    /**
     * Sets the modality of the Dialog dialog box to build/display.
     * 
     * @param MODAL Boolean. A true value = APPLICATION_MODAL, false = NONE.
     * 
     * @return The current builder
     */
    public final DialogBuilder modal(final boolean MODAL) {
        properties.put("modal", MODAL);
        return this;
    }

    /**
     * Sets the text to be displayed in the title bar of the Dialog dialog.
     * 
     * @param TITLE_TEXT String containing the text to place in the title bar.
     * 
     * @return The current builder
     */
    public final DialogBuilder titleText(final String TITLE_TEXT) {
        properties.put("titleText", TITLE_TEXT);
        return this;
    }

    /**
     * Sets x coordinate of the Dialog dialog (if centering is not desired).
     * 
     * @param X_COORD Double representing the x coordinate to use for display.
     * 
     * @return The current builder
     */
    public final DialogBuilder X(final double X_COORD) {
        properties.put("xCoord", X_COORD);
        return this;
    }

    /**
     * Sets y coordinate of the Dialog dialog (if centering is not desired).
     * 
     * @param Y_COORD Double representing the y coordinate to use for display.
     * 
     * @return The current builder
     */
    public final DialogBuilder Y(final double Y_COORD) {
        properties.put("yCoord", Y_COORD);
        return this;
    }

    /**
     * Allows developer to add stylesheet(s) for Dialog dialog, supplementing 
 or overriding existing styling.
     * 
     * @param STYLESHEET String variable containing the path/name of the 
     * stylesheet to apply to the dialog's scene and contained controls.
     * 
     * @return The current builder
     */
    public final DialogBuilder stylesheet(final String STYLESHEET) {
        stylesheets.add(STYLESHEET);
        return this;
    }
    
    public final DialogBuilder inputsHgap(final double HGAP) {
        properties.put("inputsHgap", HGAP);
        return this;
    }
    
    public final DialogBuilder inputsVgap(final double VGAP) {
        properties.put("inputsVgap", VGAP);
        return this;
    }
    
    

    /**
     * This is where the magic happens...or at least where it all comes 
     * together.  :-) Returns a Dialog dialog, ready to display with
 showDialog().
     * 
     * @return Dialog The builded dialog.
     */
    @Override
    public Dialog build() {
        final Dialog CONTROL = new Dialog();

        for (String key : properties.keySet()) {
            switch (key) {
                case "type":
                    CONTROL.setType((Type)properties.get(key));
                    break;
                case "alignbuttons":
                    CONTROL.setButtonAlignment((Dialog.ButtonAlignment) properties.get(key));
                    break;
                case "displayTime":
                    CONTROL.setDisplayTime((int) properties.get(key));
                    break;
                case "message":
                    CONTROL.setMessage((String) properties.get(key));
                    break;
                case "modal":
                    CONTROL.setModal((boolean) properties.get(key));
                    break;
                case "titleText":
                    CONTROL.setTitleText((String) properties.get(key));
                    break;
                case "xCoord":
                    CONTROL.setX((double) properties.get(key));
                    break;
                case "yCoord":
                    CONTROL.setY((double) properties.get(key));
                    break;
                case "inputsHgap":
                    CONTROL.setInputsHgap((double) properties.get(key));
                    break;
                case "inputsVgap":
                    CONTROL.setInputsVgap((double) properties.get(key));
                    break;
            }
        }

        
        for ( DialogInputField ii : inputs ) {
            CONTROL.addInput(ii);
        }
        
        for ( DialogButton mb : buttons ) {
            CONTROL.addButton(mb);
        }
        
        for ( String ss : stylesheets ) {
            CONTROL.addStylesheet(ss);
        }
        
        return CONTROL;
    }
}
