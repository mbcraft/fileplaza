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

package it.mbcraft.fileplaza.data.serialization.managers;

import it.mbcraft.fileplaza.data.serialization.storages.IObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import java.io.File;
import java.util.List;

/**
 * This abstract class defines how a model is serialized. 
 * It builds upon IObjectStorage to provide a cleaner interface.
 * 
 * @param <T> The model class to save or read
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 */
public abstract class AbstractModelManager<T> {
    
    private final IObjectStorage storage;
    
    /**
     * Protected constructor. Only one class is handled by one 'Manager'.
     * 
     * @param _storage The storage instance to use for this manager
     */
    protected AbstractModelManager(IObjectStorage _storage) {
        
        storage = _storage;
    }
        
    /**
     * Returns the storage for this model
     * 
     * @return Returns the storage for this model
     */
    protected IObjectStorage getStorage() {
        return storage;
    }
    
    /**
     * Returns true if the model serialized is a singleton, 
     * false otherwise, eg there is only one model to read or save.
     * 
     * @return true if the model class is only one, false otherwise.
     */
    public abstract boolean isSingleton();
    
    /**
     * Returns this object key inspecting the object data
     * 
     * @param obj The object to inspect
     * @return The string key of this object
     */
    public abstract String getModelKey(T obj);
    
    /**
     * This method has been added to enable AbstractModelManager automatic detection.
     * 
     * @return The root element name of this serialized model class 
     */
    protected abstract String getRootName();
        
    /**
     * Returns the serialized class
     * 
     * @return The serialized class
     */
    public abstract Class getDataClass();
    
    /**
     * Returns the configured serializer object used to read and
     * write model instances.
     * 
     * @return The serializer to be used
     */
    public abstract ISerializer getSerializer();
    
    /**
     * Deletes all the object of this kind.
     */
    public void deleteAll() {
        storage.deleteAll();
    }
    
    /**
     * Renames an object from an old key to a new one.
     * 
     * @param oldKey The old object key
     * @param newKey The new object key
     */
    public void rename(String oldKey,String newKey) {
        storage.rename(oldKey, newKey);
    }
    
    /**
     * Saves this model
     * 
     * @param model The object to saveToOrUpdate
     * @return The key of the saveTod object
     */
    public String saveOrUpdate(T model) {
        String key = getModelKey(model);
        storage.saveOrUpdate(model, key, getSerializer());
        return key;
    }
    
    /**
     * Delete the model identified by key
     * 
     * @param key The key of the model to deleteByKey
     */
    public void deleteByKey(String key) {
        storage.deleteByKey(key);
    }
    
    /**
     * Checks if the object with this key is already serialized.
     * 
     * @param key The key of the object.
     * @return true if the object with this key has been serialized.
     */
    public boolean hasKey(String key) {
        return storage.hasKey(key);
    }
    
    /**
     * Returns all the objects of this kind.
     * 
     * @return The list of all the objects for this serializer.
     */
    public List<T> findAll() {
        return storage.findAll(getSerializer());
    }
    
    /**
     * Loads the object with this key from the storage.
     * 
     * @param key The key of the object to find
     * 
     * @return The finded object
     */
    public T find(String key) {
        return (T) storage.find(key, getSerializer());
    }
    
    /**
     * Reads a single object from a file
     * 
     * @param source The file to deserialize the object from
     * @return The object deserializeed
     */
    public T loadFrom(File source) {
        ISerializer ser = getSerializer();
        Object ob = ser.deserialize(source);
        
        if (ob==null) {
            //...
            return null;
        }
        if (ob.getClass()==getDataClass())
            return (T)ob;
        else
            throw new IllegalStateException("This serializer does not support class : "+ob.getClass().getName());
    }
    
    /**
     * Save an object to a file. The format depends by the serializer.
     * 
     * @param toSave The object to save or update
     * @param dest The file used to save the object
     */
    public void saveTo(T toSave,File dest) {
        if (toSave.getClass()!=getDataClass())
            throw new IllegalStateException("This serializer does not support class : "+toSave.getClass().getName());
        ISerializer ser = getSerializer();
        ser.serialize(toSave, dest);
    }

    /**
     * Returns this model singleton
     * 
     * TO FIX : maybe check that isSingleton is true
     * 
     * @return The singleton model for this serializer
     */
    public T getSingleton() {
        return find(getModelKey(null));
    }
}
