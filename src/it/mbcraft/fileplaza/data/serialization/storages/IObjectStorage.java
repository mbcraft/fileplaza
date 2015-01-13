/*
 *  Developed by MBCRAFT. Copyright © 2014-2015. All rights reserved.
 *  This file of source code is property of MBCRAFT (http://www.mbcraft.it). 
 *  Do not sell, do not remove this license note even if you edit this file.
 *  Do not use this source code to develop your own file manager application.
 *  You can reuse part or full files for your own project (eg javafx ui classes)
 *  but keep copyright in files, and please link http://www.mbcraft.it on your
 *  project website.
 *
 *  Thanks
 *
 *  - Marco Bagnaresi
 */

package it.mbcraft.fileplaza.data.serialization.storages;

import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import java.util.List;

/**
 * This interface rapresents a storage for models. Multiple models can
 be stored within the same IObjectStorage.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 * @param <T>
 */
public interface IObjectStorage<T> {

    /**
     * Deletes a model from the storage.
     *
     * @param key The filename of the file to deleteByKey.
     */
    void deleteByKey(String key);

    /**
     * Checks if this storage has a model with key 'key' inside.
     *
     * @param key The key to check
     *
     * @return true if this a model with this key exists inside the storage, 
     * false otherwise.
     */
    boolean hasKey(String key);

    /**
     * Renames a model inside the storage
     * 
     * @param oldKey The old key
     * @param newKey The new key
     */
    boolean rename(String oldKey, String newKey);

    /**
     * Deletes all the entries of the storage.
     */
    void deleteAll();

    /**
     * Retrieves the model specified by key inside this storage.
     *
     * @param key The key of the model to find.
     * @param sz The serializer used to convert files from binary data to object instances
     * @return The model instance with all its data
     */
    T find(String key,ISerializer sz);

    /**
     * Store a model inside this storage. The model is copied and the name is
     * left unaltered.
     *
     * @param data The model to saveOrUpdate.
     * @param key The key of the model to serialize
     * @param sz The serializer used to convert data from object instances to binary data
     * 
     */
    void saveOrUpdate(T data, String key, ISerializer sz);
    
    /**
     * Returns all the models inside this storage.
     * 
     * @param sz The serializer used to convert files from binary data to object instances
     * @return The list of all the saveOrUpdated objects.
     */
    List<T> findAll(ISerializer sz);
    
}
