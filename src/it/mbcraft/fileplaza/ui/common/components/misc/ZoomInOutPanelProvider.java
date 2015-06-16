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

package it.mbcraft.fileplaza.ui.common.components.misc;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.ZoomInAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.ZoomOutAction;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ZoomInOutPanelProvider implements INodeProvider{

    private final HBox buttonsPane;
    private final IntegerProperty zoomLevel;
        
    public ZoomInOutPanelProvider() {
        
        buttonsPane = new HBox();
        buttonsPane.setPadding(new Insets(5));
        buttonsPane.setSpacing(5);
        
        zoomLevel = new SimpleIntegerProperty(ZoomHelper.getMinLevelIndex());
        
        final Button zoomInButton = new Button();
        zoomInButton.setGraphic(IconFactory.getFeatureIcon("Plus_Silver_32",ZoomHelper.getMinItemSize()*2));
        zoomInButton.setOnAction(new ZoomInAction(zoomLevel));
        zoomInButton.setDefaultButton(false);
        
        final Button zoomOutButton = new Button();
        zoomOutButton.setGraphic(IconFactory.getFeatureIcon("Minus_Silver_32",ZoomHelper.getMinItemSize()*2));
        zoomOutButton.setOnAction(new ZoomOutAction(zoomLevel));
        zoomOutButton.setDefaultButton(false);
        
        zoomLevel.addListener((ChangeListener)new ChangeListener<Integer>(){

            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer oldValue, Integer newValue) {
                updateZoomInOutButtons(zoomInButton,zoomOutButton,newValue);

            }
        });
        
        buttonsPane.getChildren().add(zoomInButton);
        buttonsPane.getChildren().add(zoomOutButton);
        
        updateZoomInOutButtons(zoomInButton, zoomOutButton, zoomLevel.get());
        
    }
    
    private void updateZoomInOutButtons(Button zoomInButton,Button zoomOutButton,Integer zoomLevel) {
        if (ZoomHelper.canZoomIn(zoomLevel))
            zoomInButton.setDisable(false);
        else
            zoomInButton.setDisable(true);

        if (ZoomHelper.canZoomOut(zoomLevel))
            zoomOutButton.setDisable(false);
        else
            zoomOutButton.setDisable(true);
    }
    
    public IntegerProperty zoomLevelProperty() {
        return zoomLevel;
    }
    
    @Override
    public Node getNode() {
        return buttonsPane;
    }
    
}
