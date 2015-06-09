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

package it.mbcraft.fileplaza.ui.main.browse.path.actions;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import java.io.File;
import it.mbcraft.fileplaza.ui.main.browse.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.dialogs.Dialog;
import it.mbcraft.fileplaza.ui.dialogs.DialogButton;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import javafx.event.ActionEvent;

/**
 * Rename current file/folder. Must be disabled if no file/folder is
 * selected.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.path.actions.RenameFileAction")
public class RenameFileAction extends AbstractBrowsePanelAction {

    public RenameFileAction(CurrentDirectoryState state) {
        super(state);
    }

    

    @Override
    public void handle(ActionEvent t) {
        File currentFile = myState.getSelectedFile();
            
        if (currentFile != null && currentFile.canWrite()) {
            Dialog inputDialog = DialogFactory.newTextInputDialog(L(this,"RenameInput_Dialog"), L(this,"RenameInput_Label"), currentFile.getName());
            DialogButton.Type result = inputDialog.show();
            if (result==DialogButton.Type.OK) {
                String newName = (String) inputDialog.getInputProperty(0).getValue();

                if (newName != null && !newName.equals("")) {
                    myState.renameCurrentFile(newName);
                } 
            }
        } else {
            DialogFactory.showErrorDialog(L(this,"Error_Dialog"), L(this,"Error_Text"));
        }

    }

}
