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

package it.mbcraft.fileplaza.ui.main.browse.path;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.service.os.DriveIdentifier;
import it.mbcraft.fileplaza.state.CurrentDriveState;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListCell;
import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.panels.files.drive.DriveIdentifierListCell;
import java.io.File;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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
 * This panel shows drive and some basic directory back commands.
 * (clicking on labels or 'buttons').
 * 
 * Takes the CurrentDirectoryState parameter as input since
 * CurrentDirectoryState has information about current selected file.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.path.CurrentPathPanel")
public class CurrentPathPanel implements INodeProvider {
    
    private final FlowPane pathPanel = new FlowPane();
    private ComboBox driveSelector;
    private Label currentPath;
    
    private final CurrentDriveState currentDriveState;
    private final CurrentDirectoryState currentDirectoryState;
    
    public CurrentPathPanel(CurrentDriveState driveState,CurrentDirectoryState directoryState) {  
        currentDriveState = driveState;
        currentDirectoryState = directoryState;
        
        initContainer();
        
        initDriveSelector();
        
        initCurrentPath();               
    }
    
    private void initContainer() {
        pathPanel.setPadding(new Insets(5));
        pathPanel.setHgap(10);
    }
    
    private void initDriveSelector() {                
        driveSelector = new ComboBox(currentDriveState.driveListProperty().get());

        driveSelector.setCellFactory(new Callback<ListView<DriveIdentifier>,ListCell<DriveIdentifier>>(){

            @Override
            public ListCell<DriveIdentifier> call(ListView<DriveIdentifier> p) {
                return new DriveIdentifierListCell(new SimpleIntegerProperty(1));
            }
        });

        //pick the selected drive from the currently selected drive in combobox
        currentDriveState.currentDriveProperty().bind(driveSelector.getSelectionModel().selectedItemProperty());
        
        pathPanel.getChildren().add(driveSelector);
    }
         
    private void initCurrentPath() {
        Label l = new Label(L(this,"CurrentDir_Label"));
        
        pathPanel.getChildren().add(l);
        
        currentPath = new Label();
        
        pathPanel.getChildren().add(currentPath);
        
        currentDirectoryState.currentPathProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                currentPath.setText(newValue.getAbsolutePath());
            }
        });
   
    }
    
    public CurrentDirectoryState getCurrentDirectoryState() {
        return currentDirectoryState; 
    }
    
    @Override
    public Node getNode() {
        return pathPanel;
    }
    

        
}
