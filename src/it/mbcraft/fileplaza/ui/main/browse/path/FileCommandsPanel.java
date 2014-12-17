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
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.DeleteFileAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.GoToParentFolderAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.NewFolderAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.RenameFileAction;
import it.mbcraft.fileplaza.ui.main.browse.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.ZoomInAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.ZoomOutAction;
import java.io.File;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.path.FileCommandsPanel")
public class FileCommandsPanel implements INodeProvider {

    private final FlowPane box = new FlowPane();
    
    private final IZoomableNodeProvider zoomableNode;
    
    public FileCommandsPanel(CurrentDirectoryState state,IZoomableNodeProvider zoomableNodeProvider) {
       
        zoomableNode = zoomableNodeProvider;
        
        initContainer();
        
        final Button goToParentFolderButton = new Button(L(this,"GoToParent_Button"));
        goToParentFolderButton.setGraphic(IconFactory.getFeatureIcon("Arrow_Up_Blue_32",ZoomHelper.getMinSize()));
        goToParentFolderButton.setOnAction(new GoToParentFolderAction(state));
        goToParentFolderButton.setDefaultButton(false);
        
        final Button newFolderButton = new Button(L(this,"NewFolder_Button"));
        newFolderButton.setGraphic(IconFactory.getFeatureIcon("Create",ZoomHelper.getMinSize()));
        newFolderButton.setOnAction(new NewFolderAction(state));
        newFolderButton.setDefaultButton(false);

        final Button renameFileButton = new Button(L(this,"Rename_Button"));
        renameFileButton.setGraphic(IconFactory.getFeatureIcon("Modify",ZoomHelper.getMinSize()));
        renameFileButton.setOnAction(new RenameFileAction(state));
        renameFileButton.setDefaultButton(false);
        renameFileButton.setDisable(true);
        
        final Button deleteFileButton = new Button(L(this,"Delete_Button"));
        deleteFileButton.setGraphic(IconFactory.getFeatureIcon("Red_Cross",ZoomHelper.getMinSize()));
        deleteFileButton.setOnAction(new DeleteFileAction(state));
        deleteFileButton.setDefaultButton(false);
        deleteFileButton.setDisable(true);
        
        final Button zoomInButton = new Button(L(this,"ZoomIn_Button"));
        zoomInButton.setGraphic(IconFactory.getFeatureIcon("Plus_Silver_32",ZoomHelper.getMinSize()));
        zoomInButton.setOnAction(new ZoomInAction(zoomableNode));
        zoomInButton.setDefaultButton(false);
        zoomInButton.disableProperty().bind(zoomableNode.zoomInDisabledProperty());
        
        final Button zoomOutButton = new Button(L(this,"ZoomOut_Button"));
        zoomOutButton.setGraphic(IconFactory.getFeatureIcon("Minus_Silver_32",ZoomHelper.getMinSize()));
        zoomOutButton.setOnAction(new ZoomOutAction(zoomableNode));
        zoomOutButton.setDefaultButton(false);
        zoomOutButton.disableProperty().bind(zoomableNode.zoomOutDisabledProperty());
        
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
        
        state.currentSelectedFileProperty().addListener(new ChangeListener<File>(){
   
            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                if (newValue!=null && newValue.canWrite()) {
                    renameFileButton.setDisable(false);
                    deleteFileButton.setDisable(false);
                } else {
                    renameFileButton.setDisable(true);
                    deleteFileButton.setDisable(true);
                }
            }
        });
        
        box.getChildren().addAll(goToParentFolderButton,newFolderButton,renameFileButton,deleteFileButton,zoomInButton, zoomOutButton);
    }
          
    private void initContainer() {
        box.setPadding(new Insets(5));
        box.setAlignment(Pos.CENTER_LEFT);
        box.setHgap(10);
    }
    
    @Override
    public Node getNode() {
        return box;
    }
    
}
