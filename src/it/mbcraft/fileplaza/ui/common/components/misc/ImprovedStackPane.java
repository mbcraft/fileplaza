/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.ui.common.components.misc;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * This class enables panes to be stacked on top of each other, and
 * to show only a single pane at a time. The panel to be shown
 * is indexed by a selectedPanel integer property. If the selectedPanel index
 * is not valid an exception is thrown.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ImprovedStackPane extends StackPane {
    
    private final IntegerProperty selectedPanel = new SimpleIntegerProperty(-1);
        
    public ImprovedStackPane() {
        selectedPanel.addListener(new ChangeListener(){

            @Override
            public void changed(ObservableValue ov, Object oldValue, Object newValue) {
                Integer t = (Integer) newValue;
                if (t<0 || t>=getChildren().size())
                    throw new IllegalStateException("Panel index exceeds panel number.");
                select(t);
            }
        });
    }
    
    private void select(int index) {
        for (Node panel : getChildren()) {
            
            panel.setVisible(false);
        }
        
        selectedPanel.setValue(index);
        Node chosen = getChildren().get(index);
        chosen.setVisible(true);
    }


    public IntegerProperty selectedPanelIndexProperty() {
        return selectedPanel;
    }
}
