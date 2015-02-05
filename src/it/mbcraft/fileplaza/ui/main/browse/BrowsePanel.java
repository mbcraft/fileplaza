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

package it.mbcraft.fileplaza.ui.main.browse;

import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.data.models.config.Settings;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.state.SingleSelectionFileSystemElementState;
import it.mbcraft.fileplaza.ui.main.browse.path.CurrentPathPanel;
import it.mbcraft.fileplaza.ui.main.browse.path.FileCommandsPanel;
import it.mbcraft.fileplaza.ui.panels.files.list.DirectoryFileListPanel;
import it.mbcraft.fileplaza.ui.panels.info.InfoPanel;
import it.mbcraft.fileplaza.ui.panels.notes.NotesPanel;
import it.mbcraft.fileplaza.ui.panels.preview.PreviewPanel;
import it.mbcraft.fileplaza.ui.panels.tags.FullTagsPanel;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.panels.files.icon.DirectoryFileIconPanel;
import it.mbcraft.fileplaza.utils.FileUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class BrowsePanel implements INodeProvider {

    private final VBox box = new VBox();
    
    private final CurrentPathPanel currentPathPanel;
    private final ViewCommandsPanel viewCommandsPanel;
    private final FileCommandsPanel fileCommandsPanel;
    
    private DirectoryFileIconPanel directoryFileIconPanel;
    private DirectoryFileListPanel directoryFileListPanel;
    private final SplitPane splitPane = new SplitPane();
    private final PreviewPanel previewPanel;
    private final NotesPanel notesPanel;
    private final InfoPanel infoPanel;
    
    private StackPane fileViewStackPane;
    private final FlowPane buttonsPane;
    
    private final FullTagsPanel fullTagsPanel;
    
    private final IntegerProperty sharedZoomLevel;
    
    public BrowsePanel() {
        
        sharedZoomLevel = new SimpleIntegerProperty(ZoomHelper.getMinLevelIndex());
        
        CurrentDirectoryState currentDirState = new CurrentDirectoryState();
        SingleSelectionFileSystemElementState currentFileSystemElementState = SingleSelectionFileSystemElementState.getInstance();
        
        List<IZoomableNodeProvider> viewList = initFileViewStackPane(currentDirState);
        
        
        currentPathPanel = new CurrentPathPanel(currentDirState);
        
        buttonsPane = new FlowPane();
        
        viewCommandsPanel = new ViewCommandsPanel(viewList);
        
        fileCommandsPanel = new FileCommandsPanel(currentDirState);
        
        buttonsPane.getChildren().add(viewCommandsPanel.getNode());
        buttonsPane.getChildren().add(fileCommandsPanel.getNode());
        
        currentDirState.linkItemViewerItems(directoryFileListPanel);
        currentDirState.linkItemViewerSelectionModel(directoryFileIconPanel);
        currentDirState.linkItemViewerItems(directoryFileIconPanel);
        currentFileSystemElementState.linkWithFileProperty(directoryFileListPanel.selectionModelProperty().get().selectedItemProperty());
        
        previewPanel = new PreviewPanel(currentFileSystemElementState.previewDataProperty());
        notesPanel = new NotesPanel(currentDirState.currentSelectedFileProperty(),currentFileSystemElementState.notesProperty());
        infoPanel = new InfoPanel(currentDirState.currentSelectedFileProperty());
        
        fullTagsPanel = new FullTagsPanel(L(this,"TagsPanel_Label"),false,currentFileSystemElementState.tagListProperty());
    
        setupMiddleSplitPane();
        
        initState(currentDirState);
        
        setupMainContent();  
    }
        
    private List<IZoomableNodeProvider> initFileViewStackPane(CurrentDirectoryState currentDirState) {
        
        fileViewStackPane = new StackPane();
        
        directoryFileListPanel = new DirectoryFileListPanel();
        directoryFileListPanel.setCellListener(new BrowsePanelFileListener(currentDirState));
        
        fileViewStackPane.getChildren().add(directoryFileListPanel.getNode());
        
        directoryFileIconPanel = new DirectoryFileIconPanel();
        directoryFileIconPanel.setCellListener(new BrowsePanelFileListener(currentDirState));
        
        directoryFileIconPanel.selectionModelProperty().bindBidirectional(directoryFileListPanel.selectionModelProperty());
        
        fileViewStackPane.getChildren().add(directoryFileIconPanel.getNode());

        List<IZoomableNodeProvider> views = new ArrayList<>();
        views.add(directoryFileListPanel);
        views.add(directoryFileIconPanel);
        
        return views;
    }
    
    private void initState(CurrentDirectoryState state) {
        SettingsDAO dao = SettingsDAO.getInstance();
        Settings s = dao.load();
        
        String initialFolderPath = FileUtils.getUserHomeDir().getAbsolutePath();
        if (s.getInitialFolder()!=null)
             initialFolderPath = s.getInitialFolder();
        
        state.setCurrentPath(new File(initialFolderPath));
    }
    
    private void setupMiddleSplitPane() {
        splitPane.setOrientation(Orientation.HORIZONTAL);

        TabPane inspectionsPanels = new TabPane();
        inspectionsPanels.setSide(Side.BOTTOM);
        Tab t1 = new Tab(L(this,"PreviewTab_Label"));
        t1.setClosable(false);
        t1.setContent(previewPanel.getNode());

        Tab t2 = new Tab(L(this,"NotesTab_Label"));
        t2.setClosable(false);
        t2.setContent(notesPanel.getNode());
        
        Tab t3 = new Tab(L(this,"InfoTab_Label"));
        t3.setClosable(false);
        t3.setContent(infoPanel.getNode());

        inspectionsPanels.getTabs().addAll(t1, t2, t3);

        splitPane.getItems().addAll(fileViewStackPane, inspectionsPanels);

        splitPane.setDividerPosition(0, 0.5);
    }
    
    private void setupMainContent() {
        box.getChildren().addAll(currentPathPanel.getNode(),buttonsPane,splitPane,fullTagsPanel.getNode());
    }
        
    @Override
    public Node getNode() {
        return box;
    }
    
}
