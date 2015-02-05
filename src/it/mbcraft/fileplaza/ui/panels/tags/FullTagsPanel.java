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
