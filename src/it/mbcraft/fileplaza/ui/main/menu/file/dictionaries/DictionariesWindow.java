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

package it.mbcraft.fileplaza.ui.main.menu.file.dictionaries;

import it.mbcraft.fileplaza.data.dao.meta.DictionaryDAO;
import it.mbcraft.fileplaza.data.models.Dictionary;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.AbstractEntityEditorWindow;
import it.mbcraft.fileplaza.ui.common.components.AbstractSettingsWindow;
import it.mbcraft.fileplaza.ui.common.components.ImprovedListView;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.io.File;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
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
@LangResource("main.menu.file.dictionaries.DictionariesWindow")
public class DictionariesWindow extends AbstractSettingsWindow {

    private HBox main;
        
    private ImprovedListView<Dictionary> dictionaryList;
    private Button importButton;
    private Button removeButton;
    private Button newButton;
    private Button editButton;
    
    private EditDictionaryWindow editDictionaryWindow;
    
    private TextField dictionaryTitle;
    private TextArea dictionaryDescription;
    private TextField entryCount;
    private Button enableButton;
    private Button disableButton;
    
    public DictionariesWindow() {
        super(L("main.menu.file.dictionaries.DictionariesWindow","Dictionaries_Window"),true);
    }
        
    /**
     * Initializes the main content of the window
     */
    @Override
    protected void initMiddleContent() {
        
        main = new HBox();
        main.setPadding(new Insets(5));
        
        editDictionaryWindow = new EditDictionaryWindow("---");
        
        initDictionaryListPanel();
        initDictionaryViewPanel();
        
        addToWindow(main);
                
    }
        
