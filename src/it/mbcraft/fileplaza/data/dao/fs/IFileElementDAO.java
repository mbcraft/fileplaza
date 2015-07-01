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

package it.mbcraft.fileplaza.data.dao.fs;

import it.mbcraft.fileplaza.data.models.FileElement;
import java.io.File;
import java.util.List;

/**
 * This is the storage independent interface for storing FileElement
 * objects.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IFileElementDAO {

    /**
     * Find a FileElement by key.
     * 
     * @param key The key of the FileElement, as a string
     * 
     * @return The FileElement instance
     */
    FileElement find(String key);

    /**
     * Returns all the FileElement instances.
     * 
     * @return All the FileElement instances, as a List.
     */
    List<FileElement> findAll();

    /**
     * Checks if a FileElement instance exists with this key
     * 
     * @param key The key to check
     * @return true if this FileElement instance exists, false otherwise
     */
    boolean hasKey(String key);

    /**
     * Changes the FileElement references file path to a new value.
     * This does actually not change the FileElement storage location, it
     * just changes the FileElement pointed file location to a new value.
     * 
     * @param el The FileElement to update
     * @param finalPath The new path of the referenced file
     * @return true if the operation was successful, false otherwise
     */
    boolean move(FileElement el, File finalPath);
    
}
