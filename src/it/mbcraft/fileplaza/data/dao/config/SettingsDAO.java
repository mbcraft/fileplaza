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

package it.mbcraft.fileplaza.data.dao.config;

import it.mbcraft.fileplaza.data.models.config.Settings;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.config.SettingsManager;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SettingsDAO implements ISettingsDAO {
    private static SettingsDAO instance = null;
    
    private final AbstractModelManager sz;
    
    private SettingsDAO() {
        sz = new SettingsManager("");
    }
    
    public static SettingsDAO getInstance() {
        if (instance==null)
            instance = new SettingsDAO();
        return instance;
    }
    
    @Override
    public Settings load() {
        String key = sz.getModelKey(null);
        if (sz.hasKey(key)) {
            return (Settings) sz.find(key);
        } else
            return new Settings();
    }
    
    @Override
    public void save(Settings settings) {
        sz.saveOrUpdate(settings);
    }
}
