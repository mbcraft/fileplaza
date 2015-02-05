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

package it.mbcraft.fileplaza.ui.main.browse;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import it.mbcraft.fileplaza.ui.common.components.PersistentButtonToggleGroup;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.common.helpers.StackableNodeProviderSelector;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.ZoomInAction;
import it.mbcraft.fileplaza.ui.main.browse.path.actions.ZoomOutAction;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ViewCommandsPanel implements INodeProvider {

    private final HBox buttonsPane;
    private final IntegerProperty sharedZoomLevel;
    
    public ViewCommandsPanel(List<IZoomableNodeProvider> panels) {
        
        sharedZoomLevel = new SimpleIntegerProperty(ZoomHelper.getMinLevelIndex());
        //bind share zoom level to all zoomable panes
        final StackableNodeProviderSelector selector = new StackableNodeProviderSelector();
        //setup view panes
        for (IZoomableNodeProvider n : panels) {
            selector.add(n);
            n.zoomLevelProperty().bind(sharedZoomLevel);
        }
                
        buttonsPane = new HBox();
        buttonsPane.setPadding(new Insets(5));
        buttonsPane.setSpacing(5);
        
        final ToggleGroup viewModeGroup = new PersistentButtonToggleGroup();
        
        final ToggleButton viewAsListToggle = new ToggleButton("",IconFactory.getFeatureIcon("Page_Lined_32",32));
        viewAsListToggle.setToggleGroup(viewModeGroup);
        viewAsListToggle.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                selector.select(0);
            }
        });
        
        
        final ToggleButton viewAsIconsToggle = new ToggleButton("",IconFactory.getFeatureIcon("Page_Green_Grid_32",32));
        viewAsIconsToggle.setToggleGroup(viewModeGroup);
        viewAsIconsToggle.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                selector.select(1);
            }
        });
        
        viewModeGroup.selectToggle(viewAsListToggle);
        
        final Button zoomInButton = new Button();
        zoomInButton.setGraphic(IconFactory.getFeatureIcon("Plus_Silver_32",ZoomHelper.getMinItemSize()*2));
        zoomInButton.setOnAction(new ZoomInAction(sharedZoomLevel));
        zoomInButton.setDefaultButton(false);
        
        final Button zoomOutButton = new Button();
        zoomOutButton.setGraphic(IconFactory.getFeatureIcon("Minus_Silver_32",ZoomHelper.getMinItemSize()*2));
        zoomOutButton.setOnAction(new ZoomOutAction(sharedZoomLevel));
        zoomOutButton.setDefaultButton(false);
        
        sharedZoomLevel.addListener((ChangeListener)new ChangeListener<Integer>(){

            @Override
            public void changed(ObservableValue<? extends Integer> ov, Integer oldValue, Integer newValue) {
                updateZoomInOutButtons(zoomInButton,zoomOutButton,newValue);

            }
        });
        
        buttonsPane.getChildren().add(viewAsListToggle);
        buttonsPane.getChildren().add(viewAsIconsToggle);
        buttonsPane.getChildren().add(zoomInButton);
        buttonsPane.getChildren().add(zoomOutButton);
        
        updateZoomInOutButtons(zoomInButton, zoomOutButton, sharedZoomLevel.get());
        
        selector.select(0);
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
    
    public IntegerProperty sharedZoomLevelProperty() {
        return sharedZoomLevel;
    }
    
    @Override
    public Node getNode() {
        return buttonsPane;
    }
    
}
