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

package it.mbcraft.fileplaza.ui.common.helpers;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class StackableNodeProviderSelector {
    
    private final List<INodeProvider> panels = new ArrayList();
    private int lastSelection = 0;
    
    public INodeProvider select(int index) {
        for (INodeProvider panel : panels) {
            Node n = panel.getNode();
            n.setVisible(false);
        }
        lastSelection = index;
        INodeProvider chosen = panels.get(index);
        chosen.getNode().setVisible(true);
        
        return chosen;
    }
    
    public void add(INodeProvider panel) {
        panels.add(panel);
        panel.getNode().setVisible(false);
    }
    
    public INodeProvider selectFirst() {
        return select(0);
    }

    public INodeProvider selectLast() {
        return select(panels.size()-1);
    }

    public int getPanelCount() {
        return panels.size();
    }

    public int getLastSelectionIndex() {
        return lastSelection;
    }
}
