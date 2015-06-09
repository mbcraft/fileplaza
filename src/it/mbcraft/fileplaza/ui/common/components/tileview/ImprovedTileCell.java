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
import com.sun.javafx.tk.FontMetrics;
import com.sun.javafx.tk.Toolkit;
import it.mbcraft.fileplaza.ui.common.components.IViewableElement;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.IconReference;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener.SelectionPlace;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 *
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
       
    private final VBox layoutPane;
    
    public ImprovedTileCell(IntegerProperty cellZoomLevelProp) {
        cellZoomLevelProperty = cellZoomLevelProp;
        
        statusIconPane = new FlowPane();
        statusIconPane.setAlignment(Pos.CENTER);
        statusIconPane.minWidthProperty().bind(requiredCellWidthProperty());

        mainIconPane = new BorderPane();
        mainIconPane.minWidthProperty().bind(requiredCellWidthProperty());
        
        myText = new Label();
        myText.setMinWidth(Control.USE_PREF_SIZE);
        myText.setMinHeight(Control.USE_PREF_SIZE);
        myText.setAlignment(Pos.TOP_CENTER);
        myText.setTextAlignment(TextAlignment.CENTER);
        myText.minWidthProperty().bind(requiredCellWidthProperty());
        myText.prefWidthProperty().bind(requiredCellWidthProperty());
        myText.setStyle("-fx-background-color:#999");
        myText.setWrapText(true);
                
        layoutPane = new VBox(); 
        layoutPane.setAlignment(Pos.TOP_CENTER);
        layoutPane.getChildren().add(statusIconPane);
        layoutPane.getChildren().add(mainIconPane);       
        layoutPane.getChildren().add(myText);

        layoutPane.minWidthProperty().bind(requiredCellWidthProperty());
        layoutPane.minHeightProperty().bind(requiredCellHeightProperty());
        
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
        
        if (ref!=null) {
            ImageView myIcon = IconFactory.getIconByReference(ref, ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get()));
            mainIconPane.setCenter(myIcon);
        
        } else
            mainIconPane.setCenter(null);
    }
    
    private String[] getLabelTokens(String fullText) {
        List<String> tokens = new ArrayList<>();
        int pos = 0;
        while (fullText.length() > pos+18) {
            int endPosition = pos+18 < fullText.length() ? pos+18 : fullText.length()-1;
            String piece = fullText.substring(pos, endPosition);
            
            tokens.add(piece);
            pos+=18;
        }
        String lastPiece = fullText.substring(pos);
        
        tokens.add(lastPiece);
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
            
    public IElementActionListener.SelectionPlace getSelectionPlace(MouseEvent t) {    
        
        if (NodeUtils.containsMouseEvent(myText,t))
            return SelectionPlace.NAME;
        if (NodeUtils.containsMouseEvent(statusIconPane,t))
            return SelectionPlace.STATUS_ICON;
        if (NodeUtils.containsMouseEvent(mainIconPane.getCenter(), t))
            return SelectionPlace.ICON;
        if (NodeUtils.containsMouseEvent(mainIconPane,t))
            return SelectionPlace.ITEM;

        
        return SelectionPlace.ITEM;
    }
    
    protected double getCharacterWidthPadding() {
        Font f = getLabelFont();
        FontLoader fl = Toolkit.getToolkit().getFontLoader();
        return fl.computeStringWidth("o", f);
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
        
        double requiredWidth = NumericUtils.getMaxValue(statusIconsWidth,mainIconWidth,textWidth);
        //System.out.println("Required width : "+requiredWidth);
        return requiredWidth;
    }
    

    protected double getRequiredCellHeight() {
        
        double statusIconsHeight = 0;
        if (statusIcons.size()>0)
            statusIconsHeight = ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get())/2;
        
        //main icon height
        double mainIconHeight = ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get());
        
        //text height
        FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(getLabelFont());
        int numLines = getLabelTokens(getLabelText()).length;
        double textHeight = Math.ceil(fm.getLineHeight());
        double totalTextHeight = textHeight*numLines;
                
        return statusIconsHeight+mainIconHeight+totalTextHeight;
    }

    /*
    private void dumpFontMetrics(FontMetrics fm) {
        System.out.println("Line Height : "+fm.getLineHeight());
        System.out.println("Descent : "+fm.getDescent());
        System.out.println("Ascent : "+fm.getAscent());
        System.out.println("MaxDescent : "+fm.getMaxDescent());
        System.out.println("MaxAscent : "+fm.getMaxAscent());
        System.out.println("Baseline : "+fm.getBaseline());
        System.out.println("Leading : "+fm.getLeading());
        System.out.println("Xheight : "+fm.getXheight());
    }
    */
    
}