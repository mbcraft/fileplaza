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

package it.mbcraft.fileplaza.data.models;

import it.mbcraft.fileplaza.utils.FileUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.Clipboard;
import javax.imageio.ImageIO;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.input.ClipboardContent;

/**
 * Detects for image in the clipboard or new file added to user home.
 * Factory class for screen shots.
 * 
 * FIX IT : should be a singleton
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CustomPreviewFactory {
        
    private final Set<File> homeFiles = new HashSet<>();
    private BufferedImage lastScreenshot = null;
    private final Clipboard clipboard;

    /**
     * Creates an instance of this screen shot factory.
     */
    public CustomPreviewFactory() {
        clipboard = Clipboard.getSystemClipboard();
    }
    
    /**
     * Waits until a screen shot is found.
     */
    private void waitForScreenshot() {
        setupBeforeScreenshot();
        
        boolean screenshotFound = false;
        while (!screenshotFound) {
            try {
                Thread.sleep(500);
                if (hasNewFiles()) {
                    updateLastScreenshotFromFile();
                    screenshotFound = true;
                } else if (hasImageInClipboard()) {
                    updateLastScreenshotFromClipboard();
                    screenshotFound = true;
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(CustomPreviewFactory.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalStateException("Unable to create a custom preview.");
            } 
        }

    }
    
    /**
     * Gets the last screen shot picked as a BufferedImage
     * 
     * @return the last screen shot
     */
    private BufferedImage getLastScreenshot() {
        return lastScreenshot;

    }
    
    /**
     * Waits until a screen shot is not done.
     * 
     * @return a BufferedImage containing the screen shot
     */
    public BufferedImage fetchCustomPreview() {
        waitForScreenshot();
        return getLastScreenshot();
    }
    
    /**
     * Does some setup before attempting to detect a new screen shot
     */
    private void setupBeforeScreenshot() {
        lastScreenshot = null;
        //Update home files
        File home = FileUtils.getUserHomeDir();
        File[] files = home.listFiles();
        homeFiles.clear();
        homeFiles.addAll(Arrays.asList(files));
        //Empty clipboard
        clipboard.setContent(new ClipboardContent());
    }
    
    /**
     * Checks if the user home has new files since the last setup.
     * This is done because in some linux distros saves the screen shot directly 
     * inside the user home.
     * 
     * @return true if the home directory has new files, false otherwise
     */
    private boolean hasNewFiles() {
        File home = FileUtils.getUserHomeDir();
        File[] files = home.listFiles();
        if (files.length>homeFiles.size())
            return true;
        else
            return false;
    }
    
    /**
     * Checks if there is an image inside the clipboard
     * 
     * @return true if there is an image inside the clipboard, false otherwise
     */
    private boolean hasImageInClipboard() {
        return clipboard.hasImage();
    }
    
    /**
     * Updates the last screen shot image picking it from the system clipboard.
     */
    private void updateLastScreenshotFromClipboard() {
        lastScreenshot = SwingFXUtils.fromFXImage(clipboard.getImage(),null);
    }
    
    /**
     * Updates the last screen shot image picking it from the home directory as an image file
     */
    private void updateLastScreenshotFromFile() {
        File home = FileUtils.getUserHomeDir();
        File[] files = home.listFiles();
        Set<File> foundFiles = new HashSet(Arrays.asList(files));
        foundFiles.removeAll(homeFiles);
        if (foundFiles.size()==1) {
            File screenshotFile = foundFiles.iterator().next();
            String extension = FileUtils.getExtensionFromFile(screenshotFile).toLowerCase();
            if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("jfif") || extension.equals("png") || extension.equals("gif") || extension.equals("gif")) {
                try {
                    FileUtils.waitForStableSize(screenshotFile);
                    lastScreenshot = ImageIO.read(screenshotFile);
                    screenshotFile.delete();
                } catch (IOException ex) {
                    Logger.getLogger(CustomPreviewFactory.class.getName()).log(Level.SEVERE, null, ex);
                    throw new IllegalStateException("Errore nella lettura dell'immagine : "+screenshotFile.getName());
                }
            } else
                throw new IllegalStateException("Unable to open screenshot as image : "+screenshotFile.getName());
        } else
            throw new IllegalStateException("Unable to identify a single screenshot : "+homeFiles.size());
    }
    

}
