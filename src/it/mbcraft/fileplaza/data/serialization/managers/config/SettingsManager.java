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

package it.mbcraft.fileplaza.data.serialization.managers.config;

import it.mbcraft.fileplaza.data.serialization.storages.local_dir.StorageConstants;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.models.config.Settings;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.LocalDirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.IStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.engines.XMLStreamSerializer;
import java.io.File;

/**
 * Settings model manager implementation.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SettingsManager extends AbstractModelManager<Settings> {

    public SettingsManager(String prefix) {
        super(new LocalDirObjectStorage(new File(prefix+StorageConstants.DEFAULT_CONFIG_PATH)));
    }
    
    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public String getModelKey(Settings obj) {
        return "settings.xml";
    }

    @Override
    protected String getRootName() {
        return "settings";
    }

    @Override
    public Class getDataClass() {
        return Settings.class;
    }

    @Override
    public IStreamSerializer getSerializer() {
        XMLStreamSerializer xs = new XMLStreamSerializer();
        xs.alias("settings", Settings.class);
        return xs;
    }
    
}
