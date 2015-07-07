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

package it.mbcraft.fileplaza.ui.common.components.windows;

import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Common helper class for settings window. It features
 * an 'Ok', 'Cancel' and an optional 'Apply' button.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("common.AbstractSettingsWindow")
public abstract class AbstractSettingsWindow implements IWindow {
    
    private Stage window;
    private VBox box;
    
    private final boolean showApply;
    private boolean dataChanged = false;
    private Button applyButton;
    
    /**
     * Creates a window instance.
     * 
     * @param title The localized window title
     * @param showApplyButton true if the apply button must be shown, false otherwise.
     */
    public AbstractSettingsWindow(String title, boolean showApplyButton) {
        showApply = showApplyButton;
        
        initialize();
        
        window.setTitle(title);
    }
    
    private void initialize() {
        initWindow();
        box.setSpacing(10);
        box.setPadding(new Insets(5));
        initMiddleContent();
        initButtonPane();
    }
    
    /**
     * Sets the title of this window.
     * 
     * @param title The new title for this window.
     */
    public void setWindowTitle(String title) {
        window.setTitle(title);
    }
    
    /**
     * Gets the title of this window.
     * 
     * @return The title as a string
     */
    public String getWindowTitle() {
        return window.getTitle();
    }
    
    /**
     * Initializes the middle content of this window.
     * Must be implemented by subclasses. Inside this method
     * all the window content must be created and added to the window using 
     * methods of this class.
     */
    protected abstract void initMiddleContent();
    
    /**
     * Adds a node to the main window body. The nodes are stacked 
     * vertically.
     * 
     * @param n The node to add.
     */
    protected void addToWindow(Node n) {
        box.getChildren().add(n);
    }
    
    private void initWindow() {
        window = new Stage();
        
        box = new VBox();
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box);
        window.setScene(scene);
        window.setResizable(false);
        window.initModality(Modality.WINDOW_MODAL);
        window.initOwner(WindowStack.top());
    }
    
    private void okButtonPressed() {
        if (validateBeforeSave()) {
            saveDataImpl();
            window.hide();
        }
    }
    
    private void applyButtonPressed() {
        if (validateBeforeSave()) 
            saveDataImpl();
    }
    
    private void initButtonPane() {
        
        Button okButton = new Button(L(AbstractSettingsWindow.class,"Ok_Button"));
        okButton.setDefaultButton(true);
        okButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                okButtonPressed();
            }
        });
        
        applyButton = new Button(L(AbstractSettingsWindow.class,"Apply_Button"));
        if (showApply) {
            applyButton.setOnAction(new EventHandler(){

                @Override
                public void handle(Event t) {
                    applyButtonPressed();
                }
            });
            applyButton.setDisable(true);
        }
        
        Button cancelButton = new Button(L(AbstractSettingsWindow.class,"Cancel_Button"));
        cancelButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event t) {
                window.hide();
            }
            
        });
        
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.getChildren().add(okButton);
        if (showApply) 
            pane.getChildren().add(applyButton);
        pane.getChildren().add(cancelButton);
        
        pane.setAlignment(Pos.CENTER_RIGHT);
        
        box.getChildren().add(pane);
    }
    
    /**
     * This method must load all the data for this window inside
     * the window components.
     * This method is called before the window is actually shown.
     * 
     */
    protected abstract void loadData();
    
    /**
     * This method must persist all the data for this window.
     * It is called if the 'Ok' or 'Apply' button are pressed.
     * It should read data from the components inside the window and save
     * the settings inside a (permanent) storage.
     */
    protected abstract void saveData();
    
    /**
     * In order to enable the 'Apply' button
     * only when the data is changed this method must be called whenever
     * data is changed, in order to enable the 'Apply' button only
     * when changes to be saved occurs.
     */
    protected final void fireDataChanged() {
        dataChanged = true;
        applyButton.setDisable(false);
    }
    
    /**
     * This method loads the data for this window.
     */
    private void loadDataImpl() {
        loadData();
        dataChanged = false;
        applyButton.setDisable(true);
    }
    
    /**
     * This method saves the data for this window.
     */
    private void saveDataImpl() {
        if (dataChanged)
            saveData();
        dataChanged = false;
        applyButton.setDisable(true);
    }
       
    @Override
    public final void showAndWait() {
        loadDataImpl();
        WindowStack.push(window);
        window.showAndWait();
        WindowStack.pop();
    }
    
    /**
     * This method is called before pressing 'Apply' or 'Ok' buttons.
     * If the data is not valid (methods return false) the window is not closed
     * and some components can be changed or error messages shown allowing
     * the user to correct its input, otherwise the data is fetched from
     * the components and saved using saveData.
     * 
     * @return True if the window can continue in its save, false otherwise.
     */
    protected abstract boolean validateBeforeSave();
}
