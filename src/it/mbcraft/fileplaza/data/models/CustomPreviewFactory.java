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
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CustomPreviewFactory {
        
    private final Set<File> homeFiles = new HashSet<>();
    private BufferedImage lastScreenshot = null;
    private final Clipboard clipboard;

    
    public CustomPreviewFactory() {
        clipboard = Clipboard.getSystemClipboard();
    }
    
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
    
    private BufferedImage getLastScreenshot() {
        return lastScreenshot;

    }
    
    public BufferedImage fetchCustomPreview() {
        waitForScreenshot();
        return getLastScreenshot();
    }
    
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
    
    private boolean hasNewFiles() {
        File home = FileUtils.getUserHomeDir();
        File[] files = home.listFiles();
        if (files.length>homeFiles.size())
            return true;
        else
            return false;
    }
    
    private boolean hasImageInClipboard() {
        return clipboard.hasImage();
    }
    
    private void updateLastScreenshotFromClipboard() {
        lastScreenshot = SwingFXUtils.fromFXImage(clipboard.getImage(),null);
    }
    
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
