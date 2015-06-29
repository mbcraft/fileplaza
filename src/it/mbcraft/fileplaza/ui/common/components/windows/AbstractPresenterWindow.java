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
 * This abstract class is used to ease the development of
 * windows presenting information to the user.
 * This abstract class features a localized window title and an 'Ok'
 * button used to close the window.
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
    
    /**
     * This method must add all the content to the window using methods
     * of this class.
     */
    protected abstract void initMiddleContent();
    
    /**
     * Adds a node to the window container. Nodes are stacked vertically
     * in a VBox layout.
     * 
     * @param n The Node instance to add to this window.
     */
    protected void addToWindow(Node n) {
        box.getChildren().add(n);
    }
    
    /**
     * Sets the spacing between nodes in this window.
     * 
     * @param d The spacing double value
     */
    protected void setSpacing(double d) {
        box.setSpacing(d);
    }
    
    /**
     * Sets the padding Insets of the nodes in this window.
     * 
     * @param insets The insets to use as padding.
     */
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
    
    /**
     * Resets this window before show. Makes the component
     * inside this window ready for be shown.
     */
    protected abstract void reset();
    
    @Override
    public final void showAndWait() {
        reset();
        WindowStack.push(window);
        window.showAndWait();
        WindowStack.pop();
    }
}
