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
import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.components.ImprovedListView;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import java.io.File;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;

/**
 * This class handle the lists of all the files and folders in the current directory.
 * Permission icons :
 * Read    : Page_Text_32
 * Write   : Pencil_Blue_32
 * Execute : Red_Thunder
 */
public class FileListPanel implements IZoomableNodeProvider {

    private final BooleanProperty zoomInDisabledProperty = new SimpleBooleanProperty();
    private final BooleanProperty zoomOutDisabledProperty = new SimpleBooleanProperty();
    
    private final BorderPane _fileListPanel;
    private final ImprovedListView<File> _fileList;
    private int zoomLevel = 0;
    
    public FileListPanel() {
        
        _fileListPanel = new BorderPane();                
        
        _fileList = new ImprovedListView<>();
        _fileList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        _fileListPanel.setCenter(_fileList);
        
        zoomInDisabledProperty.setValue(false);
        zoomOutDisabledProperty.setValue(true);

    }
    
    public void setCellListener(IFileItemActionListener listener) {
        _fileList.setCellFactory(new FileListCellFactory(listener));
    }
    
    public ObjectProperty<ObservableList<File>> itemsProperty() {
        return _fileList.itemsProperty();
    }
    
    public ObjectProperty<MultipleSelectionModel<File>> selectionModelProperty() {
        return _fileList.selectionModelProperty();
    }

    @Override
    public Node getNode() {
        return _fileListPanel;
    }  
    
    @Override
    public BooleanProperty zoomInDisabledProperty() {
        return zoomInDisabledProperty;
    }
    
    @Override
    public void zoomIn() {
        zoomLevel++;
        if (ZoomHelper.canZoomIn(zoomLevel))
            zoomInDisabledProperty.setValue(false);
        else
            zoomInDisabledProperty.setValue(true);
        
        zoomOutDisabledProperty.setValue(false);
        
        FileListCellFactory factory = (FileListCellFactory) _fileList.getCellFactory();
        factory.setZoomLevel(zoomLevel);
        _fileList.refreshAllItems();
    }
    
    @Override
    public BooleanProperty zoomOutDisabledProperty() {
        return zoomOutDisabledProperty;
    }
    
    @Override
    public void zoomOut() {
        zoomLevel--;
        if (ZoomHelper.canZoomOut(zoomLevel))
            zoomOutDisabledProperty.setValue(false);
        else
            zoomOutDisabledProperty.setValue(true);
        
        zoomInDisabledProperty.setValue(false);
        
        FileListCellFactory factory = (FileListCellFactory) _fileList.getCellFactory();
        factory.setZoomLevel(zoomLevel);
        _fileList.refreshAllItems();
    }

    /*
    private void debugSelections() {
                     
        _fileList.selectionModelProperty().get().selectedItemProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                System.out.println("Selection changed!!! : " + (oldValue!=null ? oldValue.getAbsolutePath() : "null") + " -> " + (newValue!=null ? newValue.getAbsolutePath() : "null"));
            }
        });
    }
    */
}
