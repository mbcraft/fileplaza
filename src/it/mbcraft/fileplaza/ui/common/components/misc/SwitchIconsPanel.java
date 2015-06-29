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
 * This panel enables the selection of a single item from a series of
 * buttons displayed, showing a panel from a list of available panels.
 * 
 * Actually it switches panel stacked on top of an ImprevedStackPane panel.
 * 
 * TO FIX : the button and the panel should be added on a single method, enforcing
 * consistence of data and correct usage of this class.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SwitchIconsPanel implements INodeProvider {

    private final ImprovedStackPane selector = new ImprovedStackPane();
    private final ToggleGroup viewModeGroup = new PersistentButtonToggleGroup();
    private final HBox buttonsPane; 
    private int panelCount = 0;
    
    /**
     * Creates a SwitchIconsPanel. Needs a list of INodeProviders.
     * 
     * @param panels The panels to select from.
     */
    public SwitchIconsPanel(List<INodeProvider> panels) {
        buttonsPane = new HBox();
        buttonsPane.setPadding(new Insets(5));
        buttonsPane.setSpacing(5);
    }

    /**
     * Adds a switch icon to this panel.
     * 
     * @param icon An ImageView icon for choosing among the available panels.
     */
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
    
    /**
     * Returns the StackPane containing all the panels to be shown.
     * 
     * @return The ImprovedStackPane instance
     */
    public ImprovedStackPane getStackPane() {
        return selector;
    }
    
}
