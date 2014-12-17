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

package it.mbcraft.fileplaza.ui.common.components;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ScrollableImageViewer implements INodeProvider {
    
    private final ScrollPane pane;
    private Node lastImageView;
    
    public ScrollableImageViewer() {
        pane = new ScrollPane();
        pane.setCursor(Cursor.OPEN_HAND);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        lastImageView = new Pane();
        
        pane.setContent(lastImageView);
        
        pane.setOnScroll(new EventHandler<ScrollEvent>(){
            @Override
            public void handle(ScrollEvent t) {
                t.consume();
                double dy = t.getTotalDeltaY();
                lastImageView.setScaleX(lastImageView.getScaleX()+dy);
                lastImageView.setScaleY(lastImageView.getScaleY()+dy);
            }
        });
    }
    
    public void updateImage(Image img) {
        lastImageView = new ImageView(img);
        pane.setContent(lastImageView);
    }
    
    @Override
    public Node getNode() {
        return pane;
    }
}
