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

package it.mbcraft.fileplaza.service;

import it.mbcraft.fileplaza.service.os.DriveIdentifier;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DriveWatchService implements IService {
    
    private final DriveWatchServiceRunnable runner;
    private Thread runnerThread = null;
    
    public DriveWatchService(List<DriveIdentifier> driveList) {
        //mandatory check if not previously done
        checkFileStoreToStringBehaviour();
        //ok
        runner = new DriveWatchServiceRunnable(driveList);
    }
    
    @Override
    public String getName() {
        return "DriveWatchService";
    }
    
    public static void checkFileStoreToStringBehaviour() throws IllegalStateException {
        Iterator<FileStore> itfs = FileSystems.getDefault().getFileStores().iterator();
        while (itfs.hasNext()) {
            FileStore fs = itfs.next();
            String fsString = fs.toString();
            if (fsString.indexOf(" (")==-1)
                throw new IllegalStateException("FileStore.toString() does not behave as expected in "+System.getProperty("java.vendor")+" Java "+System.getProperty("java.version"));
        }
    }
    
    @Override
    public void start() {
        runnerThread = new Thread(runner);
        runnerThread.start();
    }
    
    @Override
    public boolean isActive() {
        return runnerThread != null;
    }
    
    @Override
    public void stop() {
        runner.halt();
        try {
            //wait for thread to stop
            runnerThread.join();
            runnerThread = null;
        } catch (InterruptedException ex) {
            Logger.getLogger(DriveWatchService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}