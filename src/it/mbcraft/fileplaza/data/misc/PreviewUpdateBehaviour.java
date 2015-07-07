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

package it.mbcraft.fileplaza.data.misc;

import java.security.InvalidParameterException;

/**
 * This class contain behavior used to check if the preview data
 * should be updated. If the preview key changes, then the new preview key
 * must be updated inside the AbstractFileSystemElement to reflect the
 * change happened.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewUpdateBehaviour {
    
    /**
     * Checks if the AbstractFileSystemElement must be updated as
     * preview data changes.
     * 
     * @param oldData The old preview data
     * @param newData The new preview data
     * @return true if the AbstractFileSystemElement must be updated, false otherwise
     */
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
