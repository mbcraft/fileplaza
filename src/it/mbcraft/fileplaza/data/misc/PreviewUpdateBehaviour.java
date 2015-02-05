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

package it.mbcraft.fileplaza.data.misc;

import java.security.InvalidParameterException;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewUpdateBehaviour {
    public static boolean needsUpdate(PreviewData oldData,PreviewData newData) {
        if (oldData==null || newData==null)
            throw new InvalidParameterException("Old data or new data is null!");
        
        if (oldData.getFile()==null || newData.getFile()==null)
            return false;
        
        if (oldData.getFile().equals(newData.getFile()) && oldData.getPreviewKey()!=newData.getPreviewKey())
            return true;
        
        return false;
    }
}
