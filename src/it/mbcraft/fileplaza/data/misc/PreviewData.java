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

import java.io.File;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewData {
    
    private final File myFile;
    private final String myPreviewKey;
    
    public PreviewData(File f,String previewKey) {
        myFile = f;
        myPreviewKey = previewKey;
    }
    
    public File getFile() {
        return myFile;
    }
    
    public String getPreviewKey() {
        return myPreviewKey;
    }
        
    public boolean hasValidPreviewKey() {
        return myPreviewKey!=null && !myPreviewKey.equals("");
    }
}
