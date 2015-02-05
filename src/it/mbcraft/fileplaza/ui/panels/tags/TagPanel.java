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
