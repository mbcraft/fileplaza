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

package it.mbcraft.fileplaza.ui.panels.files.list;

import it.mbcraft.fileplaza.ui.common.components.IItemViewer;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import it.mbcraft.fileplaza.ui.common.components.listview.ImprovedListView;
import java.io.File;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.BorderPane;

/**
 * This class creates a view for a list of files. It actually supports
 * the zoom level and the the IElementActionListener interface for dealing with operations on the items.
 */
public class FileViewListPanel implements INodeProvider,IItemViewer<File> {

    private final IntegerProperty _zoomLevelProperty;
    private final ImprovedListView<File> _fileList;
    
    //final component is a BorderPane
    private final BorderPane _fullPanel;
    
    
    /**
     * Creates a FileViewListPanel.
     * 
     * @param zoomLevelProperty The IntegerProperty that contains the zoom to be used for the list items.
     * @param listener The listener interface, for dealing with events on the file items.
     */
    public FileViewListPanel(IntegerProperty zoomLevelProperty,IElementActionListener listener) {
        
        _zoomLevelProperty = zoomLevelProperty;
                
        _fullPanel = new BorderPane();       
                
        _fileList = new ImprovedListView<>();
        //_fileList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _fileList.setCellFactory(new FileListCellFactory(_zoomLevelProperty,_fileList,listener));
        
        _fullPanel.setCenter(_fileList);
        
        debug();
        
    }
    
    /**
     * Returns the items property for this widget.
     * 
     * @return The ObservableList<File> as an ObjectProperty.
     */
    @Override
    public ObjectProperty<ObservableList<File>> itemsProperty() {
        return _fileList.itemsProperty();
    }
    
    /**
     * Returns the selection model for the FileViewListPanel
     * 
     * @return The MultipleSelectionModel<File>, as an ObjectProperty
     */
    @Override
    public ObjectProperty<MultipleSelectionModel<File>> selectionModelProperty() {
        return _fileList.selectionModelProperty();
    }

    @Override
    public Node getNode() {
        return _fullPanel;
    }  
        
    
    private void debug() {
              
        itemsProperty().get().addListener(new ListChangeListener<File>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends File> change) {
                System.out.println("Items changed inside file list view panel!!!");
            }
        });
        
        selectionModelProperty().get().selectedItemProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                System.out.println("Selection changed inside file list view panel!!! : " + (oldValue!=null ? oldValue.getAbsolutePath() : "null") + " -> " + (newValue!=null ? newValue.getAbsolutePath() : "null"));
            }
        });
    }
    
}
