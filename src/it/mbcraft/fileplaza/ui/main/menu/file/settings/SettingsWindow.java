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

package it.mbcraft.fileplaza.ui.main.menu.file.settings;

import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.data.models.config.Settings;
import it.mbcraft.fileplaza.i18n.Lang;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractSettingsWindow;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * Creates the SettingsWindow. It contains generic settings for the application.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.file.settings.SettingsWindow")
public class SettingsWindow extends AbstractSettingsWindow {   
    
    private TextField startingPathField;
    private ComboBox languageField;
    
    public SettingsWindow() {
        super(L("main.menu.file.settings.SettingsWindow","Settings_Window"),true);
    }

    @Override
    protected void initMiddleContent() {
        
        addToWindow(ComponentFactory.newPaddingPane(new Label(L(this,"RestartNeeded_Label")),10));
        
        GridPaneFiller.reset(2);
        GridPane content = new GridPane();
        content.setPadding(new Insets(5));
        content.setVgap(25);
        content.setHgap(5);
        content.add(new Label(L(this,"StartingPath_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
        FlowPane pane = new FlowPane();
        
        startingPathField = new TextField();
        startingPathField.setEditable(false);
        startingPathField.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> prop, String oldValue, String newValue) {
                fireDataChanged();
            }
        });
        
        pane.getChildren().add(startingPathField);
        pane.getChildren().add(ComponentFactory.newFolderBrowseButton(L(this,"Browse_Button"), startingPathField, startingPathField));
        
        languageField = new ComboBox();
        languageField.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {    
                fireDataChanged();
            }
        });
        //languages, update as necessary
        languageField.getItems().addAll(Lang.getAllSupportedLanguages());
        

        content.add(pane, GridPaneFiller.X(), GridPaneFiller.Y());
        
        content.add(new Label(L(this,"CurrentLanguage_Label")), GridPaneFiller.X(), GridPaneFiller.Y());
        content.add(languageField, GridPaneFiller.X(), GridPaneFiller.Y());
        
        addToWindow(content);
    }
    
    @Override
    protected void saveData() {
        Settings s = new Settings();
        s.setInitialFolder(startingPathField.getText());
        s.setCurrentLanguage(languageField.getSelectionModel().getSelectedItem().toString());
        
        SettingsDAO.getInstance().save(s);
    }
    
    @Override
    protected void loadData() {
        Settings s = SettingsDAO.getInstance().load();
        
        startingPathField.setText(s.getInitialFolder());
        
        if (s.getCurrentLanguage()==null || s.getCurrentLanguage().equals("")) {
            s.setCurrentLanguage("English");
        } 
        
        languageField.getSelectionModel().select(s.getCurrentLanguage());
        
    }

    @Override
    protected boolean validateBeforeSave() {
        return true;
    }

}
