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

package it.mbcraft.fileplaza.ui.main.search;

import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import java.io.File;
import javafx.scene.input.MouseEvent;

/**
 * Listener for searched files showed in the list.
 * Actually no actions are done for heavy selection, simple selection or context
 * menu requests.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SearchPanelFileListListener implements IElementActionListener {

    @Override
    public void heavySelection(File f, MouseEvent ev, SelectionPlace p) {
        //
    }

    @Override
    public void contextMenu(File f, MouseEvent ev, SelectionPlace p) {
        //
    }

    @Override
    public void simpleSelection(File f, MouseEvent ev, SelectionPlace p) {
    
    }
    
}
