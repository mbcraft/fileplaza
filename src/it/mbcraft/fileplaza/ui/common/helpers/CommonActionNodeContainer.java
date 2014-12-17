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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.Node;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CommonActionNodeContainer {
    
    private final List<Node> nodes = new ArrayList();
    
    public void setDisable(boolean value) {
        for (Node n : nodes) {
            n.setDisable(value);
        }
    }
    
    public void setVisible(boolean value) {
        for (Node n : nodes) {
            n.setVisible(value);
        }
    }
    
    public void add(Node n) {
        nodes.add(n);
    }
    
    public void addAll(Node ...nn) {
        nodes.addAll(Arrays.asList(nn));
    }
}
