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

package it.mbcraft.fileplaza.data.serialization.managers.meta;

import it.mbcraft.fileplaza.data.models.LabelSet;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.LocalDirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.IStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.StorageConstants;
import it.mbcraft.fileplaza.utils.DigestUtils;
import java.io.File;

/**
 * LabelSet Model manager implementation.
 * 
 */
public class LabelSetManager extends AbstractModelManager<LabelSet> {

    public LabelSetManager(String prefix) {
        super(new LocalDirObjectStorage(new File(prefix+StorageConstants.DEFAULT_LABEL_SETS_PATH)));
    }

    @Override
    public IStreamSerializer getSerializer() {
        XMLStreamSerializer xs = new XMLStreamSerializer();
        xs.alias(getRootName(), LabelSet.class);
        return xs;
    }

    @Override
    public Class getDataClass() {
        return LabelSet.class;
    }
    
    @Override
    public String getRootName() {
        return "labelSet";
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public String getModelKey(LabelSet obj) {
        return DigestUtils.getMD5DigestForString(obj.getSetName())+".xml";
    }
    
    
    
}
