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

import java.io.File;
import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CurrentDirectoryState {

    private final ObjectProperty<File> currentPathProperty = new SimpleObjectProperty();
    private final ObservableList<File> myObservableList = FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<File>> currentDirectoryItemsProperty = new SimpleObjectProperty(myObservableList);
    private final BooleanProperty singleFileSelectedProperty = new SimpleBooleanProperty();
    private final ObjectProperty<File> selectedFileProperty = new SimpleObjectProperty();
    private final ObjectProperty<MultipleSelectionModel<File>> selectedFilesProperty = new SimpleObjectProperty();

    // http://stackoverflow.com/questions/18530493/complex-concurrency-in-javafx-using-observablelists-and-properties-from-multipl
    // http://stackoverflow.com/users/404495/coder-nr-23
    // For hint found in question.
    public CurrentDirectoryState() {

        //update the 'single file selected' boolean flag
        selectedFilesProperty.addListener(new ChangeListener<MultipleSelectionModel<File>>() {

            @Override
            public void changed(ObservableValue<? extends MultipleSelectionModel<File>> ov, MultipleSelectionModel<File> oldVal, MultipleSelectionModel<File> newVal) {
                if (newVal.getSelectedIndices().size() == 1) {
                    selectedFileProperty.set(newVal.getSelectedItem());
                    singleFileSelectedProperty.set(true);
                } else {
                    selectedFileProperty.set(null);
                    singleFileSelectedProperty.set(false);
                }
            }
        });

        //update the files from the current directory path.
        currentPathProperty.addListener(new ChangeListener<File>() {

            @Override
            public void changed(ObservableValue<? extends File> ov, final File oldValue, final File newValue) {
                //There was a Platform.runLater call here
                System.out.println("The current directory is changed!!");
                clearFileSelection();
                //currentDirectoryItemsProperty.get().clear();
                myObservableList.clear();
                if (newValue != null) {
                    File[] files = newValue.listFiles();
                    myObservableList.setAll(files);
                }

            }
        });
        
        currentDirectoryItemsProperty.addListener(new ChangeListener<ObservableList<File>>(){

            @Override
            public void changed(ObservableValue<? extends ObservableList<File>> ov, ObservableList<File> t, ObservableList<File> t1) {
                System.out.println("Change listener called!!");
            }
        });
        
        //directoryFilesProperty
        currentDirectoryItemsProperty.get().addListener(new ListChangeListener<File>(){

            @Override
            public void onChanged(ListChangeListener.Change<? extends File> change) {
                System.out.println("List change listener called!!");
            }
        });
        
    }
    
    public void clearFileSelection() {
        selectedFilesProperty.get().clearSelection();
        singleFileSelectedProperty.set(false);
        selectedFileProperty.setValue(null);
    }

    public void setCurrentPath(File path) {
        currentPathProperty.setValue(path);
    }

    public File getCurrentPath() {
        return currentPathProperty.getValue();
    }

    public void setSelectedFile(File f) {
        selectedFilesProperty.get().getSelectedItems().setAll(f);
    }

    public File getSelectedFile() {
        return selectedFilesProperty.get().getSelectedItem();
    }

    public ObjectProperty<File> currentPathProperty() {
        return currentPathProperty;
    }

    public BooleanProperty singleFileSelectedProperty() {
        return singleFileSelectedProperty;
    }

    public ObjectProperty<File> selectedFileProperty() {
        return selectedFileProperty;
    }

    public ObjectProperty<MultipleSelectionModel<File>> selectedFilesProperty() {
        return selectedFilesProperty;
    }

    public ObjectProperty<ObservableList<File>> currentDirectoryItemsProperty() {
        return currentDirectoryItemsProperty;
    }

    /**
     * This action deletes the current file (or folder).
     *
     * @return
     */
    public boolean deleteCurrentFile() {

        File selected = getSelectedFile();

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

        File selected = getSelectedFile();

        String fullNewName = selected.getParentFile().getAbsolutePath();
        fullNewName += File.separatorChar + newName;

        File _newFile = new File(fullNewName);

        boolean result = selected.renameTo(_newFile);

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
        if (currentDir.getParentFile() != null) {
            setCurrentPath(currentDir.getParentFile());
            return true;
        }
        return false;
    }

}
