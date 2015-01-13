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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("common.AbstractPresenterWindow")
public abstract class AbstractPresenterWindow implements IWindow {
        
    private final String windowTitle;
    private Stage window;
    private VBox box;
        
    public AbstractPresenterWindow(String title) {
        windowTitle = title;
        
        initialize();
    }
    
    private void initialize() {
        initWindow();
        initMiddleContent();
        initButtonPane();
    }
    
    protected abstract void initMiddleContent();
    
    protected void addToWindow(Node n) {
        box.getChildren().add(n);
    }
    
    protected void setSpacing(double d) {
        box.setSpacing(d);
    }
    
    protected void setPadding(Insets insets) {
        box.setPadding(insets);
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
    
    private void initButtonPane() {
        
        Button closeButton = new Button(L("common.AbstractPresenterWindow","Close_Button"));
        closeButton.setDefaultButton(true);
        closeButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                window.hide();
            }
        
        });
                
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.getChildren().addAll(closeButton);
        pane.setAlignment(Pos.CENTER_RIGHT);
        
        box.getChildren().add(pane);
    }
    
    protected abstract void reset();
    
    public final void showAndWait() {
        reset();
        WindowStack.push(window);
        window.showAndWait();
        WindowStack.pop();
    }
}
