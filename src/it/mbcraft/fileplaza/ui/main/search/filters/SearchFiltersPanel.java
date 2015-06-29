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

package it.mbcraft.fileplaza.ui.main.search.filters;

import it.mbcraft.fileplaza.algorithm.search.FileSystemElementSearch;
import it.mbcraft.fileplaza.data.models.Priority;
import it.mbcraft.fileplaza.data.models.Tag;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.panels.tags.generic.FullTagsPanel;
import it.mbcraft.fileplaza.state.SearchState;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import java.io.File;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * Creates a SearchFilterPanel for showing filters for search panel.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.search.filters.SearchFiltersPanel")
public class SearchFiltersPanel implements INodeProvider {
    
    public VBox box = new VBox();
    
    private final ToggleGroup searchTypeGroup = new ToggleGroup();
    private ToggleButton filesToggle;
    private ToggleButton foldersToggle;
    private ToggleButton bothToggle;
    private final ListProperty<Tag> tagList = new SimpleListProperty(FXCollections.observableArrayList());
    
    private final TextField withinFolderField = new TextField();
    
    private final ComboBox<String> priority = new ComboBox();
    
    private Button searchButton;
    private Button clearSearchResultsButton;
    
    private final SearchState myState;
    
    /**
     * Creates a SearchFilterPanel. Needs SearchState object to update
     * the search state.
     * 
     * @param state The SearchState to update.
     */
    public SearchFiltersPanel(SearchState state) {        
        
        myState = state;
        
        initContainer();
        initComponents();
        setupOptionsPanel();
        setupButtonsPanel();
    }
    
    private void initContainer() {
        box.setPadding(new Insets(5));
        box.setAlignment(Pos.TOP_LEFT);
        box.setSpacing(0);
    }
    
    private void initComponents() {
        initSearchTypeSelector();
        initPrioritySelector();
    }
    
    private void initSearchTypeSelector() {
        //items initialized in methods to allow localization
        filesToggle = new ToggleButton(L(this,"Files_Toggle"));
        foldersToggle = new ToggleButton(L(this,"Folders_Toggle"));
        bothToggle = new ToggleButton(L(this,"Both_Toggle"));
        
        searchTypeGroup.getToggles().addAll(filesToggle,foldersToggle,bothToggle);
        
        //selects "Files" option as default
        searchTypeGroup.selectToggle(filesToggle);
    }
    
    private void initPrioritySelector() {
       //items initialized in methods to allow localization
       priority.getItems().add(L(this,"IgnorePriority_Choice")); 
       priority.getItems().add(L(this,"LowestPriority_Choice"));
       priority.getItems().add(L(this,"LowPriority_Choice"));
       priority.getItems().add(L(this,"MediumPriority_Choice"));
       priority.getItems().add(L(this,"HighPriority_Choice"));
       priority.getItems().add(L(this,"HighestPriority_Choice"));
       
       priority.getSelectionModel().selectFirst();
    }
    
    private void setupOptionsPanel() {
        GridPane optionsPane = new GridPane();
        optionsPane.setHgap(10);
        optionsPane.setVgap(5);
        optionsPane.setAlignment(Pos.TOP_LEFT);
        
        GridPaneFiller.reset(2);
        
        //search type
        Label l1 = new Label(L(this,"SearchType_Label"));
        optionsPane.add(ComponentFactory.newRightPaddingPane(l1,0), GridPaneFiller.X(), GridPaneFiller.Y());
        FlowPane toggleOptionsPane = new FlowPane();
        l1.setLabelFor(toggleOptionsPane);
        toggleOptionsPane.setHgap(10);
        toggleOptionsPane.getChildren().addAll(filesToggle,foldersToggle,bothToggle);
        optionsPane.add(toggleOptionsPane,GridPaneFiller.X(),GridPaneFiller.Y());
        
        //within folder
        Label l2 = new Label(L(this,"WithinFolder_Label"));        
        optionsPane.add(ComponentFactory.newRightPaddingPane(l2,0), GridPaneFiller.X(), GridPaneFiller.Y());
        FlowPane withinFolderPane = new FlowPane();
        withinFolderPane.setHgap(10);
        withinFolderField.setEditable(false);
        withinFolderField.setText(null);
        withinFolderField.setDisable(true);
        Button browseButton = ComponentFactory.newFolderBrowseButton(L(this,"Browse_Button"), withinFolderField, withinFolderField);
        l2.setLabelFor(withinFolderPane);
        
        Button pathClearButton = ComponentFactory.newClearFieldButton(L(this,"Clear_Button"), withinFolderField, withinFolderField);
        
        withinFolderPane.getChildren().addAll(withinFolderField,browseButton,pathClearButton);
        optionsPane.add(withinFolderPane, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //with priority
        Label l3 = new Label(L(this,"Priority_Label"));
        l3.setLabelFor(priority);
        optionsPane.add(ComponentFactory.newRightPaddingPane(l3,0),GridPaneFiller.X(),GridPaneFiller.Y());
        optionsPane.add(priority,GridPaneFiller.X(),GridPaneFiller.Y());
        //and tags   
        Group g = new Group();
        g.getChildren().add(optionsPane);
        
        box.getChildren().add(g);
        
        FullTagsPanel fullTags = new FullTagsPanel(L(this,"Tags_Label"),true,tagList);
        box.getChildren().add(fullTags.getNode());
        
    }
    
    private Priority getCurrentPriority() {
        switch (priority.getSelectionModel().getSelectedIndex()) {
            case 0 : return Priority.NONE;
            case 1 : return Priority.LOWEST;
            case 2 : return Priority.LOW;
            case 3 : return Priority.MEDIUM;
            case 4 : return Priority.HIGH;
            case 5 : return Priority.HIGHEST;
            default : throw new IllegalStateException("Invalid value readed from combobox.");
        }
    }
    
    private void setupButtonsPanel() {
        FlowPane buttonsPane = new FlowPane();
        buttonsPane.setHgap(10);
        buttonsPane.setAlignment(Pos.TOP_CENTER);
        
        searchButton = new Button(L(this,"IncrementalSearch_Button"));
        searchButton.setDefaultButton(true);
        searchButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent ev) {
                FileSystemElementSearch search = new FileSystemElementSearch();
                //type
                if (searchTypeGroup.getSelectedToggle()==filesToggle)
                    search.setType(FileSystemElementSearch.Type.FILES);
                if (searchTypeGroup.getSelectedToggle()==foldersToggle)
                    search.setType(FileSystemElementSearch.Type.FOLDERS);
                if (searchTypeGroup.getSelectedToggle()==bothToggle)
                    search.setType(FileSystemElementSearch.Type.BOTH);
                //within folder
                search.setWithinFolder((File) withinFolderField.getUserData());
                //priority
                search.setFromPriority(getCurrentPriority());
                //tags
                for (Tag t : tagList) {
                    search.addTag(t);
                }
                //execute and update results
                myState.addResultList(search.execute());
            }
            
        });
        
        clearSearchResultsButton = new Button(L(this,"Clear_Button"));
        clearSearchResultsButton.setDefaultButton(false);
        clearSearchResultsButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                myState.clear();
            }
        });
        
        buttonsPane.getChildren().addAll(searchButton,clearSearchResultsButton);
        
        box.getChildren().add(buttonsPane);
    }

    @Override
    public Node getNode() {
        return box;
    }


}
