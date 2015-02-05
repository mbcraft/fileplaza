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

package it.mbcraft.fileplaza.ui.panels.files.icon;

import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import java.io.File;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileIconCellMouseListener implements EventHandler<MouseEvent> {

    private final IFileItemActionListener myListener;
    
    public FileIconCellMouseListener(IFileItemActionListener listener) {
        myListener = listener;
    }
    
    @Override
    public void handle(MouseEvent t) {
        FileIconCell cell = (FileIconCell) t.getSource();
        if (myListener!=null && t.getSource()!=null) {
            if (cell.getItem()!=null) {
                File item = cell.getItem();
                
                if (t.getButton()==MouseButton.SECONDARY)
                    myListener.contextMenu(item, t, cell.getSelectionPlace(t));

                if (t.getButton()==MouseButton.PRIMARY)
                {
                    if (t.getClickCount()==1)                
                        myListener.simpleSelection(item, t, cell.getSelectionPlace(t));
                    if (t.getClickCount()==2)
                        myListener.heavySelection(item, t, cell.getSelectionPlace(t));
                }
            } 
            t.consume();
        }
    }
        
}
