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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ComponentTesterWindow implements IWindow {
    
    private final Stage myStage;
    
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
