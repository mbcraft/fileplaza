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
    private final DriveType myDriveType;
    
    public enum DriveType {
        HDD_MAIN, HDD_OTHER, CD, DVD, MMC, USB_PEN, EXT_HD, TAPE, UNKNOWN
    }
    
    
    public DriveIdentifier(String deviceName,String mountPointOrRoot,String fileSystem,boolean readOnly,long unallocatedSpace,long usableSpace,long totalSpace, DriveType typeHint) {
        
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
        
        myDriveType = guessTypeFromInfo(typeHint);
    }
    
    public String getFriendlyName() {
        return myMountPoint;
    }
    
    private DriveType guessTypeFromInfo(DriveType hint) {
        File f = new File(getMountPoint());
        File DCIM = new File(f,"DCIM");
        if (DCIM.exists()) return DriveType.MMC;
        
        if (getMountPoint().equals("/") || getMountPoint().equals("C:"))
            return DriveType.HDD_MAIN;
        
        if (getMountPoint().equals("D:") || getMountPoint().equals("/var") || getMountPoint().equals("/home") || getMountPoint().equals("/usr"))
            return DriveType.HDD_OTHER;
        
        if (isReadOnly()) {
            if (getTotalSpace()<800*1024*1024) return DriveType.CD;
            else return DriveType.DVD;
        }
        
        return DriveType.USB_PEN;
    }
    
    public DriveType getDriveType() {
        return myDriveType;
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
    
    @Override
    public String toString() {
        return getMountPoint();
    }
}
