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
import java.security.InvalidParameterException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * This action actually shows a file chooser dialog.
 * It saves the selected file as object inside a user data container, 
 * and the file path inside a text field.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileChooserAction implements EventHandler<ActionEvent> {

    private final Node myUserDataContainer;
    private final TextField myPathContainer;
    private final FileChooser chooser;
    
    /**
     * Creates a file chooser action.
     * 
     * @param userDataContainer The Node instance used for saving the selected file instance
     * @param pathContainer The TextField instance to be updated with the path of the selected folder
     */
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
