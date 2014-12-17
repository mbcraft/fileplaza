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

package it.mbcraft.fileplaza.ui.common.components;

import it.mbcraft.fileplaza.ui.common.helpers.IconReference;
import javafx.scene.text.Font;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IViewableElement {

    /**
     * Removes the main icon, clearing the slot.
     */
    void clearMainIcon();

    /**
     * Removes all the status icons.
     */
    void clearStatusIcons();

    /**
     * Returns the current element size
     * 
     * @return The item size.
     */
    int getItemSize();

    /**
     * Returns the current Font used for the element label.
     * 
     * @return The font of the label.
     */
    Font getLabelFont();

    /**
     * Gets the value of the label text.
     * 
     * @return The label text as a String.
    */
    String getLabelText();

    /**
     * Adds an icon to the element status icons.
     * 
     * @param ref The reference to the icon to add.
     */
    void pushStatusIcon(IconReference ref);

    /**
     * Sets the size of the item.
     * 
     * @param size The number of pixels of the item.
     */
    void setItemSize(int size);

    /**
     * Sets the font for the element label.
     * 
     * @param f The font for the label. 
     */
    void setLabelFont(Font f);

    /**
     * Sets the text of the label.
     * 
     * @param text The text of the label.
     */
    void setLabelText(String text);

    /**
     * Sets the main icon for this element.
     * 
     * @param ref The reference to the icon to use.
     */
    void setMainIcon(IconReference ref);
    
}
