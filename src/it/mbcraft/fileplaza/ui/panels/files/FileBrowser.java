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

package it.mbcraft.fileplaza.ui.panels.files;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.components.misc.ImprovedStackPane;
import it.mbcraft.fileplaza.ui.common.components.misc.SwitchIconsPanel;
import it.mbcraft.fileplaza.ui.common.components.misc.ZoomInOutPanelProvider;
import it.mbcraft.fileplaza.ui.main.browse.BrowsePanelFileListener;
import it.mbcraft.fileplaza.ui.main.browse.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.main.browse.path.CurrentPathPanel;
import it.mbcraft.fileplaza.ui.main.browse.path.DirectoryBrowserCommandsPanelProvider;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileViewIconPanel;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListViewPanel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Implements a full file browser. It has a current directory and a current selection.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileBrowser implements INodeProvider {
    
    private final CurrentDirectoryState currentDirState = new CurrentDirectoryState();
    
    private final CurrentPathPanel currentPathPanel; 
    private final DirectoryBrowserCommandsPanelProvider fileCommandsPanel;
    
    private final SwitchIconsPanel viewSwitchPanel;
    private final ZoomInOutPanelProvider zoomInOutPanel;
            
    private final FileViewIconPanel directoryFileIconPanel;
    private final FileListViewPanel directoryFileListPanel;
    
    private final VBox layoutPanel = new VBox();
    
    public FileBrowser() {        
        currentPathPanel = new CurrentPathPanel(currentDirState);
        
        fileCommandsPanel = new DirectoryBrowserCommandsPanelProvider(currentDirState);
        
        List<INodeProvider> panels = new ArrayList<>();
        viewSwitchPanel = new SwitchIconsPanel(panels);
        zoomInOutPanel = new ZoomInOutPanelProvider();
        
        HBox switchAndZoom = new HBox();
        switchAndZoom.getChildren().addAll(viewSwitchPanel.getNode(),zoomInOutPanel.getNode());
        
        ImprovedStackPane fileViewStackPane = new ImprovedStackPane();
        IElementActionListener listener = new BrowsePanelFileListener(currentDirState);
        directoryFileListPanel = new FileListViewPanel(zoomInOutPanel.zoomLevelProperty(),listener);
        
        directoryFileIconPanel = new FileViewIconPanel(zoomInOutPanel.zoomLevelProperty(),listener);
        
        //selected files are the same on both views
        currentDirState.selectedFilesProperty().bindBidirectional(directoryFileListPanel.selectionModelProperty());
        currentDirState.selectedFilesProperty().bindBidirectional(directoryFileIconPanel.selectionModelProperty());
        
        currentDirState.currentDirectoryItemsProperty().bind(directoryFileListPanel.itemsProperty());
        currentDirState.currentDirectoryItemsProperty().bind(directoryFileIconPanel.itemsProperty());
        
        panels.add(directoryFileListPanel);
        panels.add(directoryFileIconPanel);
        fileViewStackPane.getChildren().addAll(directoryFileListPanel.getNode(),directoryFileIconPanel.getNode());
        fileViewStackPane.selectedPanelIndexProperty().setValue(0);
        //current path
        //commands
        //switch and zoom
        //file views
        layoutPanel.getChildren().addAll(currentPathPanel.getNode(),fileCommandsPanel.getNode(),switchAndZoom,fileViewStackPane);
        
        System.out.println("layoutPanel width : "+layoutPanel.widthProperty().toString());
        System.out.println("layoutPanel height : "+layoutPanel.heightProperty().toString());
    }
    
    public CurrentDirectoryState getCurrentDirectoryState() {
        return currentDirState;
    }

    @Override
    public Node getNode() {
        return layoutPanel;
    }
}
