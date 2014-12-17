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
import it.mbcraft.fileplaza.ui.main.browse.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.dialogs.Dialog;
import it.mbcraft.fileplaza.ui.dialogs.DialogButton;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import javafx.event.ActionEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.path.actions.NewFolderAction")
public class NewFolderAction extends AbstractBrowsePanelAction {

    public NewFolderAction(CurrentDirectoryState state) {
        super(state);
    }

    @Override
    public void handle(ActionEvent t) {

        Dialog dialog = DialogFactory.newTextInputDialog(L(this,"NewFolderInput_Dialog"), L(this,"NewFolderInput_Label"), "");
        
        if (dialog.show()==DialogButton.Type.OK) {
            myState.newFolder(dialog.getInputProperty(0).getValue().toString());
        }
    }
    

    
}
