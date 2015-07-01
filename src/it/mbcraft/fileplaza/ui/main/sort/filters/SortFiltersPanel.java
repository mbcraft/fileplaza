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

package it.mbcraft.fileplaza.ui.main.sort.filters;

import it.mbcraft.fileplaza.algorithm.sort.FileElementSort;
import it.mbcraft.fileplaza.algorithm.sort.SortOptions;
import it.mbcraft.fileplaza.data.dao.fs.FileElementDAO;
import it.mbcraft.fileplaza.data.dao.fs.FolderElementDAO;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Builds a panel containing the filters to be set for sorting out
 * files.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.sort.filters.SortFiltersPanel")
public class SortFiltersPanel implements INodeProvider {

    private final VBox box = new VBox();
    
    private final ToggleGroup fileCollectMode = new ToggleGroup();
    private ToggleButton allTagged;
    private ToggleButton newlyTagged;
    
    private TextField sourceFolder;
    private TextField targetFolder;
    
    private Button preview;
    private Button clear;
    
    private final ObjectProperty<FileElementSort> currentSort;
    
    /**
     * Build a sort filters panel. Needs a FileElementSort as an ObjectProperty, for
     * actually previewing and clearing the sort.
     * 
     * @param currentSortProperty 
     */
    public SortFiltersPanel(ObjectProperty<FileElementSort> currentSortProperty) {
        
        currentSort = currentSortProperty;
        initContainer();
        initFilters();
        initButtons();
    }
    
    private void initContainer() {
        box.setPadding(new Insets(5));
        box.setAlignment(Pos.TOP_LEFT);
    }
    
    private void initToggles(GridPane pane) {
        pane.add(new Label(L(this,"Filter_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
        
        newlyTagged = new ToggleButton(L(this,"OnlyNew_Toggle"));
        allTagged = new ToggleButton(L(this,"All_Toggle"));
        fileCollectMode.getToggles().addAll(newlyTagged,allTagged);
        newlyTagged.setSelected(true);
        
        FlowPane togglePane = new FlowPane();
        togglePane.setHgap(10);
        togglePane.getChildren().addAll(newlyTagged,allTagged);
        
        pane.add(togglePane,GridPaneFiller.X(),GridPaneFiller.Y());

    }
    
    private void initSourceTargetFolders(GridPane pane) {
        pane.add(new Label(L(this,"SourceFolder_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
        
        sourceFolder = new TextField();
        sourceFolder.setDisable(true);
        
        Button sourceBrowseButton = ComponentFactory.newFolderBrowseButton(L(this,"SourceBrowse_Button"), sourceFolder, sourceFolder);
        
        Button sourceClearButton = ComponentFactory.newClearFieldButton(L(this,"SourceClear_Button"), sourceFolder, sourceFolder);
        
        FlowPane sourceBrowsePane = new FlowPane();
        sourceBrowsePane.setHgap(10);
        sourceBrowsePane.getChildren().addAll(sourceFolder,sourceBrowseButton,sourceClearButton);
        pane.add(sourceBrowsePane,GridPaneFiller.X(),GridPaneFiller.Y());
        
        pane.add(new Label(L(this,"TargetFolder_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
    
        
        targetFolder = new TextField();
        targetFolder.setDisable(true);

        Button targetBrowseButton = ComponentFactory.newFolderBrowseButton(L(this,"TargetBrowse_Button"), targetFolder, targetFolder);
        
        Button targetClearButton = ComponentFactory.newClearFieldButton(L(this,"TargetClear_Button"), targetFolder, targetFolder);
        
        FlowPane targetBrowsePane = new FlowPane();
        targetBrowsePane.setHgap(10);
        targetBrowsePane.getChildren().addAll(targetFolder,targetBrowseButton,targetClearButton);
        
        pane.add(targetBrowsePane, GridPaneFiller.X(), GridPaneFiller.Y());
        
    }
    
    private void initFilters() {
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.TOP_LEFT);
        pane.setHgap(10);
        pane.setVgap(5);
        
        GridPaneFiller.reset(2);
        
        initToggles(pane);
                
        initSourceTargetFolders(pane);
        
        box.getChildren().add(pane);
    }
    
    private void previewButtonPressed() {
        SortOptions options = new SortOptions();
        options.setSortType(getSortType());
        options.setSourceRootFolder(getSourceFolder());
        options.setTargetRootFolder(getTargetFolder());
        FileElementSort sort = new FileElementSort(FolderElementDAO.getInstance(),FileElementDAO.getInstance(),options);
        sort.preview();
        if (sort.hasResults())
            currentSort.set(sort);
        else {
            DialogFactory.showInformationDialog(L(SortFiltersPanel.class,"NoElementsFound_Dialog"), L(SortFiltersPanel.class,"NoElementsFound_Text"));    
        }
    }
    
    private void clearButtonPressed() {
        currentSort.set(null);
    }
    
    private void initButtons() {
        FlowPane buttons = new FlowPane();
        buttons.setHgap(10);
        buttons.setAlignment(Pos.CENTER);

        preview = new Button(L(this,"Preview_Button"));
        preview.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                previewButtonPressed();
            }
        });
        
        clear = new Button(L(this,"Clear_Button"));
        clear.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                clearButtonPressed();
            }
        });
        clear.setDisable(true);
        
        currentSort.addListener(new ChangeListener<FileElementSort>(){

            @Override
            public void changed(ObservableValue<? extends FileElementSort> ov, FileElementSort oldValue, FileElementSort newValue) {
                if (newValue!=null) {
                    preview.setDisable(true);
                    clear.setDisable(false);
                } else {
                    preview.setDisable(false);
                    clear.setDisable(true);
                }
            }
            
        });
        
        buttons.getChildren().addAll(preview,clear);
        
        box.getChildren().add(buttons);
    }
    
    private SortOptions.SortType getSortType() {
        if (fileCollectMode.getSelectedToggle()==allTagged)
            return SortOptions.SortType.ALL_TAGGED;
        else
            return SortOptions.SortType.NEWLY_TAGGED;
    }
    
    private File getSourceFolder() {
        if (sourceFolder.getText()!=null && !sourceFolder.getText().equals(""))
            return new File(sourceFolder.getText());
        else return null;
    }
    
    private File getTargetFolder() {
        if (targetFolder.getText()!=null && !targetFolder.getText().equals(""))
            return new File(targetFolder.getText());
        else return null;
    }
    
    
    @Override
    public Node getNode() {
        return box;
    }
    
}
