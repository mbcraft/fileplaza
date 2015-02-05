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

package it.mbcraft.fileplaza.ui.main.menu.file.dictionaries;

import java.util.Map.Entry;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
class DictionaryEntryListCellFactory implements Callback<ListView<Entry<String,String>>, ListCell<Entry<String,String>>> {

    private final EventHandler<MouseEvent> listener;
    
    public DictionaryEntryListCellFactory(EventHandler<MouseEvent> listener) {
        this.listener = listener;
    }
    
    @Override
    public ListCell<Entry<String,String>> call(ListView<Entry<String,String>> p) {
        DictionaryEntryListCell dc = new DictionaryEntryListCell();
        dc.addEventHandler(MouseEvent.MOUSE_CLICKED, listener);
        return dc;
    }
    
}
