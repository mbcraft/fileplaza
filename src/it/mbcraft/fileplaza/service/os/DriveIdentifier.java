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

package it.mbcraft.fileplaza.service.os;

import it.mbcraft.fileplaza.data.storage_device.StorageInfo;
import it.mbcraft.fileplaza.data.storage_device.StorageInfoUtils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * This class contains data about drives. It is used as information container
 * for drives.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DriveIdentifier {
    
    private final StorageInfo myStorageInfo;
    private final String myDeviceName,myMountPoint,myFileSystem;
    private final boolean myReadOnly;
    private final long myUnallocatedSpace,myUsableSpace,myTotalSpace;
    
    public DriveIdentifier(String deviceName,String mountPointOrRoot,String fileSystem,boolean readOnly,long unallocatedSpace,long usableSpace,long totalSpace) {
        
        myDeviceName = deviceName;
        myMountPoint = mountPointOrRoot;
        myFileSystem = fileSystem;
        myReadOnly = readOnly;
        
        myUnallocatedSpace = unallocatedSpace;
        myUsableSpace = usableSpace;
        myTotalSpace = totalSpace;
        
        try {
            myStorageInfo = StorageInfoUtils.getStorageInfoData(new File(mountPointOrRoot));
        } catch (IOException ex) {
            Logger.getLogger(DriveIdentifier.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to get StorageInfo for this root : "+mountPointOrRoot);
        }
    }
    
    public String getDeviceName() {
        return myDeviceName;
    }
    
    public String getMountPoint() {
        return myMountPoint;
    }
    
    public String getFileSystem() {
        return myFileSystem;
    }
    
    public boolean isReadOnly() {
        return myReadOnly;
    }

    public StorageInfo getStorageInfo() {
        return myStorageInfo;
    }
    
    public long getTotalSpace() {
        return myTotalSpace;
    }
    
    public long getUsableSpace() {
        return myUsableSpace;
    }
    
    public long getUnallocatedSpace() {
        return myUnallocatedSpace;
    }
}
