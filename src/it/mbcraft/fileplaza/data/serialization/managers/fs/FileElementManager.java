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

import it.mbcraft.fileplaza.data.dao.fs.FileElementDAO;
import it.mbcraft.fileplaza.data.models.ChangeHistory;
import it.mbcraft.fileplaza.data.models.Tag;
import it.mbcraft.fileplaza.data.models.FileElement;
import it.mbcraft.fileplaza.data.serialization.storages.DirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.StorageHelper;
import java.io.File;

/**
 * This class handles the serialization of generic 
 * 
 */
public class FileElementManager extends AbstractModelManager<FileElement> {

    public FileElementManager(String prefix) {
        super(new DirObjectStorage(new File(prefix+StorageHelper.DEFAULT_FILE_ELEMENTS_PATH)));
    }

    @Override
    public ISerializer getSerializer() {
        XMLSerializer xs = new XMLSerializer();
        xs.alias(getRootName(), FileElement.class);
        xs.alias("tag",Tag.class);
        xs.alias("changeHistory",ChangeHistory.class);
        return xs;
    }

    @Override
    public Class getDataClass() {
        return FileElement.class;
    }

    @Override
    public String getRootName() {
        return "fileElement";
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public String getModelKey(FileElement obj) {
        obj.checkSha256NotNull();
        String extension;
        if (obj.isIndexNamedByPath()) {
            extension = FileElementDAO.FILE_INDEX_EXTENSION_BY_PATH;
        } else {
            extension = FileElementDAO.FILE_INDEX_EXTENSION_BY_CONTENT;
        }
        return obj.getSha256() + extension;
    }
        
}
