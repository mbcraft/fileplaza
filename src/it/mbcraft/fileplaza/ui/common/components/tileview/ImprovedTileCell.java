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

package it.mbcraft.fileplaza.ui.common.components.tileview;

import com.guigarage.fx.grid.GridCell;
import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;
import it.mbcraft.fileplaza.ui.common.components.IViewableElement;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.IconReference;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener.SelectionPlace;
import it.mbcraft.fileplaza.utils.NodeUtils;
import it.mbcraft.fileplaza.utils.NumericUtils;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T>
 */
public abstract class ImprovedTileCell<T> extends GridCell<T> implements IViewableElement {
    
    protected final IntegerProperty cellZoomLevelProperty;
    
    private IconReference mainIcon;
    
    private final List<IconReference> statusIcons = new ArrayList<>();
    
    private final BorderPane mainIconPane;
    
    private final Label myText;
    
    private final FlowPane statusIconPane;
    
    protected static final String CELL_FONT = "Arial";
       
    public ImprovedTileCell(IntegerProperty cellZoomLevelProp) {
        cellZoomLevelProperty = cellZoomLevelProp;
        
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
                
        setText(null);
        setGraphic(layoutPane);
        
        setupListeners();
    }
    
    private void setupListeners() {
        itemProperty().addListener(new ChangeListener<T>() {

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldValue, T newValue) {
                updateCellContent(cellZoomLevelProperty.get());
            }
        });
        
        cellZoomLevelProperty.addListener((ChangeListener)new ChangeListener<Integer>(){

            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer oldValue, Integer newValue) {
                updateCellContent(newValue);
            }
        });
    }
    
    private void updateCellContent(int zoomLevel) {
                
        int itemSize = ZoomHelper.getSizeFromZoomLevel(zoomLevel);
        
        setMainIcon(mainIcon);

        statusIconPane.getChildren().clear();
        for (IconReference ref : statusIcons) {
            statusIconPane.getChildren().add(IconFactory.getIconByReference(ref, itemSize));
        }
        
        setLabelFont(new Font(CELL_FONT,itemSize));
    }
    
    @Override
    public void clearMainIcon() {
        mainIconPane.setCenter(null);
    }
    
    @Override
    public void setMainIcon(IconReference ref) {
        mainIcon = ref;
        
        if (ref!=null)
            mainIconPane.setCenter(IconFactory.getIconByReference(ref, ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get())));
        else
            mainIconPane.setCenter(null);
    }
    
    private String[] getLabelTokens(String fullText) {
        List<String> tokens = new ArrayList<>();
        int pos = 0;
        while (fullText.length() > pos+18) {
            int endPosition = pos+18 < fullText.length() ? pos+18 : fullText.length()-1;
            tokens.add(fullText.substring(pos, endPosition));
            pos+=18;
        }
        tokens.add(fullText.substring(pos));
        return tokens.toArray(new String[tokens.size()]);
    }
    
    @Override
    public String getLabelText() {
        return myText.getText();
    }
    
    @Override
    public void setLabelText(String text) {
        String[] tokens = getLabelTokens(text);
        String finalText = "";
        for (String tok : tokens) {
            finalText+=tok+"\n";
        }
        myText.setText(finalText.substring(0,finalText.length()-1));
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
        statusIconPane.getChildren().add(IconFactory.getIconByReference(ref, ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get())));
    }
        
    /*
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
    
    */
    
    public IFileItemActionListener.SelectionPlace getSelectionPlace(MouseEvent t) {
        //System.out.println("X : "+t.getX());
        //System.out.println("Y : "+t.getY());       
        
        if (NodeUtils.containsMouseEvent(myText,t))
            return SelectionPlace.NAME;
        if (NodeUtils.containsMouseEvent(statusIconPane,t))
            return SelectionPlace.STATUS_ICON;
        if (NodeUtils.containsMouseEvent(mainIconPane,t))
            return SelectionPlace.ICON;
        
        return SelectionPlace.ITEM;
    }
    
    protected double getRequiredCellWidth() {
        double statusIconsWidth = statusIconPane.getPrefWidth();
        double mainIconWidth = mainIconPane.getPrefWidth();
        double textWidth = 0;
        Font f = getLabelFont();
        FontLoader fl = Toolkit.getToolkit().getFontLoader();
        for (String st : getLabelTokens(getLabelText())) {
            double w = fl.computeStringWidth(st, f);
            if (textWidth < w) 
                textWidth = w;
        }
        
        return NumericUtils.getMaxValue(statusIconsWidth,mainIconWidth,textWidth);
    }
    

    protected double getRequiredCellHeight() {
        double statusIconsHeight = statusIconPane.getPrefHeight();
        double mainIconHeight = mainIconPane.getPrefHeight();
        double textHeight = getLabelFont().getSize();
        
        return statusIconsHeight+mainIconHeight+textHeight*(getLabelTokens(getLabelText()).length);
    }
    
}