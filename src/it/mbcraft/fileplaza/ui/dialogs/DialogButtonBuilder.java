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
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

/**
 * This class is used to build buttons for the dialogs.
 * 
 * @author Mark Heckler (mark.heckler@gmail.com, @MkHeck)
 * @param <B>
 */
public class DialogButtonBuilder<B extends DialogButtonBuilder<B>> extends ControlBuilder<B> implements Builder<DialogButton> {
    private final HashMap<String, Object> properties = new HashMap<>();

    /**
     * Creates and returns a DialogButton builder object upon which 
     * to set properties and eventually, create a DialogButton for use with
     * a Dialog.
     * 
     * @return An instance of this builder
     */
    public static DialogButtonBuilder create() {
        return new DialogButtonBuilder();
    }

    /**
     * Sets the type of this button.
     * 
     * @param TYPE DialogButton.Type designation.
     * 
     * @return The current builder
     * 
     * @see MonologFXButton.Type
     */
    public final DialogButtonBuilder type(final DialogButton.Type TYPE) {
        properties.put("type", TYPE);
        return this;
    }

    /**
     * Sets the label text for the button.
     * 
     * To assign a shortcut key, simply place an underscore character ("_")
     * in front of the desired shortcut character.
     * 
     * @param LABEL String consisting of the desired button text.
     * 
     * @return The current builder
     */    
    public final DialogButtonBuilder label(final String LABEL) {
        properties.put("label", LABEL);
        return this;
    }

    /**
     * Sets the graphic for use on the button, either alone or with text.
     * Graphic format must be .png, .jpg (others?) supported by ImageView.
     * 
     * @param ICON String containing the location and name of a graphic file 
     *      (.png, .jpg) for use as an icon on the button face.
     * 
     * @return The builder
     *
     * @see ImageView
     */
    public final DialogButtonBuilder icon(final String ICON) {
        properties.put("icon", ICON);
        return this;
    }

    /**
     * Designates this button as the "default" button - or not.
     * 
     * @param DEFAULTBUTTON Boolean.
     * 
     * @return The builder
     */
    public final DialogButtonBuilder defaultButton(final boolean DEFAULTBUTTON) {
        properties.put("defaultButton", DEFAULTBUTTON);
        return this;
    }

    /**
     * Designates this button as the "cancel" button - or not.
     * 
     * @param CANCELBUTTON Boolean.
     * 
     * @return The builder
     */    
    public final DialogButtonBuilder cancelButton(final boolean CANCELBUTTON) {
        properties.put("cancelButton", CANCELBUTTON);
        return this;
    }

    /**
     * This is where the button is created/assembled. Returns a DialogButton
     * object, ready to add to a Dialog.
     * 
     * @return DialogButton The builded button
     */
    @Override
    public DialogButton build() {
        final DialogButton CONTROL = new DialogButton();

        for (String key : properties.keySet()) {
            switch (key) {
                case "type":
                    CONTROL.setType((DialogButton.Type) properties.get(key));
                    break;
                case "label":
                    CONTROL.setLabel((String) properties.get(key));
                    break;
                case "icon":
                    CONTROL.setIcon((String) properties.get(key));
                    break;
                case "defaultButton":
                    CONTROL.setDefaultButton((boolean) properties.get(key));
                    break;
                case "cancelButton":
                    CONTROL.setCancelButton((boolean) properties.get(key));
                    break;
            }
        }

        return CONTROL;
    }
}
