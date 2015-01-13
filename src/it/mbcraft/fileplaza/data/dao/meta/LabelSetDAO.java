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

package it.mbcraft.fileplaza.data.dao.meta;

import it.mbcraft.fileplaza.data.models.LabelSet;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.meta.LabelSetManager;
import java.io.File;
import java.util.List;

/**
 * This class contains the main operations done with user defined enum
 * storage.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LabelSetDAO implements ILabelSetDAO {
    
    private static LabelSetDAO instance_;
    
    private final AbstractModelManager sz;
    private final List<LabelSet> enums;
    
    public static LabelSetDAO getInstance() {
        if (instance_ == null) 
            instance_ = new LabelSetDAO();
        return instance_;
    }
    
    private LabelSetDAO() {
        sz = new LabelSetManager("");
        enums = sz.findAll();
    }
    
    /**
     * Searches for a value in all enabled label sets.
     * 
     * @param value The value to check for
     *
     * @return The found type name, or null if not found
     */
    @Override
    public String findSetNameForValue(String value) {
        for (LabelSet en : enums) {
            if (en.isEnabled()) {
                for (String enumValue : en.getLabels().keySet()) {
                    if (value.equals(enumValue))
                        return en.getSetName();
                }
            }
        }
        return null;
    }
    
    @Override
    public LabelSet importFromFile(File f) {
        LabelSet en = (LabelSet) sz.loadFrom(f);
        en.setEnabled(false);
        return en;
    }

    @Override
    public void replaceAll(List<LabelSet> items) {
        sz.deleteAll();
        for (LabelSet ls : items) {
            sz.saveOrUpdate(ls);
        }
    }

    @Override
    public List<LabelSet> findAll() {
        return sz.findAll();
    }
}
