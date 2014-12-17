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

import it.mbcraft.fileplaza.ui.common.components.actions.FileChooserAction;
import it.mbcraft.fileplaza.ui.common.components.actions.FolderChooserAction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ComponentFactory {
    
    public static Button newFileBrowseButton(String buttonText,Node myUserDataContainer,TextField pathContainer) {
        Button b = new Button(buttonText);
        b.setOnAction(new FileChooserAction(myUserDataContainer,pathContainer));
        return b;
    }
    
    public static Button newFolderBrowseButton(String buttonText,Node myUserDataContainer,TextField pathContainer) {
        Button b = new Button(buttonText);
        b.setOnAction(new FolderChooserAction(myUserDataContainer,pathContainer));
        return b;
    }
    
    public static Button newClearFieldButton(String clearText, final Node myUserDataContainer, final TextField pathContainer) {
        Button b = new Button(clearText);
        b.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                pathContainer.setText(null);
                myUserDataContainer.setUserData(null);
            }
        });
        return b;
    }
    
    public static Node newPaddingPane(Node toPad,int pixels) {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(pixels));
        bp.setCenter(toPad);
        return bp;
    }
        
    public static Node newRightPaddingPane(Node toPad,int pixels) {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(pixels));
        bp.setRight(toPad);
        return bp;
    }
    
    public static Node newLeftPaddingPane(Node toPad,int pixels) {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(pixels));
        bp.setLeft(toPad);
        return bp;
    }


        
}
