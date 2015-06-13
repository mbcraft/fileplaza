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

import it.mbcraft.fileplaza.ui.common.components.IRefreshable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CellSizeUpdater implements ChangeListener<Integer> {

    private final IntegerProperty cellZoomLevelProperty;
    private final List<WeakReference<Node>> cellsToUpdate;
    private final IRefreshable refreshable;
    
    public CellSizeUpdater(IntegerProperty cellZoomLevelProp,IRefreshable ref,List<WeakReference<Node>> cells) {
        cellZoomLevelProperty = cellZoomLevelProp;
        cellsToUpdate = cells;
        refreshable = ref;
    }

    @Override
    public synchronized void changed(ObservableValue<? extends Integer> val, Integer oldValue, Integer newValue) {
        
        cellZoomLevelProperty.set(newValue);
        
        List<WeakReference<Node>> toRemoveList = new ArrayList();
        
        for (WeakReference<Node> weakCellRef : cellsToUpdate) {
            Node cell = weakCellRef.get();
            if (cell==null) {
                toRemoveList.add(weakCellRef);
                System.out.println("Weak ref removed!");
            }
        }
        
        cellsToUpdate.removeAll(toRemoveList);
        
        refreshable.refreshAllItems();
        
    }
    
}
