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

package it.mbcraft.fileplaza.ui.panels.files.list;

import com.sun.javafx.WeakReferenceQueue;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import java.io.File;
import java.util.Iterator;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
class FileListCellFactory implements Callback<ListView<File>,ListCell<File>> {

    private final EventHandler<MouseEvent> mouseHandler;
    private int currentItemSize;
    private int zoomLevel = 0;
    
    private final WeakReferenceQueue<ListCell<File>> cells = new WeakReferenceQueue<>();
    
    public FileListCellFactory(IFileItemActionListener listener) {
        mouseHandler = new FileListCellMouseListener(listener);
        currentItemSize = ZoomHelper.getSizeFromZoomLevel(zoomLevel);
    }
    
    @Override
    public ListCell<File> call(ListView<File> list) {
        FileListCell cell = new FileListCell(currentItemSize);
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
            FileListCell obj = (FileListCell) it.next();
            obj.setItemSize(currentItemSize);
        }  
        
    }
    
    public int getZoomLevel() {
        return zoomLevel;
    }
        
}