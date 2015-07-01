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
import java.io.File;
import java.util.List;

/**
 * This is the storage independent interface for label set DAOs.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface ILabelSetDAO {

    List<LabelSet> findAll();

    /**
     * Searches for a value in all enabled label sets.
     *
     * @param value The value to check for
     *
     * @return The found type name, or null if not found
     */
    String findSetNameForValue(String value);

    LabelSet importFromFile(File f);

    void replaceAll(List<LabelSet> items);
    
}
