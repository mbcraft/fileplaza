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
