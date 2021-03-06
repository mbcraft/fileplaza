/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.ui.panels.files;

import it.mbcraft.fileplaza.service.drivewatch.DriveWatchService;
import it.mbcraft.fileplaza.state.CurrentDriveState;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.components.misc.ImprovedStackPane;
import it.mbcraft.fileplaza.ui.common.components.misc.SwitchIconsPanel;
import it.mbcraft.fileplaza.ui.common.components.misc.ZoomInOutPanel;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.main.browse.BrowsePanelFileListener;
import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.main.browse.path.CurrentPathPanel;
import it.mbcraft.fileplaza.ui.main.browse.path.DirectoryBrowserCommandsPanelProvider;
import it.mbcraft.fileplaza.ui.panels.files.header.FileSortOptionsButton;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileViewIconPanel;
import it.mbcraft.fileplaza.ui.panels.files.list.FileViewListPanel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * This class creates a panel that shows a full file browser.
 * It actually features : 
 * - a current path panel, with a drive selector and a label that shows the current path
 * - a command panel, useful for going to parent directory or doing other generic operations
 * - a double widget file shower, with support for : zoom, switch widget and file sort order.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileBrowser implements INodeProvider {
    
    private final CurrentDriveState driveState = new CurrentDriveState();
    private final CurrentDirectoryState directoryState = new CurrentDirectoryState();
    
    private final CurrentPathPanel currentPathPanel; 
    private final DirectoryBrowserCommandsPanelProvider fileCommandsPanel;
    
    private final SwitchIconsPanel viewSwitchPanel;
    private final ZoomInOutPanel zoomInOutPanel;
    private final FileSortOptionsButton optionsPanel;
    
    private final FileViewIconPanel directoryFileIconPanel;
    private final FileViewListPanel directoryFileListPanel;
    
    private final VBox layoutPanel = new VBox();
    
    private final DriveWatchService watchService;
    
    public FileBrowser() {        
        
        driveState.linkDriveChangesToPathChanges(directoryState.currentPathProperty());
        
        currentPathPanel = new CurrentPathPanel(driveState,directoryState);
        
        fileCommandsPanel = new DirectoryBrowserCommandsPanelProvider(directoryState);
        
        List<INodeProvider> panels = new ArrayList<>();
        viewSwitchPanel = new SwitchIconsPanel(panels);
        zoomInOutPanel = new ZoomInOutPanel();
        optionsPanel = new FileSortOptionsButton(directoryState);
        
        HBox switchZoomAndOptions = new HBox();
        switchZoomAndOptions.getChildren().addAll(viewSwitchPanel.getNode(),zoomInOutPanel.getNode(),optionsPanel.getNode());
        
        ImprovedStackPane fileViewStackPane = viewSwitchPanel.getStackPane();
        IElementActionListener listener = new BrowsePanelFileListener(directoryState);
        
        //file views are created
        directoryFileListPanel = new FileViewListPanel(zoomInOutPanel.zoomLevelProperty(),listener);
        directoryFileIconPanel = new FileViewIconPanel(zoomInOutPanel.zoomLevelProperty(),listener);
        
        //pick current selected file from the model - to test with two widgets
        directoryState.selectedFilesProperty().bind(directoryFileListPanel.selectionModelProperty());
        directoryState.selectedFilesProperty().bind(directoryFileIconPanel.selectionModelProperty());
        
        //files shown in the two widgets are taken FROM che current dir state
        directoryFileListPanel.itemsProperty().bind(directoryState.currentDirectoryItemsProperty());
        directoryFileIconPanel.itemsProperty().bind(directoryState.currentDirectoryItemsProperty());

        //available file views are put inside a list
        panels.add(directoryFileListPanel);
        panels.add(directoryFileIconPanel);
        
        //adding buttons for switching the view
        viewSwitchPanel.addSwitchIcon(IconFactory.getFeatureIcon("Page_Lined_32", 32));
        viewSwitchPanel.addSwitchIcon(IconFactory.getFeatureIcon("Page_Green_Grid_32", 32));
        
        //panels are put inside the stacked view
        fileViewStackPane.getChildren().add(directoryFileListPanel.getNode());
        fileViewStackPane.getChildren().add(directoryFileIconPanel.getNode());
        fileViewStackPane.selectedPanelIndexProperty().setValue(0);
        
        //current path
        //commands
        //switch and zoom
        //file views
        layoutPanel.getChildren().addAll(currentPathPanel.getNode(),fileCommandsPanel.getNode(),switchZoomAndOptions,fileViewStackPane);
    
        watchService = new DriveWatchService(driveState.getDriveList());
        watchService.start();
    }
    
    /**
     * Gets the CurrentDirectoryState instance.
     * 
     * @return The current directory state as an instance.
     */
    public CurrentDirectoryState getCurrentDirectoryState() {
        return directoryState;
    }

    @Override
    public Node getNode() {
        return layoutPanel;
    }
}
