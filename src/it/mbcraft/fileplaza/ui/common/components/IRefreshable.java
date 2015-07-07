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

/**
 * This is a common interface for viewers components that allows
 * refresh of :
 * - all items
 * - a specific index or item
 * - the currently selected item
 * 
 * @param <T> The type viewed by this widget
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IRefreshable<T> {

    /**
     * Refreshes all the items in the list view
     */
    public void refreshAllItems();

    /**
     * Refreshes the item at position i inside the listview.
     *
     * @param index The index of the item to refresh
     */
    public void refreshIndex(int index);

    /**
     * Refreshes an arbitrary item on the list
     *
     * @param item The item to refresh
     */
    public void refreshItem(T item);

    /**
     * Refreshes the current selected item
     */
    public void refreshSelectedItem();
    
}
