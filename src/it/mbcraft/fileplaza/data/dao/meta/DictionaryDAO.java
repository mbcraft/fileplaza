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

package it.mbcraft.fileplaza.data.dao.meta;

import it.mbcraft.fileplaza.data.models.Dictionary;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.meta.DictionaryManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * This class implements the DAO for Dictionary class.
 * It actually uses DirObjectStorage through DictionaryManager as a 
 * storage engine for Dictionary instances.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DictionaryDAO implements IDictionaryDAO {

    private static DictionaryDAO instance_;
    
    private final AbstractModelManager sz;
    private List<Dictionary> dictionaries = new ArrayList();
    

    public static DictionaryDAO getInstance() {
        if (instance_ == null)
            instance_ = new DictionaryDAO();
        return instance_;
    }
    
    private DictionaryDAO() {
        sz = new DictionaryManager("");
        reloadAll();
    }
    
    /**
     *  Reloads all the dictionary from storage.
     */
    private void reloadAll() {
        dictionaries = sz.findAll();
    }
        
    /**
     * Finds a dictionary title for a word, or null if no dictionary is found 
     * @param st The string to look for
     * @return The title of the dictionary, or null if no dictionaries are found
     */
    @Override
    public String findDictionaryTitleForWord(String st) {
        for (Dictionary d : dictionaries) {
            if (d.isEnabled()) {
                for (Entry<String,String> en : d.getEntries()) {
                    if (en.getValue().equals(st) || en.getKey().equals(st)) {
                        return d.getShortTitle();
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public Entry<String,String> findEntryFromEnabledDictionaries(String singularOrPlural) {
        for (Dictionary d : dictionaries) {
            if (d.isEnabled()) {
                Entry<String,String> result = d.findEntryFromWord(singularOrPlural);
                if (result != null) return result;
            }
        }
        return null;
    }
        
    @Override
    public List<Dictionary> findAll() {
        return sz.findAll();
    }
    
    @Override
    public Dictionary importFromFile(File f) {
        Dictionary d = (Dictionary) sz.loadFrom(f);
        d.setEnabled(false);
        return d;
    }

    @Override
    public void replaceAll(List<Dictionary> dicts) {
        sz.deleteAll();
        for (Dictionary d : dicts)
            sz.saveOrUpdate(d);
    }
    
    @Override
    public void save(Dictionary dict) {
        sz.saveOrUpdate(dict);
    }
    
}
