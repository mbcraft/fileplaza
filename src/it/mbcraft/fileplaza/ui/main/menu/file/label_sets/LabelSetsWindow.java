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

package it.mbcraft.fileplaza.ui.main.menu.file.label_sets;

import it.mbcraft.fileplaza.data.dao.meta.LabelSetDAO;
import it.mbcraft.fileplaza.data.models.LabelSet;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractSettingsWindow;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.file.label_sets.LabelSetsWindow")
public class LabelSetsWindow extends AbstractSettingsWindow {
    
    private HBox main;
    
    private ListView<LabelSet> labelSetsList;
    private Button importButton;
    private Button removeButton;
    
    private TextField enumTitle;
    private TextField setName;
    private ListView<String> setLabels;
    
    private Button enableButton;
    private Button disableButton;
    
    
    
    public LabelSetsWindow() {
        super(L("main.menu.file.label_sets.LabelSetsWindow","LabelSets_Window"),true);
    }
        
    /**
     * Initializes the main content of the window
     */
    @Override
    protected void initMiddleContent() {
        
        main = new HBox();
        main.setPadding(new Insets(5));
        
        initUserDefinedEnumListPanel();
        initLabelSetEditPanel();
        
        addToWindow(main);
                
    }
    
    
    private void initUserDefinedEnumListPanel() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(5));
        
        Label l = new Label(L(this,"LoadedLabelSets_Label"));       
        vbox.getChildren().add(ComponentFactory.newPaddingPane(l, 5));
        
        labelSetsList = new ListView();
        labelSetsList.setCellFactory(new LabelSetListCellFactory(new LabelSetListCellSelectionListener(this)));
        labelSetsList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        //load with dictionaries and provide a cell factory
        
        vbox.getChildren().add(ComponentFactory.newPaddingPane(labelSetsList,5));
        
        FlowPane buttons = new FlowPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(5);
        buttons.setPadding(new Insets(5));
        importButton = new Button(L(this,"Import_Button"));
        importButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                FileChooser chooser = new FileChooser();
                File result = chooser.showOpenDialog(WindowStack.top());
                if (result!=null) {
                    importLabelSetFromFile(result);
                }
            }
        });
        
        removeButton = new Button(L(this,"Remove_Button"));
        removeButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                
                LabelSet en = labelSetsList.getSelectionModel().getSelectedItem();               
                if (DialogFactory.newConfirmDialog(L(this,"Confirm_Title"), L(this,"Confirm_Text"))) {
                    deleteLabelSet(en);
                }
            }
        });
        buttons.getChildren().addAll(importButton,removeButton);
        
        vbox.getChildren().add(buttons);
        
        //add to main pane
        main.getChildren().add(vbox);
    }
    
    private void initLabelSetEditPanel() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5));
        
        Label l = new Label(L(this,"SelectedLabelSet_Label"));
        vbox.getChildren().add(ComponentFactory.newPaddingPane(l,5));
        
        GridPane dicViewGrid = new GridPane();
        dicViewGrid.setHgap(5);
        dicViewGrid.setVgap(5);
        
        GridPaneFiller.reset(2);
        dicViewGrid.add(new Label(L(this,"Title_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
        enumTitle = new TextField();
        enumTitle.setEditable(true);
        dicViewGrid.add(enumTitle, GridPaneFiller.X(), GridPaneFiller.Y());
        dicViewGrid.add(new Label(L(this,"SetName_Label")),GridPaneFiller.X(),GridPaneFiller.Y());
        setName = new TextField();
        setName.setEditable(true);
        dicViewGrid.add(setName, GridPaneFiller.X(), GridPaneFiller.Y());
        dicViewGrid.add(new Label(L(this,"Values_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
        setLabels = new ListView();
        dicViewGrid.add(setLabels, GridPaneFiller.X(),GridPaneFiller.Y());
        
        vbox.getChildren().add(ComponentFactory.newPaddingPane(dicViewGrid,5));
        
        FlowPane buttons = new FlowPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(5));
        buttons.setHgap(5);
        
        enableButton = new Button(L(this,"Enable_Button"));
        enableButton.setDisable(true);
        enableButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                enableLabelSet(labelSetsList.getSelectionModel().getSelectedItem());
            }
        });
        buttons.getChildren().add(enableButton);
        
        disableButton = new Button(L(this,"Disable_Button"));
        disableButton.setDisable(true);
        disableButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                disableLabelSet(labelSetsList.getSelectionModel().getSelectedItem());
            }
            
        });
        buttons.getChildren().add(disableButton);
        
        vbox.getChildren().add(buttons);
        
        main.getChildren().add(vbox);
    }
    
    private void importLabelSetFromFile(File f) {
        viewLabelSet(null);
        LabelSet d = LabelSetDAO.getInstance().importFromFile(f);
        labelSetsList.getItems().add(d);        
        fireDataChanged();
        viewLabelSet(d);
    }
    
    private void deleteLabelSet(LabelSet en) {
        labelSetsList.getItems().remove(en);
        fireDataChanged();
        viewLabelSet(null);
        
    }
    
    private void disableLabelSet(LabelSet en) {
        en.setEnabled(false);
        fireDataChanged();
        viewLabelSet(en);
    }
    
    private void enableLabelSet(LabelSet en) {
        en.setEnabled(true);
        fireDataChanged();
        viewLabelSet(en);
    }
    
    @Override
    protected void saveData() {
        LabelSetDAO.getInstance().replaceAll(labelSetsList.getItems());
    }
    
    @Override
    protected void loadData() {
        viewLabelSet(null);
        labelSetsList.getItems().clear();
        labelSetsList.getItems().addAll(LabelSetDAO.getInstance().findAll());        
    }
    
    public void viewLabelSet(LabelSet ls) {
        if (ls==null) {
            labelSetsList.getSelectionModel().clearSelection();
            removeButton.setDisable(true);
            enumTitle.setText(null);
            setName.setText(null);
            setLabels.getItems().clear();
            enableButton.setDisable(true);
            disableButton.setDisable(true);
        } else {
            labelSetsList.getSelectionModel().select(ls);
            removeButton.setDisable(false);
            enumTitle.setText(ls.getShortTitle());
            setName.setText(ls.getSetName());
            setLabels.getItems().clear();
            setLabels.getItems().addAll(ls.getLabels().keySet());
            enableButton.setDisable(ls.isEnabled());
            disableButton.setDisable(!ls.isEnabled());
        }
    }

    @Override
    protected boolean validateBeforeSave() {
        throw new IllegalStateException("Must be checked");
    }
    
}
