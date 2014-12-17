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

package it.mbcraft.fileplaza.ui.common.helpers;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class StackablePanelSelector {
    
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
