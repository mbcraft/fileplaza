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

package it.mbcraft.fileplaza.ui.common.helpers;

/**
 * This class helps in building GridPane in a left to right, top to
 * bottom order. The methods X and Y should be called only for inserting
 * components with the add method of gridPane.getChildren().
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class GridPaneFiller {
    
    private static int xItems = -1;
    private static int currentX = 0;
    private static int currentY = 0;
    
    /**
     * Resets this filler helper to the requested column numbers.
     * 
     * @param numColumns The number of columns of the grid to fill.
     */
    public static void reset(int numColumns) {
        xItems = numColumns;
        currentX = 0;
        currentY = 0;
    }
    
    /**
     * Returns the x value for the grid pane
     * 
     * @return The int x value
     */
    public static int X() {        
        return currentX++;
    }
    
    /**
     * Returns the y value for the grid pane
     * 
     * @return The int y value
     */
    public static int Y() {
        if (currentX == xItems) {
            currentX = 0;
            return currentY++;
        } else {
            return currentY;
        }
            
    }
    
}
