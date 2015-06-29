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

package it.mbcraft.fileplaza.ui.common.components.misc;

import javafx.scene.layout.Pane;

/**
 * This class implements a transparent pane. Transparency is obtained
 * using JavaFX css styles. It actually features a preferred width and height
 * to be specified in the panel constructor.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class GlassPane extends Pane {
    
    /**
     * Construct a transparent pane.
     * 
     * @param width The preferred width for this pane
     * @param height The preferred height for this pane
     */
    public GlassPane(double width,double height) {
        setStyle("-fx-background-color:transparent;");
        setPrefSize(width, height);
    }    
}
