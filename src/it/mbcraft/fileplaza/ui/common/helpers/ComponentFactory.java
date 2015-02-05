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

package it.mbcraft.fileplaza.ui.common.helpers;

import it.mbcraft.fileplaza.ui.common.components.actions.FileChooserAction;
import it.mbcraft.fileplaza.ui.common.components.actions.FolderChooserAction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ComponentFactory {
    
    public static Button newFileBrowseButton(String buttonText,Node myUserDataContainer,TextField pathContainer) {
        Button b = new Button(buttonText);
        b.setOnAction(new FileChooserAction(myUserDataContainer,pathContainer));
        return b;
    }
    
    public static Button newFolderBrowseButton(String buttonText,Node myUserDataContainer,TextField pathContainer) {
        Button b = new Button(buttonText);
        b.setOnAction(new FolderChooserAction(myUserDataContainer,pathContainer));
        return b;
    }
    
    public static Button newClearFieldButton(String clearText, final Node myUserDataContainer, final TextField pathContainer) {
        Button b = new Button(clearText);
        b.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                pathContainer.setText(null);
                myUserDataContainer.setUserData(null);
            }
        });
        return b;
    }
    
    public static Node newPaddingPane(Node toPad,int pixels) {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(pixels));
        bp.setCenter(toPad);
        return bp;
    }
        
    public static Node newRightPaddingPane(Node toPad,int pixels) {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(pixels));
        bp.setRight(toPad);
        return bp;
    }
    
    public static Node newLeftPaddingPane(Node toPad,int pixels) {
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(pixels));
        bp.setLeft(toPad);
        return bp;
    }


        
}
