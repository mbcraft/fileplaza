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
