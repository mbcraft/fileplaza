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

package it.mbcraft.fileplaza.ui.window;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TextField;

/**
 * This class models a status bar for FilePlaza.
 * Contains a status string property that can be changed and updated when needed.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class StatusBar implements INodeProvider {

    private final TextField statusBar;
    
    public StatusBar() {
        
        statusBar = new TextField();
        statusBar.setDisable(true);
        statusBar.setAlignment(Pos.CENTER_LEFT);
    }
    
    public StringProperty statusProperty() {
        return statusBar.textProperty();
    }
    
    @Override
    public Node getNode() {
        return statusBar;
    }
    
}
