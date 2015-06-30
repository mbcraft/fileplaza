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

package it.mbcraft.fileplaza.service.drivewatch.os;

import java.io.IOException;
import java.nio.file.FileStore;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Windows drive list updater.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class WindowsDriveListUpdater implements IDriveListUpdater {

    @Override
    public void updateDriveList(Iterable<FileStore> stores,List<DriveIdentifier> driveList) {
        Iterator<FileStore> fileStores = stores.iterator();
        
        List<DriveIdentifier> drives = new ArrayList<>();
        //all drives are added to the list
        while (fileStores.hasNext()) {
            FileStore fs = fileStores.next();
            try {
                DriveIdentifier di = new DriveIdentifier(getDeviceNameFromFileStore(fs),getMountPointFromFileStore(fs),fs.type(),fs.isReadOnly(),fs.getUnallocatedSpace(),fs.getUsableSpace(),fs.getTotalSpace(),DriveIdentifier.DriveType.UNKNOWN);
                drives.add(di);
            } catch (IOException ex) {
                Logger.getLogger(WindowsDriveListUpdater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        driveList.clear();
        driveList.addAll(drives);
    }
    
    /**
     * Returns the device name from the FileStore.
     * 
     * @param fs The FileStore instance.
     * @return The device name.
     */
    private String getDeviceNameFromFileStore(FileStore fs) {
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
    private String getMountPointFromFileStore(FileStore fs) {
        String fullToString = fs.toString();
        String tokens[] = fullToString.split(" ");
        return tokens[1].substring(1,tokens[1].length()-2);
    }
    
}