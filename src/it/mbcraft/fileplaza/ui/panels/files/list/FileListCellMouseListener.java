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

import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
class FileListCellMouseListener implements EventHandler<MouseEvent> {

    private final IFileItemActionListener myListener;
    
    public FileListCellMouseListener(IFileItemActionListener listener) {
        myListener = listener;
    }
    
    @Override
    public void handle(MouseEvent t) {
        FileListCell cell = (FileListCell) t.getSource();
        
        if (cell.getItem()!=null) {
            File item = cell.getItem();
            if (t.getButton()==MouseButton.SECONDARY)
                myListener.contextMenu(item, t);
           
            if (t.getButton()==MouseButton.PRIMARY && t.getClickCount()==2)                
                myListener.heavySelection(item);
        } 
        t.consume();
    }
        
}
