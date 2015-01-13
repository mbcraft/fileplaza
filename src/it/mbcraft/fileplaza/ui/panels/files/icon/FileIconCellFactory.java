/*
 *  Developed by MBCRAFT. Copyright © 2014-2015. All rights reserved.
 *  This file of source code is property of MBCRAFT (http://www.mbcraft.it). 
 *  Do not sell, do not remove this license note even if you edit this file.
 *  Do not use this source code to develop your own file manager application.
 *  You can reuse part or full files for your own project (eg javafx ui classes)
 *  but keep copyright in files, and please link http://www.mbcraft.it on your
 *  project website.
 *
 *  Thanks
 *
 *  - Marco Bagnaresi
 */

package it.mbcraft.fileplaza.ui.panels.files.icon;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.sun.javafx.WeakReferenceQueue;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import java.io.File;
import java.util.Iterator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileIconCellFactory implements Callback<GridView<File>,GridCell<File>> {

    private final EventHandler<MouseEvent> mouseHandler;
    private int currentItemSize;
    private int zoomLevel = 1;
    
    private final WeakReferenceQueue<GridCell<File>> cells = new WeakReferenceQueue<>();
    
    
    public FileIconCellFactory(IFileItemActionListener listener) {
        mouseHandler = new FileIconCellMouseListener(listener);
        currentItemSize = ZoomHelper.getSizeFromZoomLevel(zoomLevel);
    }
    
    @Override
    public GridCell<File> call(final GridView<File> gv) {
        final FileIconCell cell = new FileIconCell(currentItemSize);
        cell.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseHandler);        
        cells.add(cell);
        return cell;
    }

    public void setZoomLevel(int level) {
        ZoomHelper.checkZoomLevel(level);
        zoomLevel = level;
        currentItemSize = ZoomHelper.getSizeFromZoomLevel(level);
        
        Iterator it = cells.iterator();
        while (it.hasNext()) {
            FileIconCell obj = (FileIconCell) it.next();
            obj.setItemSize(currentItemSize);
        }  
        
    }
    
    public int getZoomLevel() {
        return zoomLevel;
    }

}