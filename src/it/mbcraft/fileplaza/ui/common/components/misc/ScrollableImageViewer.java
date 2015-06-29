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
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

/**
 * This class creates a scrollable image viewer. The widget never shows
 * scroll bars : it just enables the image to be moved around using a drag
 * behavior showing an open hand cursor above the image.
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
    
    /**
     * Updates the image shown in this scroller.
     * 
     * @param img The Image instance of the image to whow.
     */
    public void updateImage(Image img) {
        lastImageView = new ImageView(img);
        pane.setContent(lastImageView);
    }
    
    @Override
    public Node getNode() {
        return pane;
    }
}
