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

package it.mbcraft.fileplaza.ui.common.components.tileview;

import com.guigarage.fx.grid.GridCell;
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import it.mbcraft.fileplaza.ui.common.components.IViewableElement;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.IconReference;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener.SelectionPlace;
import it.mbcraft.fileplaza.utils.NodeUtils;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T>
 */
public abstract class ImprovedTileCell<T> extends GridCell<T> implements IViewableElement {
    
    private int itemSize;
    
    private IconReference mainIcon;
    private final List<IconReference> statusIcons = new ArrayList<>();
    
    private final BorderPane mainIconPane;
    
    private final Label myText;
    
    private final FlowPane statusIconPane;
       
    public ImprovedTileCell(int size) {
        itemSize = size;
        
        statusIconPane = new FlowPane();
        statusIconPane.setAlignment(Pos.CENTER);

        mainIconPane = new BorderPane();
        
        myText = new Label();
        myText.setMinWidth(Control.USE_PREF_SIZE);
        myText.setMinHeight(Control.USE_PREF_SIZE);
        myText.setAlignment(Pos.TOP_CENTER);
        myText.minWidthProperty().bind(widthProperty());
        myText.maxWidthProperty().bind(widthProperty());
        
        VBox layoutPane = new VBox(); 
        layoutPane.getChildren().add(statusIconPane);
        layoutPane.getChildren().add(mainIconPane);       
        layoutPane.getChildren().add(myText);
                
        setGraphic(layoutPane);
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
            statusIconPane.getChildren().clear();
            statusIconPane.getChildren().add(IconFactory.getIconByReference(ref, itemSize));
            setLabelFont(new Font(getLabelFont().getName(),size));
        }
    }
    
    @Override
    public void clearMainIcon() {
        mainIconPane.setCenter(null);
    }
    
    @Override
    public void setMainIcon(IconReference ref) {
        mainIcon = ref;
        
        if (ref!=null)
            mainIconPane.setCenter(IconFactory.getIconByReference(ref, itemSize));
        else
            mainIconPane.setCenter(null);
    }
    
    @Override
    public String getLabelText() {
        return myText.getText();
    }
    
    @Override
    public void setLabelText(String text) {
        String finalText = "";
        int pos = 0;
        while (text.length() > pos+18) {
            int endPosition = pos+18 < text.length() ? pos+18 : text.length()-1;
            finalText += text.substring(pos, endPosition)+"\n";
            pos+=18;
        }
        finalText+=text.substring(pos);
        myText.setText(finalText);
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
        statusIconPane.getChildren().clear();
    }
    
    @Override
    public void pushStatusIcon(IconReference ref) {        
        statusIcons.add(ref);
        statusIconPane.getChildren().add(IconFactory.getIconByReference(ref, itemSize));
    }
    
    private void printBounds(Bounds b) {
        System.out.println("Min X :"+b.getMinX());
        System.out.println("Min Y :"+b.getMinY());
        System.out.println("Max X :"+b.getMaxX());
        System.out.println("Max Y :"+b.getMaxY());
        System.out.println("Width : "+b.getWidth());
        System.out.println("Height : "+b.getHeight());
        
    }
    
    public void printDebugInformations() {        
        System.out.println("Component : ");
        printBounds(layoutBoundsProperty().get());
        System.out.println("Label : ");
        printBounds(myText.getLayoutBounds());
        
        Font f = new Font("Arial",10);
        
        System.out.println("Size of text : "+computeTextSize("Thisisaverylongtextsoweshouldbecareful", f));
    }
    
    public double computeTextSize(String text,Font f) {
        return Toolkit.getToolkit().getFontLoader().computeStringWidth(text, f);
    }
    
    public IFileItemActionListener.SelectionPlace getSelectionPlace(MouseEvent t) {
        System.out.println("X : "+t.getX());
        System.out.println("Y : "+t.getY());       
        
        if (NodeUtils.containsMouseEvent(myText,t))
            return SelectionPlace.NAME;
        if (NodeUtils.containsMouseEvent(statusIconPane,t))
            return SelectionPlace.STATUS_ICON;
        if (NodeUtils.containsMouseEvent(mainIconPane,t))
            return SelectionPlace.ICON;
        
        return SelectionPlace.ITEM;

    }
}
