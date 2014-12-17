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

package it.mbcraft.fileplaza.utils;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileUtils {

    public static final String NO_EXTENSION = "-";
    
    /**
     * Return the name of the file.
     * 
     * @param f The file, existing and not null
     * @return The name of the file
     */
    public static String getNameFromFile(File f) {
        if (f==null || !f.exists())
            throw new InvalidParameterException("File must exist.");
        if (!f.isFile())
            return f.getName();
        else {
            String name = f.getName();
            return name.substring(0,name.lastIndexOf("."));
        }
    }

    /**
     * Return the extension of the file. Folders return a "no extension"
     * result.
     * 
     * @param f The file or folder to get extension from
     * @return The extension or a "no extension" flag value.
     */
    public static String getExtensionFromFile(File f) {
        if (f==null || !f.exists())
            throw new InvalidParameterException("File must exist.");
        if (!f.isFile())
            return NO_EXTENSION;
        else {
            String name = f.getName();
            return name.substring(name.lastIndexOf(".")+1);
        }
    }
    
    public static File getUserHomeDir() {
        return new File(System.getProperty("user.home"));
    }
    
    /**
     * Help from 
     * https://http://stackoverflow.com/questions/4871051/getting-the-current-working-directory-in-java
     * http://stackoverflow.com/users/505893/bluish
     * 
     * @return The current working directory
     */
    
    public static File getWorkingDir() {
        File f = new File(".");
        return f;
        
    }
    
    public static void waitForStableSize(File screenshotFile) {
        long currentSize = screenshotFile.length();
        int trials = 0;

        while (trials<4) {
            try {
                if (currentSize==screenshotFile.length()) {
                    Thread.sleep(50);
                    trials++;
                } else {
                    trials = 0;
                    currentSize = screenshotFile.length();
                    System.err.println("Unstable file detected ... waiting ...");
                    Thread.sleep(400);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
