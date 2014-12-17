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

package it.mbcraft.fileplaza.ui.panels.files.icon;

import it.mbcraft.fileplaza.ui.common.components.IViewableElement;
import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.common.helpers.IconReference;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileTableCell implements IZoomableNodeProvider,IViewableElement {
    
    private final BooleanProperty zoomInDisabledProperty = new SimpleBooleanProperty();
    private final BooleanProperty zoomOutDisabledProperty = new SimpleBooleanProperty();
    
    private final Pane container;
    
    private final FlowPane statusIconsPane;
    
    private final GridPane permissionIconsPane;
    
    private final Pane iconPane;
    
    private Font labelFont;
    private final Label nameLabel;
    
    private int zoomLevel = 0;
    
    public FileTableCell() {
        container = new TilePane();
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        
        container.getChildren().add(box);
        
        statusIconsPane = new FlowPane();
        statusIconsPane.setHgap(5);
        
        box.getChildren().add(statusIconsPane);
        
        GridPane middlePane = new GridPane();
        GridPaneFiller.reset(3);
        middlePane.add(new Pane(), GridPaneFiller.X(),GridPaneFiller.Y());
        
        iconPane = new Pane();
        middlePane.add(iconPane, GridPaneFiller.X(), GridPaneFiller.Y());
        
        permissionIconsPane = new GridPane();
        middlePane.add(permissionIconsPane, GridPaneFiller.X(), GridPaneFiller.Y());
                
        box.getChildren().add(middlePane);
        
        nameLabel = new Label();
        labelFont = new Font(12);
        nameLabel.setFont(labelFont);
        box.getChildren().add(nameLabel);
        
        zoomInDisabledProperty.setValue(false);
        zoomOutDisabledProperty.setValue(true);
    }
    
    @Override
    public Node getNode() {
        return container;
    }

    @Override
    public void zoomIn() {
        zoomLevel++;
        if (ZoomHelper.canZoomIn(zoomLevel))
            zoomInDisabledProperty.setValue(false);
        else
            zoomInDisabledProperty.setValue(true);
        
        zoomOutDisabledProperty.setValue(false);

    }
    
    @Override
    public BooleanProperty zoomOutDisabledProperty() {
        return zoomOutDisabledProperty;
    }
    
    @Override
    public void zoomOut() {
        zoomLevel--;
        if (ZoomHelper.canZoomOut(zoomLevel))
            zoomOutDisabledProperty.setValue(false);
        else
            zoomOutDisabledProperty.setValue(true);
        
        zoomInDisabledProperty.setValue(false);
        
    }
    
    @Override
    public BooleanProperty zoomInDisabledProperty() {
        return zoomInDisabledProperty;
    }

    @Override
    public void clearMainIcon() {
        iconPane.getChildren().clear();
    }

    @Override
    public void clearStatusIcons() {
        statusIconsPane.getChildren().clear();
    }

    @Override
    public int getItemSize() {
        return ZoomHelper.getSizeFromZoomLevel(zoomLevel);
    }

    @Override
    public Font getLabelFont() {
        return labelFont;
    }

    @Override
    public String getLabelText() {
        return nameLabel.getText();
    }

    @Override
    public void pushStatusIcon(IconReference ref) {
        
    }

    @Override
    public void setItemSize(int size) {
    }

    @Override
    public void setLabelFont(Font f) {
        labelFont = f;
        nameLabel.setFont(labelFont);
    }

    @Override
    public void setLabelText(String text) {
        nameLabel.setText(text);
    }

    @Override
    public void setMainIcon(IconReference ref) {
    }
    
}