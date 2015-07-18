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

import it.mbcraft.fileplaza.data.models.tags.Tag;
import it.mbcraft.fileplaza.utils.DigestUtils;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * This abstract class contains data associated to a file or a folder 
 * in the FileSystem. This class is abstract, it just contains common
 * methods and fields used in both types.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public abstract class AbstractFileSystemElement  {
    
    private static final String PRIORITY_KEY = "priority";
    private static final String NOTES_KEY = "notes";
    
    private String currentPath; //che current path of the element
    private String sha256;      //contains a calculated sha256

    private Date indexedDate;   //move to meta dictionary
    private Date lastChangedDate;   //move to meta dictionary
    private boolean indexNamedByPath;   //move to meta dictionary
    private final Type type;    //move to meta dictionary
    private List<Tag> tags;     //move to generic tags list
    
    private final Properties metaDataProperties;
    private final Properties tagsProperties;
    private List<ChangeHistory> changeHistoryList;
    
    public enum Type {
        FILE, FOLDER
    }
    
    /**
     * Creates a new AbstractFileSystemElement of the given type.
     * 
     * @param t The Type of this file system element
     */
    protected AbstractFileSystemElement(Type t) {
        setPriority(Priority.NONE);
        indexNamedByPath = true;
        tags = new LinkedList<>();
        changeHistoryList = new LinkedList<>();
        
        metaDataProperties = new Properties();
        tagsProperties = new Properties();
        
        type = t;
    }
    
    /**
     * Returns true if the file is changed, false otherwise
     * 
     * @return True if the last modified date has been changed, false otherwise
     */
    public boolean isChanged() {
        File f = new File(currentPath);
        return !new Date(f.lastModified()).equals(lastChangedDate);
    }
    
    /**
     * Calculates the sha256 digest for this file system element.
     * 
     * Solution taken from www.stackoverflow.com
     * Link : http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
     * Author : http://stackoverflow.com/users/1284661/maybewecouldstealavan
     * 
     * @return the calculated digest
     */
    public String calculatePathSha256() {
        return DigestUtils.getSha256DigestForString(currentPath);
    }
    
    /**
     * Recalculates the sha256 for this element. This method is called 
     * usually when the file content is changed, the file is moved or the 
     * directory content is changed.
     */
    public abstract void recalculateSha256();
    
    /**
     * Gets the current path for this file system element.
     * 
     * @return the currentPath as a string
     */
    public String getCurrentPath() {
        return currentPath;
    }

    /**
     * Sets the current path for this file system element
     * 
     * @param currentPath the currentPath as a string
     */
    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    /**
     * Returns the date this file system element was indexed
     * 
     * @return the date this element was indexed, as a Date instance
     */
    public Date getIndexedDate() {
        return indexedDate;
    }

    /**
     * Sets the date this element was indexed
     * 
     * @param indexedDate the date this element was indexed, as a Date instance
     */
    public void setIndexedDate(Date indexedDate) {
        this.indexedDate = indexedDate;
    }

    /**
     * Returns the last date this element changed.
     * 
     * @return the last changed date as a Date instance
     */
    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    /**
     * Sets the last changed date of this file system element.
     * 
     * @param lastChangedDate the last changed date for this element as a Date instance
     */
    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }
    
    /**
     * Gets the priority of this FileSystemElement
     * 
     * @return The priority of this FileSystemElement as a Priority value
     */
    public Priority getPriority() {
        return Priority.valueOf((String)metaDataProperties.get(PRIORITY_KEY));
    }
    
    /**
     * Sets the priority of this FileSystemElement
     * 
     * @param pri The priority as a Priority value
     */
    public void setPriority(Priority pri) {
        metaDataProperties.setProperty(PRIORITY_KEY, pri.toString());
    }
    /**
     * Gets the notes for this FileSystemElement.
     * 
     * @return The notes as a string
     */
    public String getNotes() {
        return metaDataProperties.getProperty(NOTES_KEY);
    }
    
    /**
     * Sets the notes for this FileSystemElement
     * 
     * @param nt The notes as a string
     */
    public void setNotes(String nt) {
        metaDataProperties.put(NOTES_KEY, nt);
    }

    /**
     * Returns the generic tags associated with this file system element.
     * 
     * @return the tags as a List
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * Sets the tags for this file system element.
     * 
     * @param tags the tags for this file system element as a List
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Gets the list of ChangeHistory for this file system element.
     * 
     * @return the List of ChangeHistory
     */
    public List<ChangeHistory> getChangeHistoryList() {
        return changeHistoryList;
    }
    
    /**
     * Sets the list of ChangeHistory for this file system element.
     * 
     * @param changes The new change list as a List of ChangeHistory
     */
    public void setChangeHistoryList(List<ChangeHistory> changes) {
        this.changeHistoryList = changes;
    }
    
    /**
     * Adds a change to the change history of this element.
     * 
     * @param change The change to add.
     */
    public void addChangeHistory(ChangeHistory change) {
        changeHistoryList.add(change);
    }
    
    /**
     * Gets the computed sha256 for this file system element
     * 
     * @return the sha256 as a string
     */
    public String getSha256() {
        return sha256;
    }

    /**
     * Sets the sha256 for this file system element
     * 
     * @param sha256 the sha256 to set as a string
     */
    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    /**
     * Adds a tag to this FileSystemElement
     *  
     * @param t The tag to add as a Tag instance
     */
    public void addTag(Tag t) {
        tags.add(t);
    }

    /**
     * Removes a tag from this FileSystemElement
     * 
     * @param t The tag to remove as a Tag instance
     */
    public void removeTag(Tag t) {
        tags.remove(t);
    }

    /**
     * Checks that the sha256 for this element is not null.
     * If it is, throw an IllegalStateException.
     */
    public void checkSha256NotNull() {
        if (sha256 == null) {
            throw new IllegalStateException("Sha256 of this FileSystemElement is null!");
        }
    }

    /**
     * Returns true if it's named by path, false otherwise.
     * 
     * @return True if it's named by path, false otherwise.
     */
    public boolean isIndexNamedByPath() {
        return indexNamedByPath;
    }

    /**
     * Returns true if this index is named by path, false otherwise.
     * 
     * @param indexNamedByPath True if it's named by path, false otherwise.
     */
    public void setIndexNamedByPath(boolean indexNamedByPath) {
        this.indexNamedByPath = indexNamedByPath;
    }
    
    protected boolean partialEquals(Object o) {
        if (o==null) return false;
        if (!(o instanceof AbstractFileSystemElement)) return false;
        AbstractFileSystemElement other = (AbstractFileSystemElement)o;
        boolean fieldsMatches = currentPath.equals(other.currentPath) &&
                indexedDate.equals(other.indexedDate) &&
                lastChangedDate.equals(other.lastChangedDate) &&
                getPriority().equals(other.getPriority()) &&
                getNotes().equals(other.getNotes()) &&
                indexNamedByPath==other.indexNamedByPath &&
                changeHistoryList.size()==other.changeHistoryList.size() &&
                tags.size()==other.tags.size();
        if (!fieldsMatches) return false;
        
        Iterator<ChangeHistory> otherChangeHistoryIterator = other.changeHistoryList.iterator();
        for (ChangeHistory ch : changeHistoryList) {
            ChangeHistory otherChangeHistory = otherChangeHistoryIterator.next();
            if (!ch.equals(otherChangeHistory)) return false;
        }
        
        Iterator<Tag> otherTagsIterator = other.tags.iterator();
        for (Tag t : tags) {
            Tag otherTag = otherTagsIterator.next();
            if (!t.equals(otherTag)) return false;
        }
        
        return true;
                
    }

    protected int partialHashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.currentPath);
        hash = 37 * hash + Objects.hashCode(this.getPriority());
        hash = 37 * hash + Objects.hashCode(this.getNotes());
        hash = 37 * hash + Objects.hashCode(this.indexedDate);
        hash = 37 * hash + Objects.hashCode(this.lastChangedDate);
        hash = 37 * hash + (this.indexNamedByPath ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.tags);
        hash = 37 * hash + Objects.hashCode(this.changeHistoryList);
        return hash;
    }
    
    protected void checkFileNotNullAndExists(File f) {
        if (f==null)
            throw new InvalidParameterException("The file or folder is null!");
        if (!f.exists())
            throw new InvalidParameterException("The file or folder does not exists!");
    }
    
    /**
     * Gets the type of this file system element, as a Type value.
     * 
     * @return the type of this file system element
     */
    public Type getType() {
        return type;
    }
    
}