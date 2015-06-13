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

package it.mbcraft.fileplaza.ui.main.browse;

import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import java.io.File;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.browse.BrowsePanelFileListListener")
public class BrowsePanelFileListener implements IElementActionListener {

    private final CurrentDirectoryState myState;
    
    public BrowsePanelFileListener(CurrentDirectoryState state) {
        myState = state;
    }
    
    @Override
    public void heavySelection(File f, MouseEvent ev,SelectionPlace p) {
        if (f.isDirectory()) {
            myState.currentPathProperty().setValue(f);
        }
    }

    @Override
    public void contextMenu(File f, MouseEvent ev,SelectionPlace p) {
        System.out.println("Context menu requested!");
    }

    @Override
    public void simpleSelection(File f,MouseEvent ev,SelectionPlace p) {
        System.out.println("File "+f.getName()+" clicked.");
    }
    
}
