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

package it.mbcraft.fileplaza.ui.panels.files;

import java.io.File;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IFileItemActionListener {
    
    public enum SelectionPlace {
        NAME, ICON, STATUS_ICON, ITEM
    }
    /**
     * This method is called when a file is selected with a single click.
     * 
     * @param f The selected file 
     */
    public void simpleSelection(File f, MouseEvent ev, SelectionPlace p);
    /**
     * This method is called when a file is heavy selected.
     * An heavy selection is a double click on an item or a return keypress on
     * a selected item.
     * @param f The selected file
     */
    public void heavySelection(File f, MouseEvent ev, SelectionPlace p);
    
    /**
     * This method is called when alternate action is invoked for an item.
     * Alternate action is when user right-clicks on a file or a particular
     * key press (to define, eg CRTL+SPACE) occurs.
     * @param f The file for which a context meny is needed.
     * @param ev The MouseEvent containing mouse coordinates, if available.
     */
    public void contextMenu(File f, MouseEvent ev, SelectionPlace p);
}
