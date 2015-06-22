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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import java.util.UUID;

/**
 * This class models data about a storage device. This data is typically
 * used to uniquely identify the storage device, also across different computers.
 * A StorageInfo can be saved on the device on an hidden folder or calculated if
 * a folder can't be written on the storage device.
 * 
 * It always contains a storage device UUID, and sometimes and maybe in the
 * future, additional informations.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class StorageInfo {
    
    public static final String STORAGE_UUID_KEY = "storage_uuid";
    public static final String READ_ONLY_KEY = "read_only";
    
    private final Properties myProperties;
    
    public StorageInfo() {
        myProperties = new Properties();
        myProperties.put(STORAGE_UUID_KEY, UUID.randomUUID().toString());
        myProperties.put(READ_ONLY_KEY, "false");
    }
    
    StorageInfo(Properties pt) {
        myProperties = pt;
    }
    
    public UUID getStorageUUID() {
        return UUID.fromString(myProperties.getProperty(STORAGE_UUID_KEY));
    }
    
    public static StorageInfo loadFrom(File storageFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(storageFile)) {
            Properties pt = new Properties();
            pt.load(fis);
            return new StorageInfo(pt);
        }
    }
    
    public void saveTo(File storageFile) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(storageFile)) {
            myProperties.store(fos, "StorageInfo data");
        }
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof StorageInfo) {
            StorageInfo si = (StorageInfo) other;
            return si.myProperties.equals(myProperties);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.myProperties);
        return hash;
    }
}
