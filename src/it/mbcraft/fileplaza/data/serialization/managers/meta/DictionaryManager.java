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

package it.mbcraft.fileplaza.data.serialization.managers.meta;

import it.mbcraft.fileplaza.data.models.Dictionary;
import it.mbcraft.fileplaza.data.serialization.storages.DirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.StorageHelper;
import it.mbcraft.fileplaza.utils.DigestUtils;
import java.io.File;

/**
 * This class defines where and how Dictionary instances are finded and saveToOrUpdated.
 * 
 */
public class DictionaryManager extends AbstractModelManager<Dictionary> {

    public DictionaryManager(String prefix) {
        super(new DirObjectStorage(new File(prefix+StorageHelper.DEFAULT_DICTIONARIES_PATH)));
    }

    @Override
    public ISerializer getSerializer() {
        XMLSerializer xs = new XMLSerializer();
        xs.alias(getRootName(), Dictionary.class);
        xs.alias("entry", Dictionary.DictionaryEntry.class);
        return xs;
    }

    @Override
    public Class getDataClass() {
        return Dictionary.class;
    }

    @Override
    public String getRootName() {
        return "dictionary";
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public String getModelKey(Dictionary obj) {
        return DigestUtils.getMD5DigestForString(obj.getShortTitle())+".xml";
    }
    
}
