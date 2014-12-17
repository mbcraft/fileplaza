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

package it.mbcraft.fileplaza.ui.main.browse;

import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import java.io.File;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.BrowsePanelFileListListener")
public class BrowsePanelFileListListener implements IFileItemActionListener {

    private final CurrentDirectoryState myState;
    
    public BrowsePanelFileListListener(CurrentDirectoryState state) {
        myState = state;
    }
    
    @Override
    public void heavySelection(File f) {
        if (f.isDirectory()) {
            myState.currentPathProperty().setValue(f);
        }
    }

    @Override
    public void contextMenu(File f, MouseEvent ev) {
        System.out.println("Context menu requested!");
    }
    
}
