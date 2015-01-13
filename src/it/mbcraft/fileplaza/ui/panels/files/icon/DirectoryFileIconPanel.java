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

import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.components.tileview.ImprovedTileView;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DirectoryFileIconPanel implements IZoomableNodeProvider {

    private final BooleanProperty zoomInDisabledProperty = new SimpleBooleanProperty();
    private final BooleanProperty zoomOutDisabledProperty = new SimpleBooleanProperty();
    
    private final BorderPane _fileIconsPanel;
    private final ImprovedTileView<File> _fileIcons;
    private int zoomLevel = 0;
    
    public DirectoryFileIconPanel() {
        
        _fileIconsPanel=  new BorderPane();                
        
        _fileIcons = new ImprovedTileView<>();
        _fileIcons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _fileIcons.setCellFactory(new FileIconCellFactory(null));
        
        _fileIconsPanel.setCenter(_fileIcons);
        
        zoomInDisabledProperty.setValue(false);
        zoomOutDisabledProperty.setValue(true);

    }
    
    public void setCellListener(IFileItemActionListener listener) {
        _fileIcons.setCellFactory(new FileIconCellFactory(listener));
    }
    
    public ObjectProperty<ObservableList<File>> itemsProperty() {
        return _fileIcons.itemsProperty();
    }
    
    public ObjectProperty<MultipleSelectionModel<File>> selectionModelProperty() {
        return _fileIcons.selectionModelProperty();
    }

    @Override
    public Node getNode() {
        return _fileIconsPanel;
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
        
        FileIconCellFactory factory = (FileIconCellFactory) _fileIcons.getCellFactory();
        factory.setZoomLevel(zoomLevel);
        _fileIcons.refreshAllItems();
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
        
        FileIconCellFactory factory = (FileIconCellFactory) _fileIcons.getCellFactory();
        factory.setZoomLevel(zoomLevel);
        _fileIcons.refreshAllItems();
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
