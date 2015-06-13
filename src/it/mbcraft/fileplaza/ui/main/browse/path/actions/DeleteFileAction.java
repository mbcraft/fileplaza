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
import it.mbcraft.fileplaza.state.CurrentDirectoryState;
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
        File currentFile = myState.getSelectedFile();
        
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
