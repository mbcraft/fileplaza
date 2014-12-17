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

import it.mbcraft.fileplaza.ui.common.components.AbstractListCell;
import it.mbcraft.fileplaza.ui.common.helpers.IconReference;
import java.io.File;
import javafx.scene.text.Font;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileListCell extends AbstractListCell<File> {

    public FileListCell(int size) {
        super(size);
    }
    
    @Override
    public void updateItem(File data,boolean empty) {
        super.updateItem(data, empty);
        if (empty) {
            setText(null);
            setMainIcon(null);
        } else {
            setFont(Font.font("Arial", getItemSize()));
            setText(data.getName());
            if (data.isDirectory())
                pushFolderIcon();
            else
                pushFileIcon(data.getName());
        }
    }
    
    private void pushFolderIcon() {
        setMainIcon(new IconReference(IconReference.IconCategory.FILE,"folder"));
    }
    
    private void pushFileIcon(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".")+1);
        
        setMainIcon(new IconReference(IconReference.IconCategory.FILE,extension));   
    }
    
}
