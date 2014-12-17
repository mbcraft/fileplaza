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
