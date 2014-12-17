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

package it.mbcraft.fileplaza.ui.main.menu.file.label_sets;

import it.mbcraft.fileplaza.data.models.LabelSet;
import javafx.scene.control.ListCell;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LabelSetListCell extends ListCell<LabelSet> {

    @Override
    public void updateItem(LabelSet data,boolean empty) {
        super.updateItem(data, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            String enabled = data.isEnabled() ? " (enabled)" : "";
            setText(data.getShortTitle()+enabled);
            setGraphic(null);
        }
        setUserData(data);
    }
    
}
