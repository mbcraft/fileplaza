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

package it.mbcraft.fileplaza.ui.main.browse.path.actions;

import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Executes a zoom out action if the zoom level is not too low.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ZoomOutAction implements EventHandler<ActionEvent> {

    private final IntegerProperty zoomLevelProp;
    
    /**
     * Action constructor.
     * 
     * @param zoomLevel The zoomLevel IntegerProperty
     */
    public ZoomOutAction(IntegerProperty zoomLevel) {
        zoomLevelProp = zoomLevel;
    }
    
    @Override
    public void handle(ActionEvent t) {
        if (zoomLevelProp.get()>ZoomHelper.getMinLevelIndex())
            zoomLevelProp.set(zoomLevelProp.get()-1);
    }
    
}