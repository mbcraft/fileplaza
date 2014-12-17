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

package it.mbcraft.fileplaza.ui.main.browse;

import it.mbcraft.fileplaza.ui.panels.files.list.FileListPanel;
import it.mbcraft.fileplaza.ui.panels.files.NotHiddenFileFilter;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CurrentDirectoryState {
    
    private final ObjectProperty<File> currentPath = new SimpleObjectProperty();
    private final ObjectProperty<File> currentSelectedFile = new SimpleObjectProperty();
    
    public void setCurrentPath(File path) {
        currentPath.setValue(path);
    }
    
    public File getCurrentPath() {
        return currentPath.getValue();
    }
    
    public void setSelectedFile(File f) {
        currentSelectedFile.setValue(f);
    }
    
    public File getCurrentSelectedFile() {
        return currentSelectedFile.getValue();
    }
    
    public ObjectProperty<File> currentPathProperty() {
        return currentPath;
    }
    
    public ObjectProperty<File> currentSelectedFileProperty() {
        return currentSelectedFile;
    }

    /**
     * This action deletes the current file (or folder).
     * 
     * @return 
     */
    public boolean deleteCurrentFile() {
        
        File selected = getCurrentSelectedFile();
        
        boolean result = selected.delete();
        
        setCurrentPath(getCurrentPath());
        
        return result;
    }
    
    /**
     * This action renames the current file to the new name provided.
     * 
     * @param newName The new name to assign to this file (or folder).
     * @return 
     */
    public boolean renameCurrentFile(String newName) {
        
        setSelectedFile(null);
        
        File selected = getCurrentSelectedFile();
        
        String fullNewName = selected.getParentFile().getAbsolutePath();
        fullNewName += File.separatorChar + newName;
        
        File _newFile = new File(fullNewName);
        
        boolean result  = selected.renameTo(_newFile);
        
        if (result) {
            setCurrentPath(_newFile.getParentFile());
            setSelectedFile(_newFile);
        }
        return result;
    }
    
    public boolean newFolder(String name) {
        File newFolder = new File(getCurrentPath().getAbsolutePath() + File.separator + name);
        boolean result = newFolder.mkdir();
        return result;
    }
    
    public boolean goToParentFolder() {
        File currentDir = getCurrentPath();
        if (currentDir.getParentFile()!=null)
        {
            setCurrentPath(currentDir.getParentFile());
            return true;
        }
        return false;
    }
    
    public void linkWithFileList(final FileListPanel panel) {
        
        currentPath.addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                File list[] = currentPath.get().listFiles(new NotHiddenFileFilter());
        
                //panel.selectionModelProperty().get().clearSelection();

                //panel.itemsProperty().get().clear();
                panel.itemsProperty().get().setAll(list);    
            }
        });
        
        panel.selectionModelProperty().get().selectedItemProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                setSelectedFile(newValue);
            }
        });
    }
    
    
}
