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

import it.mbcraft.fileplaza.data.dao.fs.FileElementDAO;
import it.mbcraft.fileplaza.data.models.ChangeHistory;
import it.mbcraft.fileplaza.data.models.tags.Tag;
import it.mbcraft.fileplaza.data.models.FileElement;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.LocalDirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.IStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.StorageConstants;
import java.io.File;

/**
 * FileElement model manager implementation.
 * 
 */
public class FileElementManager extends AbstractModelManager<FileElement> {

    public FileElementManager(String prefix) {
        super(new LocalDirObjectStorage(new File(prefix+StorageConstants.DEFAULT_FILE_ELEMENTS_PATH)));
    }

    @Override
    public IStreamSerializer getSerializer() {
        XMLStreamSerializer xs = new XMLStreamSerializer();
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
