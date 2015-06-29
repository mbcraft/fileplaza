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

package it.mbcraft.fileplaza.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;

/**
 * This helper class forwards the action called to all contained nodes.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CommonActionNodeContainer {
    
    private final List<Node> nodes = new ArrayList();
    
    /**
     * Sets the disable property for all contained nodes.
     * 
     * @param value true for disabling nodes, false otherwise.
     */
    public void setDisable(boolean value) {
        for (Node n : nodes) {
            n.setDisable(value);
        }
        
    }
    
    /**
     * Sets the visible property for all contained nodes.
     * 
     * @param value true for showing nodes, false otherwise.
     */
    public void setVisible(boolean value) {
        for (Node n : nodes) {
            n.setVisible(value);
        }
    }
    
    /**
     * Add a node to this container.
     * 
     * @param n The Node instance.
     */
    public void add(Node n) {
        nodes.add(n);
    }
    
    /**
     * Adds an array of Node s to this container.
     * 
     * @param nn An array of Node instances.
     */
    public void addAll(Node ...nn) {
        nodes.addAll(Arrays.asList(nn));
    }
}
