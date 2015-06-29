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
package it.mbcraft.fileplaza.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.IndexedCell;
import javafx.scene.input.MouseEvent;

/**
 * This class contains useful methods for dealing with JavaFX environment.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class NodeUtils {
    
    /**
     * Checks if this Node contains the MouseEvent.
     *
     * 
     * @param n The Node used for check
     * @param ev The MouseEvent to check
     * @return true if the Node contains the MouseEvent, false otherwise.
     */
    public static boolean containsMouseEvent(Node n, MouseEvent ev) {
        return n.contains(n.sceneToLocal(ev.getSceneX(), ev.getSceneY()));
    }

    /**
     * Adds styles to a JavaFX Node in order to show an hover and not hover style.
     *
     * @param n The Node to setup
     * @param notHoverStyle The JavaFX css string for "not hover" style.
     * @param hoverStyle The JavaFX string for "hover" style.
     * Suggestions from www.stackoverflow.com :
     * http://stackoverflow.com/questions/13074459/javafx-2-and-css-pseudo-classes-setting-hover-attributes-in-setstyle-method
     * http://stackoverflow.com/users/1155209/jewelsea
     */
    public static void setupNodeHover(Node n, String notHoverStyle, String hoverStyle) {
        n.styleProperty().bind(Bindings.when(n.hoverProperty()).then(
                new SimpleStringProperty(hoverStyle)).otherwise(
                        new SimpleStringProperty(notHoverStyle)
                ));
    }
    
    /**
     * Setups a JavaFX IndexedCell adding a "not selected" style and a "selected" style.
     * @param cell The IndexedCell to add style to.
     * @param notSelectedStyle The JavaFX css string for "not selected" style.
     * @param selectedStyle The JavaFX css string for "selected" style.
     */
    public static void setupNodeSelection(IndexedCell cell, String notSelectedStyle, String selectedStyle) {
        
        cell.styleProperty().bind(Bindings.when(cell.selectedProperty()).then(new SimpleStringProperty(selectedStyle)).otherwise(new SimpleStringProperty(notSelectedStyle)));
    }
}
