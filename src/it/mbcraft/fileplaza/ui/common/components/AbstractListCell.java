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

package it.mbcraft.fileplaza.ui.common.components;

import it.mbcraft.fileplaza.ui.common.helpers.IconReference;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T>
 */
public abstract class AbstractListCell<T> extends ListCell<T> implements IViewableElement {
    
    private int itemSize;
    
    private IconReference mainIcon;
    private final List<IconReference> statusIcons = new ArrayList<IconReference>();
    
    private final BorderPane mainIconAndLabel;
    
    private final Label myText;
    
    private final HBox iconPane;
    
    public AbstractListCell(int size) {
        itemSize = size;
        
        mainIconAndLabel = new BorderPane();
        
        myText = new Label();
        
        mainIconAndLabel.setRight(myText);
        iconPane = new HBox();
        iconPane.setAlignment(Pos.CENTER_RIGHT);
        setText(null);
        BorderPane pane = new BorderPane();
        pane.setLeft(mainIconAndLabel);
        pane.setCenter(iconPane);
        setGraphic(pane);
        
    }
    
    @Override
    public int getItemSize() {
        return itemSize;
    }
    
    @Override
    public void setItemSize(int size) {
        itemSize = size;
        
        setMainIcon(mainIcon);
        
        for (IconReference ref : statusIcons) {
            iconPane.getChildren().clear();
            iconPane.getChildren().add(IconFactory.getIconByReference(ref, itemSize));
            setLabelFont(new Font(getLabelFont().getName(),size));
        }
    }
    
    @Override
    public void clearMainIcon() {
        mainIconAndLabel.setLeft(null);
    }
    
    @Override
    public void setMainIcon(IconReference ref) {
        mainIcon = ref;
        
        if (ref!=null)
            mainIconAndLabel.setLeft(IconFactory.getIconByReference(ref, itemSize));
        else
            mainIconAndLabel.setLeft(null);
    }
    
    @Override
    public String getLabelText() {
        return myText.getText();
    }
    
    @Override
    public void setLabelText(String text) {
        myText.setText(text);
    }
    
    @Override
    public void setLabelFont(Font f) {
        myText.setFont(f);
    }
    
    @Override
    public Font getLabelFont() {
        return myText.getFont();
    }
    
    @Override
    public void clearStatusIcons() {
        statusIcons.clear();
        iconPane.getChildren().clear();
    }
    
    @Override
    public void pushStatusIcon(IconReference ref) {        
        statusIcons.add(ref);
        iconPane.getChildren().add(IconFactory.getIconByReference(ref, itemSize));
    }
}
