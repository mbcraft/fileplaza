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

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import java.io.File;
import it.mbcraft.fileplaza.ui.main.browse.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import javafx.event.ActionEvent;

/**
 * Delete selected file/folder. Must be disabled if no file/folder
 * is selected.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.path.actions.DeleteFileAction")
public class DeleteFileAction extends AbstractBrowsePanelAction {

    public DeleteFileAction(CurrentDirectoryState state) {
        super(state);
    }

    @Override
    public void handle(ActionEvent t) {
        File currentFile = myState.getCurrentSelectedFile();
        
        if (currentFile!=null && currentFile.canWrite()) {
            if (DialogFactory.newConfirmDialog(L(this,"DeleteConfirm_Dialog"),L(this,"DeleteConfirm_Text")))
                myState.deleteCurrentFile();
        }
        else {
            if (currentFile==null)
                throw new IllegalStateException("File to delete can't be null!");
            DialogFactory.showErrorDialog(L(this,"Error_Dialog"), L(this,"ErrorPrefix_Text")+currentFile.getName()+L(this,"ErrorSuffix_Text"));
        }
    }

    
}
