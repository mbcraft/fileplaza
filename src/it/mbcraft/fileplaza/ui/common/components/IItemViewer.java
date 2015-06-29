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

package it.mbcraft.fileplaza.ui.common.components;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 * This is a common interface for widgets that enable to view a list
 * of items and select zero or more items.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 * @param <T> The type of the viewed and selectable items
 */
public interface IItemViewer<T> {
    
    /**
     * Returns the itemsProperty, as an ObjectProperty
     * @return The property containing an ObservableList of <T> elements.
     */
    public ObjectProperty<ObservableList<T>> itemsProperty();
    
    /**
     * Returns the selectionModelProperty, as an ObjectProperty
     * 
     * @return The property containing a MultipleSelectionModel of <T> elements.
     */
    public ObjectProperty<MultipleSelectionModel<T>> selectionModelProperty();
}
