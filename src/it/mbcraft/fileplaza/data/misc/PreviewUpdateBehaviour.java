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

package it.mbcraft.fileplaza.data.misc;

import java.security.InvalidParameterException;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewUpdateBehaviour {
    public static boolean needsUpdate(PreviewData oldData,PreviewData newData) {
        if (oldData==null || newData==null)
            throw new InvalidParameterException("Old data or new data is null!");
        
        if (oldData.getFile()==null || newData.getFile()==null)
            return false;
        
        if (oldData.getFile().equals(newData.getFile()) && oldData.getPreviewKey()!=newData.getPreviewKey())
            return true;
        
        return false;
    }
}
