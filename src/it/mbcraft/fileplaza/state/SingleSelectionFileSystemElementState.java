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

package it.mbcraft.fileplaza.state;

import it.mbcraft.fileplaza.data.dao.fs.FileElementDAO;
import it.mbcraft.fileplaza.data.dao.fs.FolderElementDAO;
import it.mbcraft.fileplaza.data.models.AbstractFileSystemElement;
import it.mbcraft.fileplaza.data.models.FileElement;
import it.mbcraft.fileplaza.data.models.FolderElement;
import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.data.models.Priority;
import it.mbcraft.fileplaza.data.models.Tag;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.fs.FileElementManager;
import it.mbcraft.fileplaza.data.serialization.managers.fs.FolderElementManager;
import it.mbcraft.fileplaza.data.misc.PreviewUpdateBehaviour;
import it.mbcraft.fileplaza.data.serialization.managers.meta.PreviewManager;
import java.io.File;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

/**
 * TODO : refactor for including multiple file selection
 * 
 * This class keeps the current AbstractFileSystemElement selected instance_.
 * It contains methods used to change the current AbstractFileSystemElement.
 * It tracks changes and handles saving of data associated to this file.
 * 
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SingleSelectionFileSystemElementState {
    
    private final PreviewData NO_FILE_SELECTED_PREVIEW_DATA = new PreviewData(null,null);
    
    private final ObjectProperty<AbstractFileSystemElement> currentElement = new SimpleObjectProperty();
    private final ObjectProperty<PreviewData> currentPreviewData = new SimpleObjectProperty(NO_FILE_SELECTED_PREVIEW_DATA);
    private final StringProperty currentNotes = new SimpleStringProperty();
    private final ListProperty<Tag> currentTags = new SimpleListProperty(FXCollections.observableArrayList());
    private boolean _currentElementChanged = false;
    
    private static SingleSelectionFileSystemElementState instance = null;
    
    /**
     * Returns the current unique instance of this class.
     * 
     * @return A SingleSelectionFileSystemElementState instance
     */
    public static SingleSelectionFileSystemElementState getInstance() {
        if (instance==null)
            instance = new SingleSelectionFileSystemElementState();
        return instance;
    }
    
    private SingleSelectionFileSystemElementState() {  
               
        currentPreviewData.addListener(new ChangeListener<PreviewData>(){
            @Override
            public void changed(ObservableValue<? extends PreviewData> ov, PreviewData oldData, PreviewData newData) {
                
                if (PreviewUpdateBehaviour.needsUpdate(oldData, newData))
                    setCustomPreviewKey(newData.getPreviewKey());
            }
        });
        
        currentNotes.addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                setNotes(newValue);
            }
        });
        
        currentTags.addListener(new ListChangeListener<Tag>(){

            @Override
            public void onChanged(ListChangeListener.Change<? extends Tag> change) {
                _currentElementChanged = true;
            }
        }); 
    }
                    
    private void initWithDirectory(File f) {
        FolderElementDAO dao = FolderElementDAO.getInstance();
        
        String key = FolderElementDAO.getFolderKeyFromPath(f.getAbsolutePath());
        FolderElement el;
        if (dao.hasKey(key)) {
            el = (FolderElement) dao.find(key);
        } else {
            el = new FolderElement();
            el.setupWithFolder(f);
        }
        if (el==null) throw new IllegalStateException("Element is null!!");
        
        //file is always last
        currentElement.set(el);
        currentNotes.set(el.getNotes());
        currentTags.setAll(el.getTags());
        currentPreviewData.setValue(new PreviewData(f,null));
    }
    
    private void initWithFile(File f) {
        FileElementDAO dao = FileElementDAO.getInstance();

        String key = FileElementDAO.getFileKeyFromPath(f.getAbsolutePath());
        FileElement el;
        if (dao.hasKey(key)) {
            el = (FileElement) dao.find(key);
        } else {
            el = new FileElement();
            el.setupWithFile(f);
        }
        if (el==null) throw new IllegalStateException("Element is null!!");
        
        currentElement.set(el);
        currentNotes.set(el.getNotes());
        currentTags.setAll(el.getTags());
        currentPreviewData.setValue(new PreviewData(f,el.getCustomPreviewKey()));
    }
    
    private void initWithNull() {
        currentElement.set(null);
        currentTags.clear();
        currentPreviewData.set(NO_FILE_SELECTED_PREVIEW_DATA);
    }
            
    /**
     * Sets the priority on the currently selected file.
     * 
     * @param p The Priority p to set 
     */
    public void setPriority(Priority p) {
        currentElement.getValue().setPriority(p);
        _currentElementChanged = true;
    }
    
    /**
     * Returns the Priority associated with the current element.
     * 
     * @return The Priority instance
     */
    public Priority getPriority() {
        return currentElement.getValue().getPriority();
    }
    
    /**
     * Sets the custom preview key associated with the currently selected file.
     * 
     * @param nk The preview key
     */
    public void setCustomPreviewKey(String nk) {
        FileElement el = (FileElement) currentElement.getValue();
        
        String currentPreviewKey = el.getCustomPreviewKey();
        if (currentPreviewKey!=null) {
            AbstractModelManager ser = new PreviewManager("");
            ser.deleteByKey(currentPreviewKey);
        }
        
        el.setCustomPreviewKey(nk);
        _currentElementChanged = true;
    }
    
    /**
     * Gets the custom preview key associated with this file
     * 
     * @return A string containing the custom preview key of this file
     */
    public String getCustomPreviewKey() {
        FileElement el = (FileElement) currentElement.getValue();
        return el.getCustomPreviewKey();
    }
    
    /**
     * Returns the notes associated to this file
     * 
     * @param nt The notes associated to this file
     */
    public void setNotes(String nt) {
        currentElement.getValue().setNotes(nt);
        _currentElementChanged = true;
    }
    
    /**
     * Returns the notes associated to the currently selected file
     * 
     * @return The notes associated to this file
     */
    public String getNotes() {
        return currentElement.getValue().getNotes();
    }
    
    private void saveCurrentFileElement(FileElement el) {
        el.recalculateSha256();
        FileElementManager fem = new FileElementManager("");
        fem.saveOrUpdate(el);
    }

    private void saveCurrentFolderElement(FolderElement el) {
        el.recalculateSha256();
        FolderElementManager fem = new FolderElementManager("");
        fem.saveOrUpdate(el);
    }
    
    /**
     * Saves the metadata associated to the current element if needed.
     */
    public void saveCurrentElementIfNeeded() {
        if (currentElement.getValue()!=null && _currentElementChanged) {
            AbstractFileSystemElement element = currentElement.getValue();
            element.getTags().clear();
            element.getTags().addAll(currentTags);
            if (element instanceof FileElement) 
                saveCurrentFileElement((FileElement)element);
            if (element instanceof FolderElement)
                saveCurrentFolderElement((FolderElement)element);
        }
    }
    
    /**
     * Returns the ObjectProperty containing the PreviewData for this file.
     * 
     * @return An ObjectProperty containing the PreviewData of this file.
     */
    public ObjectProperty previewDataProperty() {
        return currentPreviewData;
    }
    
    /**
     * Returns the StringProperty containing the text notes for
     * this file.
     * 
     * @return The StringProperty containing the notes
     */
    public StringProperty notesProperty() {
        return currentNotes;
    }
    
    /**
     * Returns the tagList property for this class.
     * 
     * @return The tag list property of the generic tags associated with this file.
     */
    public ListProperty<Tag> tagListProperty() {
        return currentTags;
    }
    
    /**
     * Link the currently selected file with the provided ReadOnlyObjectProperty.
     * 
     * @param property The property used for reading data about the currently selected file.
     */
    public void linkWithFileProperty(ReadOnlyObjectProperty<File> property) {
        property.addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                
                saveCurrentElementIfNeeded();
                
                if (newValue!=null) {
                    if (newValue.isFile())
                        initWithFile(newValue);
                    else
                        initWithDirectory(newValue);
                }
                else
                    initWithNull();
                
                _currentElementChanged = false;
            }
        });
        
    }
    
}