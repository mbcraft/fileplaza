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

package it.mbcraft.fileplaza.ui.main.menu.file.actions;

import it.mbcraft.fileplaza.state.SingleSelectionFileSystemElementState;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileQuitAction implements EventHandler<ActionEvent> {
    
    private static FileQuitAction instance = null;
    
    public static FileQuitAction getInstance() {
        if (instance==null)
            instance = new FileQuitAction();
        return instance;
    }
    
    
    private FileQuitAction() {}
    
    
    @Override
    public void handle(ActionEvent t) {
        
        SingleSelectionFileSystemElementState.getInstance().saveCurrentElementIfNeeded();
        
        Platform.exit();
    }
    
}
