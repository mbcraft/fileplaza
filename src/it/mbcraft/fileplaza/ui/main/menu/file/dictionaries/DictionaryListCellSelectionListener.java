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

import it.mbcraft.fileplaza.data.models.Dictionary;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
class DictionaryListCellSelectionListener implements EventHandler<MouseEvent> {

    private final DictionariesWindow window;
    
    public DictionaryListCellSelectionListener(DictionariesWindow window) {
        this.window = window;
    }
    
    @Override
    public void handle(MouseEvent t) {
        DictionaryListCell cell = (DictionaryListCell)t.getSource();
        Dictionary d = (Dictionary) cell.getUserData();
        window.viewDictionary(d);
    }
    
}
