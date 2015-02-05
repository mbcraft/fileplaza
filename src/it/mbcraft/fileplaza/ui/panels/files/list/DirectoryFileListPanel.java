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
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.components.listview.ImprovedListView;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import java.io.File;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
public class DirectoryFileListPanel implements IZoomableNodeProvider,IItemViewer<File> {

    private final IntegerProperty zoomLevelProperty;
    private final BorderPane _fileListPanel;
    private final ImprovedListView<File> _fileList;
    
    public DirectoryFileListPanel() {
        
        zoomLevelProperty = new SimpleIntegerProperty(ZoomHelper.getMinLevelIndex());
                
        _fileListPanel = new BorderPane();                
        
        _fileList = new ImprovedListView<>();
        _fileList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        _fileListPanel.setCenter(_fileList);
        
    }
    
    public void setCellListener(IFileItemActionListener listener) {
        _fileList.setCellFactory(new FileListCellFactory(zoomLevelProperty,_fileList,listener));
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
        return _fileListPanel;
    }  
        
    @Override
    public IntegerProperty zoomLevelProperty() {
        return zoomLevelProperty;
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
