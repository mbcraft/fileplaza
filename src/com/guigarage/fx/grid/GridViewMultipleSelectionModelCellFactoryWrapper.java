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

package com.guigarage.fx.grid;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T>
 */
public class GridViewMultipleSelectionModelCellFactoryWrapper<T> implements Callback<GridView<T>, GridCell<T>> {
    
    private final Callback<GridView<T>, GridCell<T>> innerCellFactory;
    
    public GridViewMultipleSelectionModelCellFactoryWrapper(Callback<GridView<T>, GridCell<T>> factory) {
        innerCellFactory = factory; 
    }

    @Override
    public GridCell<T> call(final GridView<T> gv) {
        final GridCell<T> cell = innerCellFactory.call(gv);
        
        cell.selectedProperty().addListener(new ChangeListener<Boolean>(){

            @Override
            public void changed(ObservableValue<? extends Boolean> observed, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    gv.selectionModelProperty().get().select(cell.getIndex());
                } else {
                    gv.selectionModelProperty().get().clearSelection(cell.getIndex());
                }
            }
        });
        return cell;
    }
}