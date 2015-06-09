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

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import it.mbcraft.fileplaza.ui.common.components.IRefreshable;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.util.Callback;

/**
 *
 * This widget enables view of elements in a tiled fashion. If used directly
 * does not allow zoom. It allows change of viewed elements.
 * 
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T> The type contained inside this view 
 */
public class ImprovedTileView<T> extends ScrollPane implements IRefreshable<T> {  
    
    private final GridView<T> internalView;
    
    public ImprovedTileView() {
        
        internalView = new GridView();
        
        setPannable(false);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setFitToWidth(true);
        setContent(internalView);
        prefViewportHeightProperty().bind(internalView.heightProperty());
    }
    
    /**
     * Returns the items property of this widget.
     * 
     * @return The items property
     */
    public ObjectProperty<ObservableList<T>> itemsProperty() {
        return internalView.itemsProperty();
    }
    
    /**
     * Returns the selection model property of this widget.
     * 
     * @return The selection model property
     */
    public ObjectProperty<MultipleSelectionModel<T>> selectionModelProperty() {
        return internalView.selectionModelProperty();
    }
    
    /**
     * Returns the cell factory property of this widget.
     * 
     * @return The cell factory property
     */
    public ObjectProperty<Callback<GridView<T>,GridCell<T>>> cellFactoryProperty() {
        return internalView.cellFactoryProperty();
    }
    
    /**
     * Refreshes all the items in the tile view
     */
    @Override
    public void refreshAllItems() {
        internalView.requestLayout();    
    }
    
    /**
     * Refreshes the item at position i inside the tile view.
     * 
     * @param index The index of the item to refresh
     */
    @Override
    public void refreshIndex(int index) {
        internalView.requestLayout();
    }
    
    /**
     * Refreshes an arbitrary item on the list
     * 
     * @param item The item to refresh
     */
    @Override
    public void refreshItem(T item) {
        internalView.requestLayout();
    }
    
    /**
     * Refreshes the current selected item
     */
    @Override
    public void refreshSelectedItem() {
        internalView.requestLayout();
    }
    
}