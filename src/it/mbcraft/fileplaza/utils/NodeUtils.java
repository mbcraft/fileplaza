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
package it.mbcraft.fileplaza.utils;

import it.mbcraft.fileplaza.ui.panels.files.icon.FileIconCell;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.IndexedCell;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class NodeUtils {
    
    public static boolean containsMouseEvent(Node n, MouseEvent ev) {
        return n.contains(n.sceneToLocal(ev.getSceneX(), ev.getSceneY()));
    }

    /*
     Suggestions from www.stackoverflow.com :
     http://stackoverflow.com/questions/13074459/javafx-2-and-css-pseudo-classes-setting-hover-attributes-in-setstyle-method
     http://stackoverflow.com/users/1155209/jewelsea
     */
    public static void setupNodeHover(Node n, String notHoverStyle, String hoverStyle) {
        n.styleProperty().bind(Bindings.when(n.hoverProperty()).then(
                new SimpleStringProperty(hoverStyle)).otherwise(
                        new SimpleStringProperty(notHoverStyle)
                ));
    }
    
    public static void setupNodeSelection(IndexedCell cell, String notSelectedStyle, String selectedStyle) {
        
        cell.styleProperty().bind(Bindings.when(cell.selectedProperty()).then(new SimpleStringProperty(selectedStyle)).otherwise(new SimpleStringProperty(notSelectedStyle)));
    }
}
