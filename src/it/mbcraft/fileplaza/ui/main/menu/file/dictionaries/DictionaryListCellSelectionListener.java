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

package it.mbcraft.fileplaza.ui.main.menu.file.dictionaries;

import it.mbcraft.fileplaza.data.models.Dictionary;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * Creates a listener for selection on Dictionary ListCell s.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
class DictionaryListCellSelectionListener implements EventHandler<MouseEvent> {

    private final DictionariesSettingsWindow window;
    
    public DictionaryListCellSelectionListener(DictionariesSettingsWindow window) {
        this.window = window;
    }
    
    @Override
    public void handle(MouseEvent t) {
        DictionaryListCell cell = (DictionaryListCell)t.getSource();
        Dictionary d = (Dictionary) cell.getUserData();
        window.viewDictionary(d);
    }
    
}
