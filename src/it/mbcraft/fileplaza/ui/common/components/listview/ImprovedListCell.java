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

package it.mbcraft.fileplaza.ui.common.components.listview;

import it.mbcraft.fileplaza.ui.common.components.IViewableElement;
import it.mbcraft.fileplaza.ui.common.IconReference;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener.SelectionPlace;
import it.mbcraft.fileplaza.utils.NodeUtils;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

/**
 * This class builds on ListCell providing some common methods and a standard
 * view for items as ListCell s.
 * It actually features :
 * - zoom level of items
 * - a main icon
 * - a main label text
 * - a customizable label font
 * - a list of status icons
 * - detection of selection place (status icon, main icon, label text or other)
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 * @param <T> The type of the item to be displayed and used to update the cell content
 */
public abstract class ImprovedListCell<T> extends ListCell<T> implements IViewableElement {
    
    private final IntegerProperty cellZoomLevelProperty;
    
    private IconReference mainIcon;
    
    private final List<IconReference> statusIcons = new ArrayList<>();
    
    private final HBox mainIconAndLabelPane;
    
    private final Pane mainIconPane;
    
    private final Label myText;
    
    private final HBox statusIconsPane;
    
    protected static final String CELL_FONT = "Arial";
    
    /**
     * Creates a cell.
     * 
     * @param cellZoomLevelProp The property to be used as zoom value for this cell
     */
    public ImprovedListCell(IntegerProperty cellZoomLevelProp) {
        
        cellZoomLevelProperty = cellZoomLevelProp;
        
        mainIconAndLabelPane = new HBox();
        mainIconAndLabelPane.setAlignment(Pos.CENTER_LEFT);
        mainIconAndLabelPane.setSpacing(5);
        
        mainIconPane = new Pane();
        myText = new Label();

        mainIconAndLabelPane.getChildren().add(mainIconPane);
        mainIconAndLabelPane.getChildren().add(myText);
        
        statusIconsPane = new HBox();
        statusIconsPane.setAlignment(Pos.CENTER_RIGHT);
        
        BorderPane finalPane = new BorderPane();
        
        finalPane.setLeft(mainIconAndLabelPane);
        finalPane.setRight(statusIconsPane);
        
        setGraphic(finalPane);
        
        setupListeners();
    }
              
    private void setupListeners() {
        itemProperty().addListener((ChangeListener)new ChangeListener<T>(){

            @Override
            public void changed(ObservableValue<? extends T> ov, T oldValue, T newValue) {
                if (newValue != null) {
                    updateItem(newValue, false);
                    updateCellContent();
                } else {
                    updateItem(null, true);
                }}
        });
        
    }
    
    private void updateCellContent() {
        int itemSize = ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get());
        
        setMainIcon(mainIcon);
        
        statusIconsPane.getChildren().clear();
        for (IconReference ref : statusIcons) {
            statusIconsPane.getChildren().add(IconFactory.getIconByReference(ref, itemSize));
        }
        
        setLabelFont(new Font(CELL_FONT,itemSize));
    }
    
    @Override
    public void clearMainIcon() {
        mainIconPane.getChildren().clear();
    }
    
    @Override
    public void setMainIcon(IconReference ref) {
        mainIcon = ref;
        
        mainIconPane.getChildren().clear();
        
        if (ref!=null)
            mainIconPane.getChildren().add(IconFactory.getIconByReference(ref, ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get()))); 
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
        statusIconsPane.getChildren().clear();
    }
    
    @Override
    public void pushStatusIcon(IconReference ref) {        
        statusIcons.add(ref);
        statusIconsPane.getChildren().add(IconFactory.getIconByReference(ref, ZoomHelper.getSizeFromZoomLevel(cellZoomLevelProperty.get())));
    }
    
    public SelectionPlace getSelectionPlace(MouseEvent t) {
        //System.out.println("X : "+t.getX());
        //System.out.println("Y : "+t.getY());       
        
        if (NodeUtils.containsMouseEvent(myText,t)) {
            //System.out.println("NAME");
            return SelectionPlace.NAME;
        }
        
        if (NodeUtils.containsMouseEvent(statusIconsPane,t)) {
            //System.out.println("STATUS ICON");
            return SelectionPlace.STATUS_ICON;
        }
        
        if (NodeUtils.containsMouseEvent(mainIconPane,t)) {
            //System.out.println("ICON");
            return SelectionPlace.ICON;
        }
        
        //System.out.println("ITEM");
        return SelectionPlace.ITEM;
        
    }
}
