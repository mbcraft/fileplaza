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
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;

/**
 * This class handles the view of a list of files.
 * 
 */
public class FileViewListPanel implements INodeProvider,IItemViewer<File> {

    private final IntegerProperty _zoomLevelProperty;
    private final ImprovedListView<File> _fileList;
    
    //final component is a BorderPane
    private final BorderPane _fullPanel;
    
    public FileViewListPanel(IntegerProperty zoomLevelProperty,IElementActionListener listener) {
        
        _zoomLevelProperty = zoomLevelProperty;
                
        _fullPanel = new BorderPane();       
                
        _fileList = new ImprovedListView<>();
        //_fileList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        _fileList.setCellFactory(new FileListCellFactory(_zoomLevelProperty,_fileList,listener));
        
        _fullPanel.setCenter(_fileList);
        
        debug();
        
    }
    
    @Override
    public ObjectProperty<ObservableList<File>> itemsProperty() {
        return _fileList.itemsProperty();
    }
    
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
