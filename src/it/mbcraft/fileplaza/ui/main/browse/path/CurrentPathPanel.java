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

package it.mbcraft.fileplaza.ui.main.browse.path;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListCell;
import it.mbcraft.fileplaza.ui.main.browse.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.util.Callback;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.path.CurrentPathPanel")
public class CurrentPathPanel implements INodeProvider {
    
    private final FlowPane pathPanel = new FlowPane();
    private ComboBox driveSelector;

    private Label currentPath;
    
    private final CurrentDirectoryState myState;
    
    public CurrentPathPanel(CurrentDirectoryState state) {  
        
        myState = state;
        
        initContainer();
        
        initDriveSelector();
        
        initCurrentPath();               
    }
    
    private void initContainer() {
        pathPanel.setPadding(new Insets(5));
        pathPanel.setHgap(10);
    }
    
    private void initDriveSelector() {
        Label l = new Label(L(this,"Drive_Label"));
        l.setGraphic(IconFactory.getFeatureIcon("Drive_32", 16));
        pathPanel.getChildren().add(l);
        
        File[] roots = File.listRoots();
                
        driveSelector = new ComboBox();
        
        driveSelector.setCellFactory(new Callback<ListView<File>,ListCell<File>>(){

            @Override
            public ListCell<File> call(ListView<File> p) {
                return new FileListCell(ZoomHelper.getMinSize());
            }
        });
        driveSelector.getItems().setAll((Object[]) roots);
        driveSelector.getSelectionModel().selectFirst();
        
        driveSelector.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                updateState();
            }
            
        });

        pathPanel.getChildren().add(driveSelector);
    }
    
    private void updateState() {
        myState.setCurrentPath((File)driveSelector.getSelectionModel().getSelectedItem());  
    }
     
    private void initCurrentPath() {
        Label l = new Label(L(this,"CurrentDir_Label"));
        
        pathPanel.getChildren().add(l);
        
        currentPath = new Label();
        
        pathPanel.getChildren().add(currentPath);
        
        myState.currentPathProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                currentPath.setText(newValue.getAbsolutePath());
            }
        });
   
    }
    
    @Override
    public Node getNode() {
        return pathPanel;
    }
    

        
}
