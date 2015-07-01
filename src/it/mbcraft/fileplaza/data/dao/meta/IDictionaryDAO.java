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

package it.mbcraft.fileplaza.data.dao.meta;

import it.mbcraft.fileplaza.data.models.Dictionary;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * This is the storage independent interface for Dictionary DAOs.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IDictionaryDAO {

    List<Dictionary> findAll();

    /**
     * Finds a dictionary title for a word, or null if no dictionary is found
     * @param st The string to look for
     * @return The title of the dictionary, or null if no dictionaries are found
     */
    String findDictionaryTitleForWord(String st);

    Map.Entry<String, String> findEntryFromEnabledDictionaries(String singularOrPlural);

    Dictionary importFromFile(File f);

    void replaceAll(List<Dictionary> dicts);

    void save(Dictionary dict);
    
}
