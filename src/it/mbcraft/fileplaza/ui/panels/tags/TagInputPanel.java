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
import it.mbcraft.fileplaza.data.dao.meta.DictionaryDAO;
import it.mbcraft.fileplaza.data.dao.meta.LabelSetDAO;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class TagInputPanel implements INodeProvider {

    private final List<Tag> myList;
    
    private final HBox pane = new HBox();
    
    private TextField tagInput;
        
    public TagInputPanel(List<Tag> list) {
        
        myList = list;
        
        initContainer();
        
        initContent();

    }
    
    private void initContainer() {
        pane.setPadding(new Insets(5));
    }
    
    private void initContent() {
        Label l = new Label(L(this,"Input_Label"));
        
        tagInput = new TextField();
        tagInput.setPrefColumnCount(10);
        tagInput.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent ev) {
                if (tagInput.getText()!=null && !tagInput.getText().equals("")) {
                    List<Tag> tags = createTagsFromCurrentText();
                    for (Tag t : tags)
                        myList.add(t);
                    tagInput.setText(null);
                }
            }
        });
        
        pane.getChildren().addAll(ComponentFactory.newPaddingPane(l, 5),ComponentFactory.newPaddingPane(tagInput, 5)); 
    
    }
    
    @Override
    public Node getNode() {
        return pane;
    }
            
    private List<Tag> createTagsFromCurrentText() {
        String text = tagInput.getText();
        
        String[] parts = text.split(",");
        
        List<Tag> tags = new ArrayList();
        for (String part : parts) {
            tags.add(createTagFromText(part));
        }
        return tags;
    }
        
    private Tag createTagFromText(String text) {
                    try {
            Date dt = Date.valueOf(text);
            Tag t = new Tag("date",dt,Tag.TagType.DATE);
            return t;
        }
        catch (Exception dfe) {
        }
        
        //integer
        try {
            Integer i = Integer.parseInt(text);
            Tag t = new Tag("num",i,Tag.TagType.NUMBER);
            return t;
        } catch (NumberFormatException ex) {
            
        }
        
        //label set
        String enumFound = LabelSetDAO.getInstance().findSetNameForValue(text);
        if (enumFound!=null) {
            Tag t = new Tag(enumFound,text,Tag.TagType.LABEL);
            return t;
        }
        
        //dictionary
        String d = DictionaryDAO.getInstance().findDictionaryTitleForWord(text);
        if (d!=null) {
            Tag t = new Tag(d,text,Tag.TagType.DICTIONARY);
            return t;
        }
        
        //string
        Tag t = new Tag("-",text,Tag.TagType.STRING);
        return t;
        }
    
}
