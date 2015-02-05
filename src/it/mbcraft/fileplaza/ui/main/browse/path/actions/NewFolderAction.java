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
