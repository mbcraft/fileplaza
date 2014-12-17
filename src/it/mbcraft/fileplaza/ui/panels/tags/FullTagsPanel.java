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
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FullTagsPanel implements INodeProvider {

    private final TagListPanel tagList;
    private final TagInputPanel tagInput;
    
    private final BorderPane panel_;
    
    public FullTagsPanel(String label,boolean labelAlwaysShown,ListProperty<Tag> list) {
        
        tagList = new TagListPanel(label,labelAlwaysShown,list);
        tagInput = new TagInputPanel(list.get());
        
        panel_ = new BorderPane();
        
        panel_.setCenter(tagList.getNode());
        panel_.setRight(tagInput.getNode());
        
    }
            
    @Override
    public Node getNode() {
        return panel_;
    }    
}
