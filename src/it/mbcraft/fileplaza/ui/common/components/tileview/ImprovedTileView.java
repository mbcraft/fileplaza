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

package it.mbcraft.fileplaza.ui.common.components.tileview;

import com.guigarage.fx.grid.GridView;
import it.mbcraft.fileplaza.ui.common.components.IRefreshable;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T>
 */
public class ImprovedTileView<T> extends GridView<T> implements IRefreshable<T> {  
    
    /**
     * Refreshes all the items in the tile view
     */
    @Override
    public void refreshAllItems() {
        requestLayout();    
    }
    
    /**
     * Refreshes the item at position i inside the tile view.
     * 
     * @param index The index of the item to refresh
     */
    @Override
    public void refreshIndex(int index) {
        requestLayout();
    }
    
    /**
     * Refreshes an arbitrary item on the list
     * 
     * @param item The item to refresh
     */
    @Override
    public void refreshItem(T item) {
        requestLayout();
    }
    
    /**
     * Refreshes the current selected item
     */
    @Override
    public void refreshSelectedItem() {
        requestLayout();
    }
    
}