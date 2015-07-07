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

package it.mbcraft.fileplaza.data.models;

import it.mbcraft.fileplaza.utils.DigestUtils;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 * This class models file with its associated tags and meta data
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileElement extends AbstractFileSystemElement {    
    
    private long size;
    private String customPreviewKey = "";
    
    /**
     * Builds a new FileElement.
     */
    public FileElement() {
        super(AbstractFileSystemElement.Type.FILE);
    }
    
    /**
     * This method calculates the sha256 of the content.
     * @return 
     */
    public String calculateContentSha256() {
        File f = new File(getCurrentPath());
        return DigestUtils.getSha256ForFile(f);
    }

    /**
     * Returns the size of this file
     * 
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * Sets the size of this file.
     * 
     * TO FIX : remove this method, or change 'size' to 'lastObservedSize'
     * 
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }
    
    /**
     * Checks if this file has a custom preview.
     * 
     * @return true if this file has a custom preview, false otherwise
     */
    public boolean hasCustomPreview() {
        return !customPreviewKey.equals("");
    }
    
    /**
     * Gets the key of the custom preview, or returns an empty string
     * if no custom preview is available.
     * 
     * @return The key of the custom preview, or empty string if no custom preview is available.
     */
    public String getCustomPreviewKey() {
        return customPreviewKey;
    }
    
    /**
     * Sets the custom preview key for this FileElement
     * 
     * @param key The key of the custom preview.
     */
    public void setCustomPreviewKey(String key) {
        this.customPreviewKey = key;
    }

    @Override
    public void recalculateSha256() {
        if (isIndexNamedByPath())
            setSha256(calculatePathSha256());
        else
            setSha256(calculateContentSha256());
    }
        
    @Override
    public boolean equals(Object o) {
        if (o==null) return false;
        if (!(o instanceof FileElement)) return false;
        FileElement other = (FileElement)o;
        return partialEquals(o) && size==other.size;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = hash * 23 + partialHashCode();
        hash = 23 * hash + (int) (this.size ^ (this.size >>> 32));
        return hash;
    }

    /**
     * Setup the data inside this FileElement from the provided paramenter.
     * 
     * @param f The File instance used to configure this FileElement.
     */
    public void setupWithFile(File f) {
        checkFileNotNullAndExists(f);
        if (!f.isFile())
            throw new InvalidParameterException("The parameter is not a valid file!");
    
        setCurrentPath(f.getAbsolutePath());
        setIndexedDate(new Date());
        setLastChangedDate(new Date(f.lastModified()));
        setPriority(Priority.NONE);
        setIndexNamedByPath(true);
        setSize(f.length());
    }


}
