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

package it.mbcraft.fileplaza.ui.common.components;

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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("common.AbstractSettingsWindow")
public abstract class AbstractSettingsWindow {
    
    private final String windowTitle;
    private Stage window;
    private VBox box;
    
    private final boolean showApply;
    private boolean dataChanged = false;
    private Button applyButton;
    
    public AbstractSettingsWindow(String title, boolean showApplyButton) {
        showApply = showApplyButton;
        windowTitle = title;
        
        initialize();
    }
    
    private void initialize() {
        initWindow();
        box.setSpacing(10);
        box.setPadding(new Insets(5));
        initMiddleContent();
        initButtonPane();
    }
    
    public void setWindowTitle(String title) {
        window.setTitle(title);
    }
    
    public String getWindowTitle() {
        return window.getTitle();
    }
    
    /**
     * Initializes the middle content od this window.
     */
    protected abstract void initMiddleContent();
    
    /**
     * Adds a node to the main window body. The nodes are stacked 
     * vertically.
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
        window.setTitle(windowTitle);
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
     * This method must load all the data for this window
     */
    protected abstract void loadData();
    
    /**
     * This method must persist all the data for this window
     */
    protected abstract void saveData();
    
    /**
     * This method must be called whenever data is changed, in order to 
     * enable the 'Apply' button. ???
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
       
    public final void showAndWait() {
        loadDataImpl();
        WindowStack.push(window);
        window.showAndWait();
        WindowStack.pop();
    }
    
    /**
     * This method is called before pressing Apply or Ok buttons.
     * 
     * @return True if the window can continue in it's action, false otherwise.
     */
    protected abstract boolean validateBeforeSave();
}
