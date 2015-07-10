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

package it.mbcraft.fileplaza.data.serialization.storages;

import it.mbcraft.fileplaza.data.serialization.engines.file.IFileSerializer;
import it.mbcraft.fileplaza.data.serialization.engines.stream.IStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.engines.stream.ImageStreamSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This implementation of IObjectStorage interface stores all the data objects
 * inside files in a single folder, using the provided key as a file name
 * and a default file extension.
 * Permission check is done during creation.
 * 
 */
public class DirObjectStorage implements IObjectStorage {
    
    private static final FileOnlyFileFilter FILES_ONLY = new FileOnlyFileFilter();
    
    private final File _rootDir;
    
    /**
     * Creates a new storage using a directory as root.
     * 
     * @param rootDir The root of the storage.
     */
    public DirObjectStorage(File rootDir) {
        checkParameterNotNull(rootDir);
        
        if (rootDir.isDirectory()) {
            _rootDir = rootDir;

            recursiveCheckPermissions(_rootDir);
        } else 
            throw new InvalidParameterException("Storage works only with a root dir! : "+rootDir.getAbsolutePath());
    }
    
    /**
     * Checks if the file is not null. If it is, throws an InvalidParameterException
     * 
     * @param f The file to check.
     */
    private void checkParameterNotNull(Object p) {
        if (p==null)
            throw new InvalidParameterException("null is not allowed for parameter");
    }
    
    /**
     * Recursively checks for permissions for each file and folder found.
     * 
     * @param dir The dir to check
     */
    private void recursiveCheckPermissions(File dir) {
        if (!dir.isDirectory())
            throw new IllegalStateException("Only dirs are allowed as parameters");
           
        if (!dir.canWrite())
            throw new IllegalStateException("Dirs must be writable.");
        if (!dir.canRead())
            throw new IllegalStateException("Dirs must be readable.");
        
        File[] allFiles = dir.listFiles();

        for (File f : allFiles) {
            if (f.getName().equals(".") || f.getName().equals(".."))
                continue;
            
            if (!f.canRead() || !f.canWrite())
                throw new IllegalStateException("All files must be readable and writable.");
            if (f.isDirectory())
                recursiveCheckPermissions(f);
            else if (!f.isFile()) 
                throw new IllegalStateException("Only files and dirs are allowed inside storage.");
        }
        
    }
    
    /**
     * Deletes all the objects inside this storage.
     */
    @Override
    public void deleteAll() {
        recursiveDelete(_rootDir, false);
    }
    
    /**
     * Recursively delete all the storage files and folders.
     * 
     * @param dir The dir which content must be deleted
     * @param deleteDir true if the dir itself must be deleted, false otherwise.
     */
    private void recursiveDelete(File dir, boolean deleteDir) {
        File[] files = dir.listFiles();
        for (File f : files) {
            
            if (f.isDirectory())
                recursiveDelete(f,true);
            f.delete();
        }
        if (deleteDir)
            dir.delete();
    }

    /**
     * Store an object inside this storage, which is mapped to a file.
     * 
     * @param model The object to saveToOrUpdate
     * @param key The key to use for saving this object
     * @param sz The serializer used to convert object instances to binary data
     */
    @Override
    public void saveOrUpdate(Object model, String key, IStreamSerializer sz) {
        checkParameterNotNull(model);
        checkParameterNotNull(key);
        checkParameterNotNull(sz);

        File dest = new File(_rootDir,key);
        try (OutputStream o = new FileOutputStream(dest)) {
            sz.serialize(model,o );
        } catch (IOException ex) {
            Logger.getLogger(DirObjectStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
                
    /**
     * Deletes an object from the storage.
     * 
     * @param key The key of the object to delete, which is mapped to a file
     */
    @Override
    public void deleteByKey(String key) {
        checkParameterNotNull(key);
        
        File dest = new File(_rootDir,key);
        dest.delete();
    }
    
    /**
     * Checks if this storage has an object with a specified key, mapping
     * it to a file inside the storage
     * 
     * @param key The key to check
     * 
     * @return true if an object with this key exists, false otherwise
     */
    @Override
    public boolean hasKey(String key) {
        checkParameterNotNull(key);
        
        File dest = new File(_rootDir,key);
        return dest.exists();
    }
    
    /**
     * Retrieves the object with the specified key inside this storage.
     * The hasKey method must be called before using this one.
     * 
     * TO FIX : rename to load, or make it return null if key is not valid
     * 
     * 
     * @param key The name of the object to find, which is mapped to a file.
     * @param sz The serializer used to convert binary data to object instances
     * @return The object found
     */
    @Override
    public Object find(String key, IStreamSerializer sz) {
        checkParameterNotNull(key);
        checkParameterNotNull(sz);
        
        File dest = new File(_rootDir,key);
        try (InputStream is = new FileInputStream(dest)) {
            return sz.deserialize(is);
        } catch (IOException ex) {
            Logger.getLogger(DirObjectStorage.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Object with key "+key+" not found.");
        }
    }
    
    /**
     * Rename the object with the old key to a new key
     * 
     * @param oldKey The old object key
     * @param newKey The new object key
     * @return true if rename was successful, false otherwise
     */
    @Override
    public boolean rename(String oldKey, String newKey) {
        checkParameterNotNull(oldKey);
        checkParameterNotNull(newKey);
        
        File f = new File(_rootDir,oldKey);
        
        return f.renameTo(new File(_rootDir,newKey));
    }

    /**
     * Find all objects inside this object storage.
     * 
     * @param sz The serializer used to convert the object from binary to object instances
     * @return The list of objects.
     */
    @Override
    public List findAll(IStreamSerializer sz) {
        checkParameterNotNull(sz);
        
        List result = new ArrayList();
        File[] allObjects = _rootDir.listFiles(FILES_ONLY);

        for (File f : allObjects) {
            try (InputStream is = new FileInputStream(f)) {
                result.add(sz.deserialize(is));
            } catch (IOException ex) {
                Logger.getLogger(DirObjectStorage.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalStateException("Unable to read object from file : "+f.getName());
            } 
        }
        
        return result;
    }
}
