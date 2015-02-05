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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class NodeUtils {
    
    public static boolean containsMouseEvent(Node n, MouseEvent ev) {
        return n.contains(n.sceneToLocal(ev.getSceneX(), ev.getSceneY()));
    }

    /*
     Suggestions from www.stackoverflow.com :
     http://stackoverflow.com/questions/13074459/javafx-2-and-css-pseudo-classes-setting-hover-attributes-in-setstyle-method
     http://stackoverflow.com/users/1155209/jewelsea
     */
    public static void setupNodeHover(Node n, String notHoverStyle, String hoverStyle) {
        n.styleProperty().bind(Bindings.when(n.hoverProperty()).then(
                new SimpleStringProperty(hoverStyle)).otherwise(
                        new SimpleStringProperty(notHoverStyle)
                ));
    }
    
    public static void setupNodeSelection(IndexedCell cell, String notSelectedStyle, String selectedStyle) {
        
        cell.styleProperty().bind(Bindings.when(cell.selectedProperty()).then(new SimpleStringProperty(selectedStyle)).otherwise(new SimpleStringProperty(notSelectedStyle)));
    }
}
