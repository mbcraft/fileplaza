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

import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.data.models.config.Settings;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.state.SingleSelectionFileSystemElementState;
import it.mbcraft.fileplaza.ui.panels.info.FileInfoPanel;
import it.mbcraft.fileplaza.ui.panels.notes.NotesPanel;
import it.mbcraft.fileplaza.ui.panels.preview.PreviewPanel;
import it.mbcraft.fileplaza.ui.panels.tags.generic.FullTagsPanel;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.panels.files.FileBrowser;
import it.mbcraft.fileplaza.utils.FileUtils;
import java.io.File;
import javafx.geometry.Orientation;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * TODO : define what this component does.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class BrowsePanel implements INodeProvider {
    
    private final FileBrowser browserPane;
    
    private final SplitPane splitPane = new SplitPane();
    
    private final PreviewPanel previewPanel;
    private final NotesPanel notesPanel;
    private final FileInfoPanel infoPanel;
    private final FullTagsPanel fullTagsPanel;    
    
    public BrowsePanel() {
        
        //selected file
        SingleSelectionFileSystemElementState currentFileSystemElementState = SingleSelectionFileSystemElementState.getInstance();
        
        browserPane = new FileBrowser();
        
        previewPanel = new PreviewPanel(currentFileSystemElementState.previewDataProperty());
        notesPanel = new NotesPanel(browserPane.getCurrentDirectoryState().selectedFileProperty(),currentFileSystemElementState.notesProperty());
        infoPanel = new FileInfoPanel(browserPane.getCurrentDirectoryState().selectedFileProperty());
        fullTagsPanel = new FullTagsPanel(L(this,"TagsPanel_Label"),false,currentFileSystemElementState.tagListProperty());
        
        setupMiddleSplitPane();
        
        initState(browserPane.getCurrentDirectoryState());
    }
    
    private void initState(CurrentDirectoryState state) {
        SettingsDAO dao = SettingsDAO.getInstance();
        Settings s = dao.load();
        
        String initialFolderPath = FileUtils.getUserHomeDir().getAbsolutePath();
        if (s.getInitialFolder()!=null)
             initialFolderPath = s.getInitialFolder();
        
        state.setCurrentPath(new File(initialFolderPath));
        //System.out.println("Initial folder path set to : "+initialFolderPath);
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

        Tab t4 = new Tab(L(this,"TagsTab_Label"));
        t4.setClosable(false);
        t4.setContent(fullTagsPanel.getNode());
        
        inspectionsPanels.getTabs().addAll(t1, t2, t3, t4);

        splitPane.getItems().addAll(browserPane.getNode(), inspectionsPanels);

        splitPane.setDividerPosition(0, 0.5);
    }
            
    @Override
    public Node getNode() {
        return splitPane;
    }
    
}
