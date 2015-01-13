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
import it.mbcraft.fileplaza.utils.FileUtils;
import java.io.File;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class BrowsePanel implements INodeProvider {

    private final VBox box = new VBox();
    
    private final CurrentPathPanel currentPathPanel;
    private final FileCommandsPanel fileCommandsPanel;
    
    private final DirectoryFileListPanel directoryFileListPanel;
    private final SplitPane splitPane = new SplitPane();
    private final PreviewPanel previewPanel;
    private final NotesPanel notesPanel;
    private final InfoPanel infoPanel;
    
    private final FullTagsPanel fullTagsPanel;
    
    public BrowsePanel() {
        
        CurrentDirectoryState currentDirState = new CurrentDirectoryState();
        SingleSelectionFileSystemElementState currentFileSystemElementState = SingleSelectionFileSystemElementState.getInstance();
        
        currentPathPanel = new CurrentPathPanel(currentDirState);
        directoryFileListPanel = new DirectoryFileListPanel();
        fileCommandsPanel = new FileCommandsPanel(currentDirState,directoryFileListPanel);
        directoryFileListPanel.setCellListener(new BrowsePanelFileListListener(currentDirState));
        currentDirState.linkWithFileList(directoryFileListPanel);
        currentFileSystemElementState.linkWithFileProperty(directoryFileListPanel.selectionModelProperty().get().selectedItemProperty());
        
        previewPanel = new PreviewPanel(currentFileSystemElementState.previewDataProperty());
        notesPanel = new NotesPanel(currentDirState.currentSelectedFileProperty(),currentFileSystemElementState.notesProperty());
        infoPanel = new InfoPanel(currentDirState.currentSelectedFileProperty());
        
        fullTagsPanel = new FullTagsPanel(L(this,"TagsPanel_Label"),false,currentFileSystemElementState.tagListProperty());
    
        setupMiddleSplitPane();
        
        initState(currentDirState);
        
        setupMainContent();  
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

        TabPane pane = new TabPane();
        pane.setSide(Side.BOTTOM);
        Tab t1 = new Tab(L(this,"PreviewTab_Label"));
        t1.setClosable(false);
        t1.setContent(previewPanel.getNode());

        Tab t2 = new Tab(L(this,"NotesTab_Label"));
        t2.setClosable(false);
        t2.setContent(notesPanel.getNode());
        
        Tab t3 = new Tab(L(this,"InfoTab_Label"));
        t3.setClosable(false);
        t3.setContent(infoPanel.getNode());

        pane.getTabs().addAll(t1, t2, t3);

        splitPane.getItems().addAll(directoryFileListPanel.getNode(), pane);

        splitPane.setDividerPosition(0, 0.5);
    }
    
    private void setupMainContent() {
        box.getChildren().addAll(currentPathPanel.getNode(),fileCommandsPanel.getNode(),splitPane,fullTagsPanel.getNode());
    }
        
    @Override
    public Node getNode() {
        return box;
    }
    
}
