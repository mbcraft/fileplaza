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

import it.mbcraft.fileplaza.data.models.Dictionary;
import it.mbcraft.fileplaza.data.models.Dictionary.DictionaryEntry;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.AbstractEntityEditorWindow;
import it.mbcraft.fileplaza.ui.common.components.ImprovedListView;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import java.util.Map.Entry;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.file.dictionaries.EditDictionaryWindow")
public class EditDictionaryWindow extends AbstractEntityEditorWindow {

    @Override
    protected boolean validateBeforeSave() {
        fireDataChanged();
        
        String title = titleField.getText();
        String description = descriptionArea.getText();
        
        if (!title.equals("") && !description.equals(""))
            return true;
        
        DialogFactory.showErrorDialog(L(this,"DictionaryError_Dialog"), L(this,"DictionaryError_Text"));
        
        return false;
    }

    private TextField titleField;
    private TextArea descriptionArea;
    private boolean isEnabled;
    private ImprovedListView<Entry<String,String>> entryList;

    private TextField singularField;
    private TextField pluralField;
    private Button addOrSaveButton;
    private Button deleteButton;

    public EditDictionaryWindow(String title) {
        super(title);
    }

    public void setDictionary(Dictionary d) {
        titleField.setText(d.getShortTitle());
        descriptionArea.setText(d.getDescription());
        isEnabled = d.isEnabled();
        entryList.getItems().setAll(d.getEntries());
        viewEntry(null);
    }

    public Dictionary getDictionary() {
        Dictionary d = new Dictionary();
        d.setShortTitle(titleField.getText());
        d.setDescription(descriptionArea.getText());
        d.setEnabled(isEnabled);
        d.getEntries().addAll(entryList.getItems());
        return d;
    }

    private void initTitleAndDescription() {
        GridPane pane = new GridPane();
        GridPaneFiller.reset(2);

        pane.add(new Label(L(this, "Title_Label")), GridPaneFiller.X(), GridPaneFiller.Y());

        titleField = new TextField();
        titleField.setPromptText(L(this, "Title_PromptText"));
        pane.add(titleField, GridPaneFiller.X(), GridPaneFiller.Y());

        pane.add(new Label(L(this, "Description_Label")), GridPaneFiller.X(), GridPaneFiller.Y());

        descriptionArea = new TextArea();
        descriptionArea.setPromptText(L(this, "Description_PromptText"));
        descriptionArea.setWrapText(true);
        pane.add(descriptionArea, GridPaneFiller.X(), GridPaneFiller.Y());

        addToWindow(pane);
    }

    private void initEntryList() {
        entryList = new ImprovedListView<>();
        entryList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        entryList.setCellFactory(new DictionaryEntryListCellFactory(new DictionaryEntryListCellSelectionListener(this)));

        addToWindow(entryList);
    }

    private void initEditBar() {
        FlowPane pane = new FlowPane();
        pane.setHgap(10);

        Label singularLabel = new Label(L(this, "Singular_Label"));
        pane.getChildren().add(singularLabel);

        singularField = new TextField();
        pluralField = new TextField();
        
        singularField.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                pluralField.requestFocus();
            }
        });
        pane.getChildren().add(singularField);

        Label pluralLabel = new Label(L(this, "Plural_Label"));
        pane.getChildren().add(pluralLabel);
        
        
        pluralField.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                saveOrAddEntry();
            }
        });
        pane.getChildren().add(pluralField);

        addOrSaveButton = new Button();
        addOrSaveButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                saveOrAddEntry();
            }
        });
        pane.getChildren().add(addOrSaveButton);

        deleteButton = new Button(L(this, "Delete_Button"));
        deleteButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event t) {
                deleteEntry();
            }
            
        });
        deleteButton.setDisable(true);

        pane.getChildren().add(deleteButton);

        addToWindow(pane);
    }

    public void saveOrAddEntry() {
        String singular = singularField.getText().toLowerCase();
        String plural = pluralField.getText().toLowerCase();
        if (singular != null && !singular.equals("") && plural != null && !plural.equals("")) {
            Entry<String,String> entry = new DictionaryEntry(singular, plural);
            if (entryList.getSelectionModel().isEmpty()) {
                entryList.getItems().add(entry);
                viewEntry(null);
                entryList.scrollTo(entryList.getItems().size()-1);
            } else {
                int index = entryList.getSelectionModel().getSelectedIndex();
                entryList.getItems().remove(index);
                entryList.getItems().add(index, entry);
                viewEntry(null);
            }

        } else {
            DialogFactory.showErrorDialog(L(this, "EntryError_Dialog"), L(this, "EntryError_Text"));
        }
        singularField.requestFocus();

    }
    
    public void deleteEntry() {
        entryList.getItems().remove(entryList.getSelectionModel().getSelectedIndex());
        viewEntry(null);
    }

    @Override
    protected void initMiddleContent() {
        initTitleAndDescription();
        initEntryList();
        initEditBar();

    }

    void viewEntry(DictionaryEntry e) {
        if (e == null) {
            singularField.setText("");
            pluralField.setText("");
            addOrSaveButton.setText(L(this, "Add_Button"));
            deleteButton.setDisable(true);
            entryList.getSelectionModel().clearSelection();
        } else {
            singularField.setText(e.getKey());
            pluralField.setText(e.getValue());
            addOrSaveButton.setText(L(this, "Save_Button"));
            deleteButton.setDisable(false);
        }
    }

}
