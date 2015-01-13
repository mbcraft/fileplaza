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

package it.mbcraft.fileplaza.data.serialization.managers.fs;

import it.mbcraft.fileplaza.data.dao.fs.FolderElementDAO;
import it.mbcraft.fileplaza.data.models.ChangeHistory;
import it.mbcraft.fileplaza.data.models.FolderElement;
import it.mbcraft.fileplaza.data.models.Tag;
import it.mbcraft.fileplaza.data.serialization.storages.DirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.StorageHelper;
import java.io.File;

/**
 * This class defines where and how FolderElement instances are finded and saveToOrUpdated.
 */
public class FolderElementManager extends AbstractModelManager<FolderElement> {

    public FolderElementManager(String prefix) {
        super(new DirObjectStorage(new File(prefix+StorageHelper.DEFAULT_FOLDER_ELEMENTS_PATH)));
    }
    
    @Override
    public ISerializer getSerializer() {
        XMLSerializer xs = new XMLSerializer();
        xs.alias(getRootName(), FolderElement.class);
        xs.alias("changeHistory", ChangeHistory.class);
        xs.alias("tag",Tag.class);
        return xs;
    }

    @Override
    public Class getDataClass() {
        return FolderElement.class;
    }

    @Override
    public String getRootName() {
        return "folderElement";
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public String getModelKey(FolderElement obj) {
        obj.checkSha256NotNull();
        String extension;
        if (obj.isIndexNamedByPath()) {
            extension = FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_PATH;
        } else {
            extension = FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_CONTENT;
        }
        return obj.getSha256() + extension;
    }
    
}
