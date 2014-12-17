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
