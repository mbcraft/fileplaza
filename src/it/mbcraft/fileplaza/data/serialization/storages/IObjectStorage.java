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

import it.mbcraft.fileplaza.data.serialization.engines.IStreamSerializer;
import java.util.List;

/**
 * This interface represents a storage for models. Multiple models can
 * be stored within the same IObjectStorage.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 * @param <T> The object type to store
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
     * 
     * @return true if the rename is successful, false otherwise
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
    T find(String key,IStreamSerializer sz);

    /**
     * Store a model inside this storage. The model is copied and the name is
     * left unaltered.
     *
     * @param data The model to saveOrUpdate.
     * @param key The key of the model to serialize
     * @param sz The serializer used to convert data from object instances to binary data
     * 
     */
    void saveOrUpdate(T data, String key, IStreamSerializer sz);
    
    /**
     * Returns all the models inside this storage.
     * 
     * @param sz The serializer used to convert files from binary data to object instances
     * @return The list of all the saveOrUpdated objects.
     */
    List<T> findAll(IStreamSerializer sz);
    
}
