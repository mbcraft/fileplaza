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

package it.mbcraft.fileplaza.ui.common.helpers;

import java.security.InvalidParameterException;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ZoomHelper {
    
    private static final int ZOOM_SIZES[] = {16,24,32,48,96,128,256};
    
    public static int getMinSize() {
        return ZOOM_SIZES[getMinLevel()];
    }    
    
    public static int getMaxSize() {
        return ZOOM_SIZES[getMaxLevel()];
    }
    
    public static int getMinLevel() {
        return 0;
    }
    
    public static int getMaxLevel() {
        return ZOOM_SIZES.length-1;
    }
    
    public static final int getSizeFromZoomLevel(int zoomLevel) {
        return ZOOM_SIZES[zoomLevel];
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
