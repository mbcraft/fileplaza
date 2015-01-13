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
