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
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.DeleteFileAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.GoToParentFolderAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.NewFolderAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.RenameFileAction;
import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Contiene i pulsanti dei comandi relativi alla cartella corrente
 * (go to parent folder, new folder, rename file, delete file)
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.path.FileCommandsPanel")
public class DirectoryBrowserCommandsPanelProvider implements INodeProvider {

    private final HBox pane = new HBox();
    
    /**
     * Creates the directory browser commands panel. Needs the CurrentDirectoryState instance of changing path or doing action on the currently selected file.
     * @param state 
     */
    public DirectoryBrowserCommandsPanelProvider(final CurrentDirectoryState state) {
        
        initContainer();
                
        final Button goToParentFolderButton = new Button(L(this,"GoToParent_Button"));
        goToParentFolderButton.setGraphic(IconFactory.getFeatureIcon("Arrow_Up_Blue_32",ZoomHelper.getMinItemSize()));
        goToParentFolderButton.setOnAction(new GoToParentFolderAction(state));
        goToParentFolderButton.setDefaultButton(false);
        
        final Button newFolderButton = new Button(L(this,"NewFolder_Button"));
        newFolderButton.setGraphic(IconFactory.getFeatureIcon("Create",ZoomHelper.getMinItemSize()));
        newFolderButton.setOnAction(new NewFolderAction(state));
        newFolderButton.setDefaultButton(false);

        final Button renameFileButton = new Button(L(this,"Rename_Button"));
        renameFileButton.setGraphic(IconFactory.getFeatureIcon("Modify",ZoomHelper.getMinItemSize()));
        renameFileButton.setOnAction(new RenameFileAction(state));
        renameFileButton.setDefaultButton(false);
        renameFileButton.setDisable(true);
        
        final Button deleteFileButton = new Button(L(this,"Delete_Button"));
        deleteFileButton.setGraphic(IconFactory.getFeatureIcon("Red_Cross",ZoomHelper.getMinItemSize()));
        deleteFileButton.setOnAction(new DeleteFileAction(state));
        deleteFileButton.setDefaultButton(false);
        deleteFileButton.setDisable(true);
        
        
        state.currentPathProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                if (newValue.canWrite())
                    newFolderButton.setDisable(false);
                else
                    newFolderButton.setDisable(true);
                if (newValue.getParentFile()!=null)
                    goToParentFolderButton.setDisable(false);
                else
                    goToParentFolderButton.setDisable(true);
            
            }
        
        });
        
        state.singleFileSelectedProperty().addListener(new ChangeListener<Boolean>(){
   
            @Override
            public void changed(ObservableValue<? extends Boolean> ov, Boolean oldValue, Boolean newValue) {
                if (newValue && state.getSelectedFile().canWrite()) {
                    renameFileButton.setDisable(false);
                    deleteFileButton.setDisable(false);
                } else {
                    renameFileButton.setDisable(true);
                    deleteFileButton.setDisable(true);
                }
            }
        });
        
        pane.getChildren().addAll(goToParentFolderButton,newFolderButton,renameFileButton,deleteFileButton);
    }
          
    private void initContainer() {
        pane.setPadding(new Insets(5));
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setSpacing(5);
    }
    
    @Override
    public Node getNode() {
        return pane;
    }
    
}
