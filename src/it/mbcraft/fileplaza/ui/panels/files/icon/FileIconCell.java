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

import it.mbcraft.fileplaza.ui.common.components.tileview.ImprovedTileCell;
import it.mbcraft.fileplaza.ui.common.helpers.IconReference;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.text.Font;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileIconCell extends ImprovedTileCell<File> {

    public FileIconCell(int size) {
        super(size);
        itemProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                if (newValue!=null)
                    updateItem(newValue,false);
                else
                    updateItem(null,true);
            }
        });
    }
    
    @Override
    public void updateItem(File data,boolean empty) {
        super.updateItem(data, empty);
        if (empty) {
            setLabelText(null);
            setMainIcon(null);
        } else {
            setLabelFont(Font.font("Arial", getItemSize()));
            setLabelText(data.getName());
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