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

package it.mbcraft.fileplaza.service.drivewatch;

import it.mbcraft.fileplaza.service.drivewatch.os.DriveIdentifier;
import it.mbcraft.fileplaza.service.drivewatch.os.DriveListUpdaterFactory;
import it.mbcraft.fileplaza.service.drivewatch.os.IDriveListUpdater;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 * This class is the actual Runnable implementation of the DriveWatchService.
 * Checks for drive changes and updates the drive list accordingly.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DriveWatchServiceRunnable implements Runnable {

    private boolean running = false;
    
    private final ArrayList<FileStore> lastFileStoreList = new ArrayList<>();
    private final ArrayList<FileStore> currentFileStoreList = new ArrayList<>();
    
    private final IDriveListUpdater myDriveListUpdater;
    private final List<DriveIdentifier> myDrives;
    
    /**
     * 
     * This Runnable is used inside the DriveWatchService thread to monitor for drive changes.
     * 
     * @param driveListProperty The drive list property to be updated.
     */
    public DriveWatchServiceRunnable(List<DriveIdentifier> driveList) {
        myDrives = driveList;
        myDriveListUpdater = DriveListUpdaterFactory.getDriveListUpdater();
    }
    
    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                //wait 500ms for the next poll
                Thread.sleep(500);
                
                if (running)
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        checkForDriveChanges();
                    }
                });
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(DriveWatchServiceRunnable.class.getName()).log(Level.SEVERE, null, ex);
                running = false;
            }
        }
    }
    
    private void checkForDriveChanges() {
        Iterator<FileStore> itfs = FileSystems.getDefault().getFileStores().iterator();
        currentFileStoreList.clear();
        while (itfs.hasNext()) {
            currentFileStoreList.add(itfs.next());
        }
        
        //just check the list size
        //should be improved checking the drive names
        if (currentFileStoreList.size()!=lastFileStoreList.size())
            updateDriveList();
        
        //update the last FileStore list for the next check
        lastFileStoreList.clear();
        lastFileStoreList.addAll(currentFileStoreList); 
    }
    
    private void updateDriveList() {
        //System.out.println("The drive list is changed!");
        myDriveListUpdater.updateDriveList(currentFileStoreList, myDrives);
    }
    
    /**
     * Checks if this runnable is being used.
     * 
     * @return true if this runnable is actually used by a thread, false otherwise
     */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Changes the flag for halting this Runnable (inside a Thread).
     */
    public void halt() {
        running = false;
    }
    
}
