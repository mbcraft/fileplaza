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

package it.mbcraft.fileplaza.ui.panels.files.header;

import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileSortOptionsPanel implements INodeProvider {

    private final HBox pane;
    private final Button sortOptionsButton;
    private final FileSortOptionsWindow fileSortOptionsWindow;
    
    public FileSortOptionsPanel(final CurrentDirectoryState currentDirState) {
        pane = new HBox();
        pane.setPadding(new Insets(5));
        pane.setSpacing(5);
        
        fileSortOptionsWindow = new FileSortOptionsWindow(currentDirState.sortModeProperty(),currentDirState.sortOptionProperty());
        sortOptionsButton = new Button();
        sortOptionsButton.setGraphic(IconFactory.getFeatureIcon("Directions_32", 32));
        sortOptionsButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                fileSortOptionsWindow.showAndWait();
                currentDirState.refreshFileListIfNeeded();
            }
        });
        
        pane.getChildren().add(sortOptionsButton);
    }
    
    @Override
    public Node getNode() {
        return pane;
    }
    
}
