/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.ui.panels.files.icon;

import it.mbcraft.fileplaza.ui.common.components.IItemViewer;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.components.tileview.ImprovedTileView;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import java.io.File;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;

/**
 * This class creates a panel for showing file items in a 'grid' or 'tile' layout.
 * Supports zoom level and IElementActionListener interface for its items.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileViewIconPanel implements INodeProvider,IItemViewer<File> {
   
    private final IntegerProperty _zoomLevelProperty;
    private final ImprovedTileView<File> _fileIcons;
    
    //final component is a BorderPane, since ScrollPane is already used inside
    private final BorderPane _fullPanel;
    
    /**
     * Creates an instance of FileViewIconPanel.
     * 
     * @param zoomLevelProperty The IntegerProperty of the zoom level to be used.
     * @param listener The IElementActionListener to be used for all its items.
     */
    public FileViewIconPanel(IntegerProperty zoomLevelProperty,IElementActionListener listener) {
        
        _fullPanel = new BorderPane();
        
        _zoomLevelProperty = zoomLevelProperty;
                       
        _fileIcons = new ImprovedTileView<>();
        _fileIcons.selectionModelProperty().get().setSelectionMode(SelectionMode.SINGLE);      
        _fileIcons.cellFactoryProperty().set(new FileIconCellFactory(_zoomLevelProperty,_fileIcons,listener));
        
        _fullPanel.setCenter(_fileIcons);
        
        debug();
    }
    
    /**
     * Returns the items property for the panel, as a ObservableList<File> ObjectProperty.
     * 
     * @return the item list for this panel.
     */
    @Override
    public ObjectProperty<ObservableList<File>> itemsProperty() {
        return _fileIcons.itemsProperty();
    }
    
    /**
     * Returns the selection model for the panel.
     * 
     * @return The selection model, as a MultipleSelectionModel<File> ObjectProperty.
     */
    @Override
    public ObjectProperty<MultipleSelectionModel<File>> selectionModelProperty() {
        return _fileIcons.selectionModelProperty();
    }

    @Override
    public Node getNode() {
        return _fullPanel;
    }  
    
    /**
     * Debug method : adds console output when the item list changes.
     */
    private void debug() {
              
        itemsProperty().get().addListener(new ListChangeListener<File>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends File> change) {
                System.out.println("Items changed inside file view icon panel!!!");
            }
        });
        
        selectionModelProperty().get().selectedItemProperty().addListener(new ChangeListener<File>(){
            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                System.out.println("Selection changed inside file view icon panel!!! : " + (oldValue!=null ? oldValue.getAbsolutePath() : "null") + " -> " + (newValue!=null ? newValue.getAbsolutePath() : "null"));
            }
        });
    }
            
}
