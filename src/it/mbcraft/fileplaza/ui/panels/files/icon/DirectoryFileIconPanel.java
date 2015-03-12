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

package it.mbcraft.fileplaza.ui.panels.files.icon;

import it.mbcraft.fileplaza.ui.common.components.IItemViewer;
import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.components.tileview.ImprovedTileView;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import java.io.File;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DirectoryFileIconPanel implements IZoomableNodeProvider,IItemViewer<File> {

    private final IntegerProperty zoomLevelProperty;
    private final ScrollPane _fileIconsPanel;
    private final ImprovedTileView<File> _fileIcons;
    
    public DirectoryFileIconPanel() {
        zoomLevelProperty = new SimpleIntegerProperty(ZoomHelper.getMinLevelIndex());
        
        _fileIcons = new ImprovedTileView<>();
        _fileIcons.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        _fileIconsPanel = new ScrollPane();

        _fileIconsPanel.setPannable(false);
        _fileIconsPanel.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        _fileIconsPanel.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        _fileIconsPanel.setFitToWidth(true);
        _fileIconsPanel.setContent(_fileIcons);
    }
    
    public void setCellListener(IFileItemActionListener listener) {
        _fileIcons.setCellFactory(new FileIconCellFactory(zoomLevelProperty,_fileIcons,listener));
    }
    
    @Override
    public ObjectProperty<ObservableList<File>> itemsProperty() {
        return _fileIcons.itemsProperty();
    }
    
    @Override
    public ObjectProperty<MultipleSelectionModel<File>> selectionModelProperty() {
        return _fileIcons.selectionModelProperty();
    }

    @Override
    public Node getNode() {
        return _fileIconsPanel;
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
