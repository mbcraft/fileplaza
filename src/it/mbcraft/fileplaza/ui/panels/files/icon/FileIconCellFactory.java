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

package it.mbcraft.fileplaza.ui.panels.files.icon;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import it.mbcraft.fileplaza.ui.common.components.IRefreshable;
import it.mbcraft.fileplaza.ui.panels.files.CellSizeUpdater;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * This class is a factory for FileIconCell s.
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileIconCellFactory implements Callback<GridView<File>,GridCell<File>> {

    private final IntegerProperty zoomLevelProperty;
    private final IntegerProperty cellZoomLevelProperty;
    
    private final EventHandler<MouseEvent> mouseHandler;
    private final List<WeakReference<Node>> currentCells = new ArrayList();
    
    public FileIconCellFactory(IntegerProperty zoomLevelProp,IRefreshable ref,IElementActionListener listener) {
        zoomLevelProperty = zoomLevelProp;
        cellZoomLevelProperty = new SimpleIntegerProperty(zoomLevelProperty.get());
        mouseHandler = new FileIconCellMouseListener(listener);   
        zoomLevelProperty.addListener((ChangeListener)new CellSizeUpdater(cellZoomLevelProperty,ref,currentCells));
        
    }
    
    @Override
    public GridCell<File> call(final GridView<File> gv) {
        final FileIconCell cell = new FileIconCell(cellZoomLevelProperty);
        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseHandler);        
        currentCells.add(new WeakReference(cell));
        return cell;
    }
}