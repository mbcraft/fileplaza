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

package it.mbcraft.fileplaza.ui.common.components.listview;

import it.mbcraft.fileplaza.ui.common.components.IRefreshable;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

/**
 * Enables view of elements as a "list". Allows change of items.
 * If used directly does not allow zoom of the elements.
 * 
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 * @param <T> The type contained inside this list
 */
public class ImprovedListView<T> extends ListView<T> implements IRefreshable<T> {
    
    /**
     * Refreshes all the items in the list view
     */
    @Override
    public void refreshAllItems() {
        ObservableList<T> myList = getItems();
        T[] items = (T[]) getItems().toArray();
        myList.clear();
        myList.addAll(items);
    }
    
    /**
     * Refreshes the item at position i inside the list view.
     * 
     * @param index The index of the item to refresh
     */
    @Override
    public void refreshIndex(int index) {
        T item = getItems().get(index);
        getItems().remove(index);
        getItems().add(index, item);
    }
    
    /**
     * Refreshes an arbitrary item on the list
     * 
     * @param item The item to refresh
     */
    @Override
    public void refreshItem(T item) {
        int index = getItems().indexOf(item);
        getItems().remove(item);
        getItems().add(index, item);
    }
    
    /**
     * Refreshes the current selected item
     */
    @Override
    public void refreshSelectedItem() {
        refreshIndex(getSelectionModel().getSelectedIndex());
    }
}
