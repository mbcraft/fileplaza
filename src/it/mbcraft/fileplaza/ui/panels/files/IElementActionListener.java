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

package it.mbcraft.fileplaza.ui.panels.files;

import java.io.File;
import javafx.scene.input.MouseEvent;

/**
 * This interface is used for file widgets, for capturing relevant events.
 * Actually a 'simple selection', 'heavy selection' and 'context menu' events
 * are supported.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IElementActionListener {
    
    public enum SelectionPlace {
        NAME, ICON, STATUS_ICON, ITEM
    }
    /**
     * This method is called when a file is selected with a single click.
     * This is called 'simple selection'
     * 
     * @param f The selected file 
     */
    public void simpleSelection(File f, MouseEvent ev, SelectionPlace p);
    /**
     * This method is called when a file is 'heavy selected'.
     * An heavy selection is a double click on an item or a return keypress on
     * a selected item.
     * 
     * @param f The selected file
     */
    public void heavySelection(File f, MouseEvent ev, SelectionPlace p);
    
    /**
     * This method is called when alternate action is invoked for an item.
     * Alternate action is when user right-clicks on a file or a particular
     * key press (to define, eg CRTL+SPACE) occurs.
     * 
     * @param f The file for which a context meny is needed.
     * @param ev The MouseEvent containing mouse coordinates, if available.
     */
    public void contextMenu(File f, MouseEvent ev, SelectionPlace p);
}
