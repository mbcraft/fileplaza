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

package it.mbcraft.fileplaza.ui.main.browse;

import it.mbcraft.fileplaza.ui.common.components.IItemViewer;
import it.mbcraft.fileplaza.ui.panels.files.NotHiddenFileFilter;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;

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
    
    public void linkItemViewerItems(final IItemViewer<File> viewer) {
        
        currentPath.addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                File list[] = currentPath.get().listFiles(new NotHiddenFileFilter());
        
                viewer.itemsProperty().set(FXCollections.observableArrayList(list));
                //viewer.itemsProperty().get().setAll(list);    
            }
        });
        

    }
    
    public void linkItemViewerSelectionModel(final IItemViewer<File> viewer) {
        viewer.selectionModelProperty().get().selectedItemProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                setSelectedFile(newValue);
            }
        });
    }
    
    
}
