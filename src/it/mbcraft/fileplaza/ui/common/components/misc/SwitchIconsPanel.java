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
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SwitchIconsPanel implements INodeProvider {

    private final ImprovedStackPane selector = new ImprovedStackPane();
    private final ToggleGroup viewModeGroup = new PersistentButtonToggleGroup();
    private final HBox buttonsPane; 
    private int panelCount = 0;
    
    public SwitchIconsPanel(List<INodeProvider> panels) {
        buttonsPane = new HBox();
        buttonsPane.setPadding(new Insets(5));
        buttonsPane.setSpacing(5);
    }

    public void addSwitchIcon(ImageView icon) {
        final ToggleButton toggleButton = new ToggleButton("",icon);
        toggleButton.setUserData(new Integer(panelCount));
        toggleButton.setToggleGroup(viewModeGroup);
        toggleButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                selector.selectedPanelIndexProperty().setValue((int) toggleButton.getUserData());
            }
        });
        
        if (panelCount==0)
            toggleButton.selectedProperty().set(true);
        
        buttonsPane.getChildren().add(toggleButton);
        
        panelCount++;            
    }
    
    @Override
    public Node getNode() {
        return buttonsPane;
    }
    
    public ImprovedStackPane getStackPane() {
        return selector;
    }
    
}
