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

import java.util.Map.Entry;
import javafx.scene.control.ListCell;

/**
 * This class displays a DictionaryEntry as a ListCell.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
class DictionaryEntryListCell extends ListCell<Entry<String,String>> {
    
    @Override
    public void updateItem(Entry<String,String> data,boolean empty) {
        super.updateItem(data, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            setText(data.getKey()+" / "+data.getValue());
            setGraphic(null);
        }
        setUserData(data);
    }
}
