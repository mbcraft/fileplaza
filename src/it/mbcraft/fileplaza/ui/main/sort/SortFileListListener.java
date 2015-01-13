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

package it.mbcraft.fileplaza.ui.main.sort;

import it.mbcraft.fileplaza.algorithm.sort.FileElementSort;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
class SortFileListListener implements IFileItemActionListener {

    private final ObservableList<File> myFileList;
    private final ObjectProperty<FileElementSort> mySort;
    
    public SortFileListListener(ObservableList<File> fileList,ObjectProperty<FileElementSort> sortProperty) {
        myFileList = fileList;
        mySort = sortProperty;
    }

    @Override
    public void heavySelection(File f, MouseEvent ev,SelectionPlace p) {
        mySort.get().sort(f);
        myFileList.remove(f);
    }

    @Override
    public void contextMenu(File f, MouseEvent ev,SelectionPlace p) {
    }

    @Override
    public void simpleSelection(File f, MouseEvent ev,SelectionPlace p) {
    
    }
    
}
