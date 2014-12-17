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

package it.mbcraft.fileplaza.ui.main.sort;

import it.mbcraft.fileplaza.algorithm.sort.FileElementSort;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListPanel;
import it.mbcraft.fileplaza.ui.main.sort.filters.FiltersPanel;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    private FileListPanel previewedList;
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
        previewedList = new FileListPanel();
        previewedList.setCellListener(new SortFileListListener(previewedList.itemsProperty().get(),currentSort));
        sortDetails = new SortDetailsPanel(previewedList.itemsProperty().get(),previewedList.selectionModelProperty(),currentSort);
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
