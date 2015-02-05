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

package it.mbcraft.fileplaza.data.serialization.storages;

import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import it.mbcraft.fileplaza.state.devices.FileOnlyFileFilter;
import java.io.File;
import java.io.FileFilter;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * DirObjectStorage
 
 Current version : basic model saveToOrUpdate, findByKey, findAll, deleteByKey, check, rename and deleteAll.
 * No subdirectories supported for improving structure. All files are copied and
 * only File type is supported. Permission check is done during creation.
 * 
 */
public class DirObjectStorage implements IObjectStorage {
    
    private static final FileFilter FILES_ONLY = new FileOnlyFileFilter();
    
    private final File _rootDir;
    
    /**
     * Creates a new storage using dir as root.
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
     * Deletes all the content of the storage.
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
     * Store a file inside this storage. The file is copied and the name is
     * left unaltered.
     * 
     * @param model The model to saveToOrUpdate
     * @param key The key to use for saving this object
     * @param sz The serializer used to convert binary data to object instances
     */
    @Override
    public void saveOrUpdate(Object model, String key, ISerializer sz) {
        checkParameterNotNull(model);
        checkParameterNotNull(key);
        checkParameterNotNull(sz);

        File dest = new File(_rootDir,key);
        sz.serialize(model, dest);
    }
                
    /**
     * Deletes a file from the storage.
     * 
     * @param key The filename of the file to deleteByKey.
     */
    @Override
    public void deleteByKey(String key) {
        checkParameterNotNull(key);
        
        File dest = new File(_rootDir,key);
        dest.delete();
    }
    
    /**
     * Checks if this storage has a file named fileName inside.
     * 
     * @param key The filename to check
     * 
     * @return true if this filename exists inside the storage, false otherwise.
     */
    @Override
    public boolean hasKey(String key) {
        checkParameterNotNull(key);
        
        File dest = new File(_rootDir,key);
        return dest.exists();
    }
    
    /**
     * Retrieves the file specified by filename saveToOrUpdateOrUpdated inside this storage.
     * 
     * @param key The name of the file to find.
     * @param sz The serializer used to convert binary data to object instances
     * @return The find file in its original position.
     */
    @Override
    public Object find(String key, ISerializer sz) {
        checkParameterNotNull(key);
        checkParameterNotNull(sz);
        
        File dest = new File(_rootDir,key);
        return sz.deserialize(dest);
    }
    
    /**
     * Rename the object with the old key to a new key
     * 
     * @param oldKey The old object key
     * @param newKey The new object key
     * @return true if rename was succesful, false otherwise
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
    public List findAll(ISerializer sz) {
        checkParameterNotNull(sz);
        
        List result = new ArrayList();
        File[] allObjects = _rootDir.listFiles(FILES_ONLY);

        for (File f : allObjects) {
            result.add(sz.deserialize(f));
        }
        
        return result;
    }
}
