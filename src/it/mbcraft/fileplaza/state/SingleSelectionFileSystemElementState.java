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
 * This class keeps the current AbstractFileSystemElement selected instance_.
 * It contains methods used to change the current AbstractFileSystemElement.
 * It tracks changes and handles saving on file.
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
            
    public void setPriority(Priority p) {
        currentElement.getValue().setPriority(p);
        _currentElementChanged = true;
    }
    
    public Priority getPriority() {
        return currentElement.getValue().getPriority();
    }
    
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
    
    public String getCustomPreviewKey() {
        FileElement el = (FileElement) currentElement.getValue();
        return el.getCustomPreviewKey();
    }
    
    public void setNotes(String nt) {
        currentElement.getValue().setNotes(nt);
        _currentElementChanged = true;
    }
    
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
    
    public ObjectProperty previewDataProperty() {
        return currentPreviewData;
    }
    
    public StringProperty notesProperty() {
        return currentNotes;
    }
    
    public ListProperty<Tag> tagListProperty() {
        return currentTags;
    }
    
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