/*
 *  Developed by MBCRAFT. Copyright © 2014-2015. All rights reserved.
 *  This file of source code is property of MBCRAFT (http://www.mbcraft.it). 
 *  Do not sell, do not remove this license note even if you edit this file.
 *  Do not use this source code to develop your own file manager application.
 *  You can reuse part or full files for your own project (eg javafx ui classes)
 *  but keep copyright in files, and please link http://www.mbcraft.it on your
 *  project website.
 *
 *  Thanks
 *
 *  - Marco Bagnaresi
 */

package it.mbcraft.fileplaza.ui.main.menu.file.dictionaries;

import java.util.Map.Entry;
import javafx.scene.control.ListCell;

/**
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
