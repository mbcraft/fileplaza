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

package it.mbcraft.fileplaza.ui.common.components.actions;

import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

/**
 * This action actually shows a folder chooser dialog.
 * It saves the selected folder as object inside a user data container, 
 * and the folder path inside a text field.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FolderChooserAction implements EventHandler<ActionEvent> {

    private final Node myUserDataContainer;
    private final TextField myPathContainer;
    private final DirectoryChooser chooser;
    
    /**
     * Creates a folder chooser action.
     * 
     * @param userDataContainer The Node instance used for saving the selected folder instance
     * @param pathContainer The TextField instance to be updated with the path of the selected folder
     */
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
