/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.ui.main.browse.path.actions;

import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Common abstract action for file browser actions. Saves the CurrentDirectoryState parameter to a private final variable.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
abstract class AbstractBrowsePanelAction implements EventHandler<ActionEvent> {
    
    protected final CurrentDirectoryState myState;
    
    /**
     * Shared common constructor.
     * 
     * @param state The CurrentDirectoryState instance to save.
     */
    public AbstractBrowsePanelAction(CurrentDirectoryState state) {
        myState = state;
    }
}
