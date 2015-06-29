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
     * Returns the extension from the full filename string.
     * 
     * @param name The name of the file
     * 
     * @return The extension of this file
     */
    public static String getExtensionFromFilename(String name) {
        return name.substring(name.lastIndexOf(".")+1);
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
            return getExtensionFromFilename(name);
        }
    }
    
    /**
     * Returns the user home directory.
     * 
     * @return The user home directory as a File instance.
     */
    public static File getUserHomeDir() {
        return new File(System.getProperty("user.home"));
    }
    
    /**
     * Returns the current working directory, as a File instance.
     * 
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
    
    /**
     * Waits untile the screenshot is fully written. Maybe can be changed trying to open the file for write instead of checking the file size.
     * 
     * @param screenshotFile The File to check, as a File instance
     */
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
