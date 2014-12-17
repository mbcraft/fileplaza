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

package it.mbcraft.fileplaza.ui.main.browse.path.actions;

import it.mbcraft.fileplaza.ui.common.components.IZoomableNodeProvider;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ZoomInAction implements EventHandler<ActionEvent> {

    private final IZoomableNodeProvider zoomableNode;
    
    public ZoomInAction(IZoomableNodeProvider node) {
        zoomableNode = node;
    }
    
    @Override
    public void handle(ActionEvent t) {
        zoomableNode.zoomIn();
    }
    
}
