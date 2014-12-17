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
    
    public static void reset(int numColumns) {
        xItems = numColumns;
        currentX = 0;
        currentY = 0;
    }
    
    public static int X() {        
        return currentX++;
    }
    
    public static int Y() {
        if (currentX == xItems) {
            currentX = 0;
            return currentY++;
        } else {
            return currentY;
        }
            
    }
    
}
