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
import java.io.File;
import java.util.List;
import java.util.Map;

/**
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
