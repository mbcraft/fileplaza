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

package it.mbcraft.fileplaza.ui.panels.tags.generic;

import it.mbcraft.fileplaza.data.models.Tag;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import javafx.beans.property.ListProperty;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * This class handles the state of the tag panel. It contains all the tags of the current
 * selected file or folder.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */

public class TagListPanel implements INodeProvider {

    private final FlowPane pane = new FlowPane();
    private final Label tagsLabel;
    
    private final boolean isLabelAlwaysShown;
    private final ListProperty<Tag> tagList;
    
    public TagListPanel(String labelText,boolean labelAlwaysShown,ListProperty<Tag> myList) {    
        tagsLabel = new Label(labelText);
        tagsLabel.setLabelFor(pane);
        isLabelAlwaysShown = labelAlwaysShown;
        tagList = myList;
        
        initContainer();
        
        if (isLabelAlwaysShown)
            pane.getChildren().add(tagsLabel);
        
        tagList.addListener(new ListChangeListener<Tag>(){

            @Override
            public void onChanged(Change<? extends Tag> tag) {
                updateTags();
            }
        });
    }
    
    private void initContainer() {
        pane.setPadding(new Insets(5));
        pane.setHgap(3);
        pane.setVgap(3);
    }
    
    @Override
    public Node getNode() {
        return pane;
    }
            
    private void updateTags() {
        
        pane.getChildren().clear();
        if (isLabelAlwaysShown)
            pane.getChildren().add(tagsLabel);
        
        if (!tagList.isEmpty()) {
           
            if (!isLabelAlwaysShown)
                pane.getChildren().add(tagsLabel);

            for (Tag t : tagList.getValue()) {
                TagPanel tp = new TagPanel(t,tagList);
                pane.getChildren().add(tp.getNode());
            }
        }
        
    }
            
}
