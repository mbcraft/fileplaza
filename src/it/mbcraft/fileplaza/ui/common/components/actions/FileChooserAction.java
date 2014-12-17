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
import java.security.InvalidParameterException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * This class is used to set the chosen file in a disabled TextField
 * as is done in many applications.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileChooserAction implements EventHandler<ActionEvent> {

    private final Node myUserDataContainer;
    private final TextField myPathContainer;
    private final FileChooser chooser;
    
    public FileChooserAction(Node userDataContainer,TextField pathContainer) {
        if (userDataContainer==null) throw new InvalidParameterException("User data container can't be null.");
         
        myUserDataContainer = userDataContainer;
        myPathContainer = pathContainer;
        chooser = new FileChooser();
    }
    
    @Override
    public void handle(ActionEvent t) {
        File chosenFile = chooser.showOpenDialog(WindowStack.top());
        if (chosenFile!=null) {
            if (myPathContainer!=null) {
                myPathContainer.setText(chosenFile.getAbsolutePath());
            }
            myUserDataContainer.setUserData(chosenFile);
        }
    }   
}
