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
package it.mbcraft.fileplaza.ui.main.menu.file.dictionaries;

import it.mbcraft.fileplaza.data.models.Dictionary;
import it.mbcraft.fileplaza.data.models.Dictionary.DictionaryEntry;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractEntityEditorWindow;
import it.mbcraft.fileplaza.ui.common.components.listview.ImprovedListView;
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
 * Creates the window for editing a dictionary.
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

    /**
     * Creates the EditDictionaryWindow. Requires a localized title.
     * 
     * 
     * @param title The title of the window
     */
    public EditDictionaryWindow(String title) {
        super(title);
    }

    /**
     * Sets the dictionary to edit.
     * 
     * @param d A Dictionary instance
     */
    public void setDictionary(Dictionary d) {
        titleField.setText(d.getShortTitle());
        descriptionArea.setText(d.getDescription());
        isEnabled = d.isEnabled();
        entryList.getItems().setAll(d.getEntries());
        viewEntry(null);
    }

    /**
     * Gets the modified dictionary
     * 
     * @return A Dictionary instance
     */
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

    /**
     * Saves or adds an entry to the current dictionary being edited.
     */
    private void saveOrAddEntry() {
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
    
    /**
     * Deletes the selected entry from the dictionary.
     */
    private void deleteEntry() {
        entryList.getItems().remove(entryList.getSelectionModel().getSelectedIndex());
        viewEntry(null);
    }

    @Override
    protected void initMiddleContent() {
        initTitleAndDescription();
        initEntryList();
        initEditBar();

    }

    /**
     * Updates the edit fields with the entry.
     * 
     * @param e The DictionaryEntry to show in the edit fields
     */
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
