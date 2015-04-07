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

package it.mbcraft.fileplaza.ui.main.sort;

import it.mbcraft.fileplaza.algorithm.sort.FileElementSort;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListViewPanel;
import it.mbcraft.fileplaza.ui.main.sort.filters.FiltersPanel;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.sort.SortPanel")
public class SortPanel implements INodeProvider {
    
    private final VBox box = new VBox();
    
    private FiltersPanel filters;
    private FileListViewPanel previewedList;
    private SortDetailsPanel sortDetails;
    
    private Button sortAllButton;

    private final ObjectProperty<FileElementSort> currentSort = new SimpleObjectProperty();

    public SortPanel() {
        initContainer();
        initComponents();
        initContent();
        initListeners();
    }
    
    private void initContainer() {
        box.setPadding(new Insets(5));
        box.setAlignment(Pos.TOP_LEFT);
    }
    
    private void initComponents() {
        filters = new FiltersPanel(currentSort);
        ObservableList<File> elements = FXCollections.observableArrayList();
        previewedList = new FileListViewPanel(new SimpleIntegerProperty(ZoomHelper.getMinLevelIndex()),new SortFileListListener(elements,currentSort));
        sortDetails = new SortDetailsPanel(elements,previewedList.selectionModelProperty(),currentSort);
    }
    
    private void initContent() {
        box.getChildren().add(filters.getNode());
        box.getChildren().add(previewedList.getNode());
        box.getChildren().add(sortDetails.getNode());
        
        FlowPane buttons = new FlowPane();
        sortAllButton = new Button(L(this,"SortAll_Button"));
        sortAllButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                currentSort.get().sortAll();
                currentSort.set(null);
            }
        });
        sortAllButton.setDisable(true);
        
        box.getChildren().add(buttons);
    }
    
    private void initListeners() {
        currentSort.addListener(new ChangeListener<FileElementSort>(){

            @Override
            public void changed(ObservableValue<? extends FileElementSort> ov, FileElementSort oldValue, FileElementSort newValue) {
                if (newValue==null) {
                    sortAllButton.setDisable(true);
                    previewedList.itemsProperty().get().clear();
                }
                else {
                    sortAllButton.setDisable(false);
                    previewedList.itemsProperty().get().setAll(newValue.getFileList());
                }
            }
        });
        
        
    }
    
    @Override
    public Node getNode() {
        return box;
    }

}
