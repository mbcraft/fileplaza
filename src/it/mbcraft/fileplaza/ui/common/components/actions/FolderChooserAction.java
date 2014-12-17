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

package it.mbcraft.fileplaza.ui.common.components.actions;

import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FolderChooserAction implements EventHandler<ActionEvent> {

    private final Node myUserDataContainer;
    private final TextField myPathContainer;
    private final DirectoryChooser chooser;
    
    public FolderChooserAction(Node userDataContainer,TextField pathContainer) {
        myUserDataContainer = userDataContainer;
        myPathContainer = pathContainer;
        chooser = new DirectoryChooser();
    }
    
    @Override
    public void handle(ActionEvent t) {
        File chosenFile = chooser.showDialog(WindowStack.top());
        if (chosenFile!=null) {
            if (myPathContainer!=null) {
                myPathContainer.setText(chosenFile.getAbsolutePath());
            }
            myUserDataContainer.setUserData(chosenFile);
        }
    }  
    
}
