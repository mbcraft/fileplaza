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

package it.mbcraft.fileplaza.data.serialization.managers.meta;

import it.mbcraft.fileplaza.data.models.LabelSet;
import it.mbcraft.fileplaza.data.serialization.storages.DirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.StorageHelper;
import it.mbcraft.fileplaza.utils.DigestUtils;
import java.io.File;

/**
 * This class defines where and how LabelSet instances are finded and saveToOrUpdated.
 * 
 */
public class LabelSetManager extends AbstractModelManager<LabelSet> {

    public LabelSetManager(String prefix) {
        super(new DirObjectStorage(new File(prefix+StorageHelper.DEFAULT_LABEL_SETS_PATH)));
    }

    @Override
    public ISerializer getSerializer() {
        XMLSerializer xs = new XMLSerializer();
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
