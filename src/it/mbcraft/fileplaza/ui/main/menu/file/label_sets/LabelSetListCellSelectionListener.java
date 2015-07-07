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

package it.mbcraft.fileplaza.ui.main.menu.file.label_sets;

import it.mbcraft.fileplaza.data.models.LabelSet;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

/**
 * This class handles the event of clicks on LabelSetListCell s.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LabelSetListCellSelectionListener implements EventHandler<MouseEvent> {

    private final LabelSetsSettingsWindow window;
    
    public LabelSetListCellSelectionListener(LabelSetsSettingsWindow window) {
        this.window = window;
    }
    
    @Override
    public void handle(MouseEvent t) {
        LabelSetListCell cell = (LabelSetListCell)t.getSource();
        LabelSet en = (LabelSet) cell.getUserData();
        window.viewLabelSet(en);
    }
    
}
