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
