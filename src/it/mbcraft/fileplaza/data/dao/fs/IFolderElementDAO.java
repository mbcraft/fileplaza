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

package it.mbcraft.fileplaza.data.dao.fs;

import it.mbcraft.fileplaza.data.models.FolderElement;
import java.util.List;

/**
 * This is the storage independent interface for reading and writing
 * FolderElement s.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IFolderElementDAO {

    /**
     * Find a FolderElement instance by its key.
     * 
     * @param key The key of the FolderElement
     * 
     * @return The FolderElement instance
     */
    FolderElement find(String key);

    /**
     * Returns all the FolderElement instances loaded.
     * 
     * @return The List of all the FolderElement s.
     */
    List<FolderElement> findAll();

    /**
     * Checks if a FolderElement with this key exists.
     * 
     * @param key The string key identifying the FolderElement
     * 
     * @return true if a stored FolderElement with this key exists, false otherwise
     */
    boolean hasKey(String key);
    
}
