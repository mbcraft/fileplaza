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

import it.mbcraft.fileplaza.utils.DigestUtils;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 * This class models a file index with its tags.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileElement extends AbstractFileSystemElement {    
    
    private long size;
    private String customPreviewKey = "";
    
    
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
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }
    
    public boolean hasCustomPreview() {
        return !customPreviewKey.equals("");
    }
    
    /**
     * Gets the key of the preview, or empty if no preview is available.
     * 
     * @return The key of the preview, or empty if no custom preview is available.
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
