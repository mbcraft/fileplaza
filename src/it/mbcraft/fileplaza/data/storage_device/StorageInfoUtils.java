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
package it.mbcraft.fileplaza.data.storage_device;

import it.mbcraft.fileplaza.state.order.FileSortMode;
import it.mbcraft.fileplaza.state.order.FileSortOption;
import it.mbcraft.fileplaza.state.order.FileSorter;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility classes for dealing with StorageInfo
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class StorageInfoUtils {

    /**
     * The default folder for storage info data
     */
    private static final String STORAGE_INFO_DIRECTORY_DEFAULT_NAME = ".storage_info";
    /**
     * The default file name for storage info data
     */
    private static final String STORAGE_INFO_DEFAULT_FILENAME = "data.ini";

    /**
     * Gets the storage device info data (UUID and other info ...). If the info data does not exist
     * and the device is writable, write the storage_info into it. Otherwise
     * return the calculated StorageInfo
     * 
     * @param root The root of this storage device
     * @return The StorageInfo for this storage device
     * @throws IOException If something goes wrong
     */
    public static StorageInfo getStorageInfoData(File root) throws IOException {
        if (hasStorageInfoData(root))
            return loadStorageInfoData(root);
        else if (canSaveStorageInfoData(root)) {
            StorageInfo info = new StorageInfo();
            saveStorageInfoData(root, info);
            return info;
        } else return calculateStorageInfoData(root);
    }
    
    
    /**
     * Calculates the StorageInfo for a non-writable device, given its root.
     * It is guaranteed to remain constant upon calls and on different OSs.
     * @param root The device root path.
     * @return The StorageInfo for this storage device.
     * @throws IOException If something goes wrong.
     */
    public static StorageInfo calculateStorageInfoData(File root) throws IOException {
        File[] files = root.listFiles();
        
        File[] ordered = FileSorter.sort(files, FileSortMode.ALPHABETICAL_ASCENDING, FileSortOption.MIXED);
        StringBuilder buf = new StringBuilder();
        for (File f : ordered) {
            if (f.isDirectory()) {
                buf.append(f.getName());
                buf.append("/");
                buf.append(0);
                buf.append("/");
                buf.append(f.lastModified());
            }
            if (f.isFile()) {
                buf.append(f.getName());
                buf.append("/");
                buf.append(f.length());
                buf.append("/");
                buf.append(f.lastModified());
            }
            buf.append(",");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(buf.toString().getBytes("UTF-8"));
            UUID uuid = UUID.nameUUIDFromBytes(digest);
            Properties pt = new Properties();
            pt.put(StorageInfo.STORAGE_UUID_KEY,uuid.toString());
            pt.put(StorageInfo.READ_ONLY_KEY,"true");
            return new StorageInfo(pt);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(StorageInfoUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to compute SHA-1.");
        }
        
    }
    
    /**
     * Loads the storage info data for a device.
     * 
     * @param root The storage root path
     * @return The StorageInfo loaded from the device.
     * @throws IOException If something wrong occurs.
     */
    public static StorageInfo loadStorageInfoData(File root) throws IOException {
        File storageInfoDir = new File(root, STORAGE_INFO_DIRECTORY_DEFAULT_NAME);
        File storageDataFile = new File(storageInfoDir, STORAGE_INFO_DEFAULT_FILENAME);
        return StorageInfo.loadFrom(storageDataFile);
    }

    /**
     * Returns true if the storage has an info data file.
     * 
     * @param root The root path on a device
     * @return true if the storage info data is found, false otherwise.
     */
    public static boolean hasStorageInfoData(File root) {
        File storageInfoDir = new File(root, STORAGE_INFO_DIRECTORY_DEFAULT_NAME);
        if (storageInfoDir.exists()) {
            File storageDataFile = new File(storageInfoDir, STORAGE_INFO_DEFAULT_FILENAME);
            if (storageDataFile.exists() && storageDataFile.canRead()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a StorageInfo can be saved on a device, eg. if the device is writable
     * 
     * @param root The device root path
     * @return true if the storage info can be saved, false otherwise
     */
    public static boolean canSaveStorageInfoData(File root) {
        return root.canWrite();
    }

    /**
     * Saves the storage info on a device, following the convention
     * 
     * @param root The device root path
     * @param data The StorageInfo to save
     * @throws IOException If something wrong happens.
     */
    public static void saveStorageInfoData(File root, StorageInfo data) throws IOException {

        File storageInfoDir = new File(root, STORAGE_INFO_DIRECTORY_DEFAULT_NAME);
        if (!storageInfoDir.exists() && storageInfoDir.mkdir()) {
            File storageDataFile = new File(storageInfoDir, STORAGE_INFO_DEFAULT_FILENAME);
            if (!storageDataFile.exists() && storageDataFile.createNewFile()) {
                data.saveTo(storageDataFile);
            }
        }
    }
    
    /**
     * Deletes the storage data on a device, given the device root (mainly for testing).
     * 
     * @param root The device root path
     */
    public static void deleteStorageInfoData(File root) {
        File storageInfoDir = new File(root, STORAGE_INFO_DIRECTORY_DEFAULT_NAME);
        File[] files = storageInfoDir.listFiles();
        for (File f : files)
            f.delete();
        storageInfoDir.delete();
    }
}
