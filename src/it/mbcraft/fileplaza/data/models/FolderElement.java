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

package it.mbcraft.fileplaza.data.models;

import it.mbcraft.fileplaza.data.dao.fs.FolderElementDAO;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 * This class models a folder index with its tags
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FolderElement extends AbstractFileSystemElement {
    
    public FolderElement() {
        super(AbstractFileSystemElement.Type.FOLDER);
    }
    
    private String calculateDirContentSha256() {
        return FolderElementDAO.getFolderKeyFromContent(getCurrentPath());
    }
    
    @Override
    public void recalculateSha256() {
        if (isIndexNamedByPath())
            setSha256(calculatePathSha256());
        else
            setSha256(calculateDirContentSha256());
    }
    
    @Override
    public boolean equals(Object o) {
        if (o==null) return false;
        if (!(o instanceof FolderElement)) return false;
        return partialEquals(o);
    }

    @Override
    public int hashCode() {
        return partialHashCode();
    }

    public void setupWithFolder(File f) {
        checkFileNotNullAndExists(f);
        if (!f.isDirectory())
            throw new InvalidParameterException("The file parameter is not a directory!!");
    
        setCurrentPath(f.getAbsolutePath());
        setIndexedDate(new Date());
        setLastChangedDate(new Date(f.lastModified()));
        setPriority(Priority.NONE);
        setIndexNamedByPath(true);
    }


    
}