    private void initDictionaryListPanel() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(5));
        
        Label l = new Label(L(this,"LoadedDictionaries_Label"));       
        vbox.getChildren().add(ComponentFactory.newPaddingPane(l, 5));
        
        dictionaryList = new ImprovedListView();
        //load with dictionaries and provide a cell factory
        dictionaryList.setCellFactory(new DictionaryListCellFactory(new DictionaryListCellSelectionListener(this)));
        dictionaryList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        vbox.getChildren().add(ComponentFactory.newPaddingPane(dictionaryList,5));
        
        FlowPane buttons = new FlowPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setHgap(5);
        buttons.setPadding(new Insets(5));
        importButton = new Button(L(this,"Import_Button"));
        importButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                importDictionaryFromFile();            
            }
        });
        
        newButton = new Button(L(this,"New_Button"));
        newButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                newDictionary();
            }
        });
        
        editButton = new Button(L(this,"Edit_Button"));
        editButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                editDictionary();
            }
        });
        
        removeButton = new Button(L(this,"Remove_Button"));
        removeButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                deleteDictionary();
            }
            
        });
        buttons.getChildren().addAll(importButton,newButton,editButton,removeButton);
        
        vbox.getChildren().add(buttons);
        
        //add to main pane
        main.getChildren().add(vbox);
    }
    
    private void initDictionaryViewPanel() {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(5));
        
        Label l = new Label(L(this,"SelectedDictionary_Label"));
        vbox.getChildren().add(ComponentFactory.newPaddingPane(l,5));
        
        GridPane dicViewGrid = new GridPane();
        dicViewGrid.setHgap(5);
        dicViewGrid.setVgap(5);
        
        GridPaneFiller.reset(2);
        dicViewGrid.add(new Label(L(this,"Title_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
        dictionaryTitle = new TextField();
        dictionaryTitle.setEditable(false);
        dicViewGrid.add(dictionaryTitle, GridPaneFiller.X(), GridPaneFiller.Y());
        dicViewGrid.add(new Label(L(this,"Description_Label")),GridPaneFiller.X(),GridPaneFiller.Y());
        dictionaryDescription = new TextArea();
        dictionaryDescription.setEditable(false);
        dictionaryDescription.setWrapText(true);
        dicViewGrid.add(dictionaryDescription, GridPaneFiller.X(), GridPaneFiller.Y());
    
        dicViewGrid.add(new Label(L(this,"EntryCount_Label")),GridPaneFiller.X(),GridPaneFiller.Y());
        
        entryCount = new TextField();
        entryCount.setEditable(false);
        dicViewGrid.add(entryCount,GridPaneFiller.X(),GridPaneFiller.Y());
        
        vbox.getChildren().add(ComponentFactory.newPaddingPane(dicViewGrid,5));
        
        FlowPane buttons = new FlowPane();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(5));
        buttons.setHgap(5);
        
        enableButton = new Button(L(this,"Enable_Button"));
        enableButton.setDisable(true);
        enableButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                enableDictionary();
            }
            
        });
        buttons.getChildren().add(enableButton);
        
        disableButton = new Button(L(this,"Disable_Button"));
        disableButton.setDisable(true);
        disableButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event t) {
                disableDictionary();
            }
        
        });
        buttons.getChildren().add(disableButton);
        
        vbox.getChildren().add(buttons);
        
        main.getChildren().add(vbox);
    }
    
    private void importDictionaryFromFile() {
        FileChooser chooser = new FileChooser();
        File result = chooser.showOpenDialog(WindowStack.top());
        if (result!=null) {
            Dictionary d = DictionaryDAO.getInstance().importFromFile(result);
            dictionaryList.getItems().add(d);        
            dictionaryList.getSelectionModel().select(d);
            viewDictionary(null);
            fireDataChanged();
        }

    }
    
    private void newDictionary() {
        editDictionaryWindow.setDictionary(new Dictionary());
        editDictionaryWindow.setMode(AbstractEntityEditorWindow.Mode.NEW);
        editDictionaryWindow.showAndWait();
        if (editDictionaryWindow.needsSave()) {
            Dictionary d = editDictionaryWindow.getDictionary();
            dictionaryList.getItems().add(d);
            dictionaryList.getSelectionModel().selectLast();
            viewDictionary(d);
            fireDataChanged();
        }
    }
    
    private void editDictionary() {
        editDictionaryWindow.setDictionary(dictionaryList.getSelectionModel().getSelectedItem());
        editDictionaryWindow.setMode(AbstractEntityEditorWindow.Mode.EDIT);   
        editDictionaryWindow.showAndWait();
        if (editDictionaryWindow.needsSave()) {
            int selectedIndex = dictionaryList.getSelectionModel().getSelectedIndex();
            dictionaryList.getItems().remove(selectedIndex);
            Dictionary d = editDictionaryWindow.getDictionary();
            dictionaryList.getItems().add(selectedIndex, d);
            dictionaryList.getSelectionModel().select(selectedIndex);
            viewDictionary(d);
            fireDataChanged();
        } 
    }
    
    private void deleteDictionary() {
        Dictionary dict = dictionaryList.getSelectionModel().getSelectedItem();
        viewDictionary(null);
        dictionaryList.getItems().remove(dict);
        fireDataChanged();
    }
    
    private void disableDictionary() {
        Dictionary dict = dictionaryList.getSelectionModel().getSelectedItem();
        dict.setEnabled(false);
        dictionaryList.refreshSelectedItem();
        viewDictionary(dict);
        fireDataChanged();
    }
    
    private void enableDictionary() {
        Dictionary dict = dictionaryList.getSelectionModel().getSelectedItem();
        dict.setEnabled(true);
        dictionaryList.refreshSelectedItem();
        viewDictionary(dict);
        fireDataChanged();
    }
        
    @Override
    protected void loadData() {
        viewDictionary(null);
        dictionaryList.getItems().clear();
        dictionaryList.getItems().addAll(DictionaryDAO.getInstance().findAll());
    }
    
    public void viewDictionary(Dictionary d) {
        if (d==null) {
            dictionaryList.getSelectionModel().clearSelection();
            editButton.setDisable(true);
            removeButton.setDisable(true);
            dictionaryTitle.setText(null);
            dictionaryDescription.setText(null);
            entryCount.setText(null);
            enableButton.setDisable(true);
            disableButton.setDisable(true);
        } else {
            dictionaryList.getSelectionModel().select(d);
            editButton.setDisable(false);
            removeButton.setDisable(false);
            dictionaryTitle.setText(d.getShortTitle());
            dictionaryDescription.setText(d.getDescription());
            entryCount.setText(""+d.getEntries().size());
            enableButton.setDisable(d.isEnabled());
            disableButton.setDisable(!d.isEnabled());
        }
    }

    @Override
    protected void saveData() {
        DictionaryDAO.getInstance().replaceAll(dictionaryList.getItems());
    }

    @Override
    protected boolean validateBeforeSave() {
        return true;
    }
}
