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

package it.mbcraft.fileplaza.ui.panels.tags;

import it.mbcraft.fileplaza.data.models.Tag;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.CssFactory;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class TagPanel implements INodeProvider {
    
    private final Tag myTag;
    
    private final Label myText = new Label();
    
    private final HBox pane = new HBox();
    
    private final List<Tag> myList;
    
    public TagPanel(Tag t,List<Tag> list) {
        
        myTag = t;
        
        myList = list;
        
        initContainer();
        
        initContent();
        
        
    }    
    
    private void initContainer() {
        pane.setPadding(new Insets(2));
        pane.setAlignment(Pos.CENTER);
    }
    
    private void initContent() {
        
        myText.setText(myTag.getValueAsString());
        myText.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        myText.setAlignment(Pos.CENTER);
        pane.getChildren().add(myText);
        
        
        Button deleteButton = new Button();
        deleteButton.getStylesheets().add(CssFactory.getIconButtonCss());
        deleteButton.getStyleClass().add("icon_button");
        deleteButton.setGraphic(IconFactory.getFeatureIcon("Cross_Red_32", 16));
        deleteButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                myList.remove(myTag);
            }
        });
        deleteButton.setAlignment(Pos.TOP_RIGHT);
        pane.getChildren().add(ComponentFactory.newPaddingPane(deleteButton,3));
        
    }
    
    @Override
    public Node getNode() {
        return pane;
    }
    
}
