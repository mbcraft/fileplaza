/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.ui.common.components;

import it.mbcraft.fileplaza.ui.common.IconReference;
import javafx.scene.text.Font;

/**
 * Common interface for elements that features :
 * - a main icon
 * - a main label
 * - a customizable font for the label
 * - a list of status icons
 * 
 * It's used for the list view and the icon view.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IViewableElement {

    /**
     * Removes the main icon, clearing the slot.
     */
    public void clearMainIcon();

    /**
     * Removes all the status icons.
     */
    public void clearStatusIcons();

    /**
     * Returns the current Font used for the element label.
     * 
     * @return The font of the label.
     */
    public Font getLabelFont();

    /**
     * Gets the value of the label text.
     * 
     * @return The label text as a String.
    */
    public String getLabelText();

    /**
     * Adds an icon to the element status icons.
     * 
     * @param ref The reference to the icon to add.
     */
    public void pushStatusIcon(IconReference ref);

    /**
     * Sets the font for the element label.
     * 
     * @param f The font for the label. 
     */
    public void setLabelFont(Font f);

    /**
     * Sets the text of the label.
     * 
     * @param text The text of the label.
     */
    public void setLabelText(String text);

    /**
     * Sets the main icon for this element.
     * 
     * @param ref The reference to the icon to use.
     */
    public void setMainIcon(IconReference ref);
    
}
