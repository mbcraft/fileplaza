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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ZoomHelper {
    
    private static final int ZOOM_SIZES[] = {16,24,32,48,96,128,256};
    
    public static int getMinItemSize() {
        return ZOOM_SIZES[getMinLevelIndex()];
    }    
    
    public static int getMaxItemSize() {
        return ZOOM_SIZES[getMaxLevelIndex()];
    }
    
    public static int getMinLevelIndex() {
        return 0;
    }
    
    public static int getMaxLevelIndex() {
        return ZOOM_SIZES.length-1;
    }
    
    public static final int getSizeFromZoomLevel(int zoomLevel) {
        return ZOOM_SIZES[zoomLevel];
    }
    
    public static final void checkAllowedItemSize(int itemSize) {
        for (int i=0;i<ZOOM_SIZES.length;i++)
            if (ZOOM_SIZES[i]==itemSize) return;
        throw new InvalidParameterException("Item size is not valid!");
    }

    public static void checkZoomLevel(int level) {
        if (level<0 || level>ZOOM_SIZES.length-1)
            throw new InvalidParameterException("The zoom level is not available.");
    }
    
    public static boolean canZoomIn(int fromLevel) {
        return fromLevel<ZOOM_SIZES.length-1;
    }
    
    public static boolean canZoomOut(int fromLevel) {
        return fromLevel>0;
    }
}
