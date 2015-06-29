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

import java.security.InvalidParameterException;

/**
 * Helper class for dealing with zoom level.
 * 
 * FIX IT : the zoom levels available seems not to be ok.
 * 
 * The Zoom level can vary from 1 (minimun value) to 7 (maximum value).
 * 
 * Each zoom level is mapped to an icon size.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ZoomHelper {
    
    private static final int ZOOM_SIZES[] = {16,24,32,48,96,128,256};
    
    /**
     * Returns the minimum icon dimension size.
     * 
     * @return The minimum icon size, as integer value
     */
    public static int getMinItemSize() {
        return ZOOM_SIZES[getMinLevelIndex()];
    }    
    
    /**
     * Returns the maximum icon dimension size.
     * 
     * @return The maximum icon size, as integer value
     */
    public static int getMaxItemSize() {
        return ZOOM_SIZES[getMaxLevelIndex()];
    }
    
    /**
     * Returns the minimum zoom level index (min zoom level - 1 = 0).
     * 
     * @return 
     */
    public static int getMinLevelIndex() {
        return 0;
    }
    
    /**
     * Returns the maximum zoom level index (max zoom level -1 = 6).
     * @return 
     */
    public static int getMaxLevelIndex() {
        return ZOOM_SIZES.length-1;
    }
    
    /**
     * Returns the icon size from the provided zoom level parameter.
     * 
     * @param zoomLevel The zoom level integer value
     * @return The icon size for this zoom level
     */
    public static final int getSizeFromZoomLevel(int zoomLevel) {
        return ZOOM_SIZES[zoomLevel];
    }
    
    /**
     * Checks if the item size is allowed.
     * 
     * @param itemSize The item size to check, as integer value dimension size
     */
    public static final void checkAllowedItemSize(int itemSize) {
        for (int i=0;i<ZOOM_SIZES.length;i++)
            if (ZOOM_SIZES[i]==itemSize) return;
        throw new InvalidParameterException("Item size is not valid!");
    }

    /**
     * Checks the zoom level integer value to be a valid value.
     * 
     * FIX IT : zoom level < 0 ? should be < 1 maybe.
     * 
     * @param level The integer zoom level to check.
     */
    public static void checkZoomLevel(int level) {
        if (level<0 || level>ZOOM_SIZES.length-1)
            throw new InvalidParameterException("The zoom level is not available.");
    }
    
    /**
     * Checks if it is possible to do a zoom in action from the provided
     * zoom level value.
     * 
     * @param fromLevel The starting zoom level parameter to check.
     * @return true if it is possible to do a zoom in action, false otherwise.
     */
    public static boolean canZoomIn(int fromLevel) {
        return fromLevel<ZOOM_SIZES.length-1;
    }
    
    /**
     * Checks if it is possible to do a zoom out action from the provided
     * zoom level value.
     * 
     * @param fromLevel The starting zoom level parameter to check.
     * @return true if it is possible to do a zoom out action, false otherwise.
     */
    public static boolean canZoomOut(int fromLevel) {
        return fromLevel>0;
    }
}
