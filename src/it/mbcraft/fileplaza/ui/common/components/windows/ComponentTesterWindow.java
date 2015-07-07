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

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

/**
 * This class models a window that can be used for testing components.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ComponentTesterWindow implements IWindow {
    
    private final Stage myStage;
    
    /**
     * Creates a ComponentTesterWindow. Requires the JavaFX stage.
     * 
     * @param st The JavaFX stage.
     * @param n The component to test, as a Node instance
     */
    public ComponentTesterWindow(Stage st,Node n) {
        
        st.setTitle("Component tester");
        st.setResizable(true);
        
        BorderPane box = new BorderPane();
        
        box.setCenter(n);
        initCloseBar(box);
        
        Scene sc = new Scene(box,1000,1000);
        st.setScene(sc);

        myStage = st;
    }
    
    private void initCloseBar(BorderPane box) {
        Button closeButton = new Button("Close");
        closeButton.setDefaultButton(true);
        closeButton.setOnAction(new EventHandler(){

            @Override
            public void handle(Event t) {
                myStage.hide();
            }
        
        });
                
        FlowPane pane = new FlowPane();
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.getChildren().addAll(closeButton);
        pane.setAlignment(Pos.CENTER_RIGHT);
        
        box.setBottom(pane);
    }
    
    @Override
    public void showAndWait() {
        myStage.show();
    }
    
}
