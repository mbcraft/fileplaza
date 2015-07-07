/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.service.drivewatch.os;

import it.mbcraft.fileplaza.service.drivewatch.os.DriveIdentifier.DriveType;
import java.io.IOException;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of IDriveListUpdater for the Linux os.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LinuxDriveListUpdater implements IDriveListUpdater {

    @Override
    public void updateDriveList(Iterable<FileStore> stores,List<DriveIdentifier> driveList) {
        
            List<DriveIdentifier> drives = getValidDrives(stores);
            
            driveList.clear();
            driveList.addAll(drives);
        }
        
    /**
     * Returns the list of valid drives given an Iterable FileStore list.
     * 
     * @param stores The iterable file store.
     * 
     * @return The list of drives.
     */
    private List<DriveIdentifier> getValidDrives(Iterable<FileStore> stores) {

        List<DriveIdentifier> drives = new ArrayList<>();
        
        Iterator<FileStore> it = stores.iterator();
        while (it.hasNext()) {
            FileStore fs = it.next();
            String deviceName = getDeviceNameFromFileStore(fs);
            if (deviceName.startsWith("/dev/sd") || deviceName.startsWith("/dev/hd")) {
                drives.add(createDriveIdentifierFromFileStore(fs));
            }
        }
        
        return drives;
    }
    
    /**
     * 
     * Creates a DriveIdentifier instance containing informations about a drive given a FileStore instance.
     * 
     * @param fs The FileStore instance.
     * @return The name of the device.
     */
    private DriveIdentifier createDriveIdentifierFromFileStore(FileStore fs) {
        DriveIdentifier di;
        try {
            di = new DriveIdentifier(getMountPointFromFileStore(fs),getMountPointFromFileStore(fs),fs.type(),fs.isReadOnly(),fs.getUnallocatedSpace(),fs.getUsableSpace(),fs.getTotalSpace(),DriveType.UNKNOWN);
        } catch (IOException ex) {
            Logger.getLogger(LinuxDriveListUpdater.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to create DriveIdentifier");
        }   
        return di;
    }
    
    /**
     * Returns the device name from the FileStore.
     * 
     * @param fs The FileStore instance.
     * @return The device name.
     */
    private String getMountPointFromFileStore(FileStore fs) {
        String fullToString = fs.toString();
        String tokens[] = fullToString.split(" ");
        return tokens[0];
    }
    
    /**
     * Returns the mount point path from the FileStore.
     * 
     * @param fs The FileStore instance
     * @return The mount point path.
     */
    private String getDeviceNameFromFileStore(FileStore fs) {
        String fullToString = fs.toString();
        String tokens[] = fullToString.split(" ");
        return tokens[1].substring(1,tokens[1].length()-1);
    }
    
}