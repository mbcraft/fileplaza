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

package it.mbcraft.fileplaza.data.dao.meta;

import it.mbcraft.fileplaza.data.models.Dictionary;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.meta.DictionaryManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * This class contains the main operations done on dictionaries storage.
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
        sz = new DictionaryManager();
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
