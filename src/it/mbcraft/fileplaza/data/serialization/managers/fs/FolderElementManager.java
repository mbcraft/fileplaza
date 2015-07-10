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

package it.mbcraft.fileplaza.data.serialization.managers.fs;

import it.mbcraft.fileplaza.data.dao.fs.FolderElementDAO;
import it.mbcraft.fileplaza.data.models.ChangeHistory;
import it.mbcraft.fileplaza.data.models.FolderElement;
import it.mbcraft.fileplaza.data.models.Tag;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.LocalDirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.IStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.StorageConstants;
import java.io.File;

/**
 * FolderElement model manager implementation. 
 */
public class FolderElementManager extends AbstractModelManager<FolderElement> {

    public FolderElementManager(String prefix) {
        super(new LocalDirObjectStorage(new File(prefix+StorageConstants.DEFAULT_FOLDER_ELEMENTS_PATH)));
    }
    
    @Override
    public IStreamSerializer getSerializer() {
        XMLStreamSerializer xs = new XMLStreamSerializer();
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
