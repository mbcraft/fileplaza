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

import it.mbcraft.fileplaza.utils.DigestUtils;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Questa classe rappresenta un file o una cartella nel filesystem.
 * Classe astratta, contiene solo i metodi e i campi comuni a entrambi i tipi.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public abstract class AbstractFileSystemElement  {
    
    private String currentPath;
    private String sha256;
    private Priority priority;  //move to meta dictionary
    private String notes;       //move to meta dictionary
    private Date indexedDate;   //move to meta dictionary
    private Date lastChangedDate;   //move to meta dictionary
    private boolean indexNamedByPath;   //move to meta dictionary
    private final Type type;    //move to meta dictionary
    private List<Tag> tags;     //move to generic tags list
    private List<ChangeHistory> changeHistoryList;
    
    public enum Type {
        FILE, FOLDER
    }
    
    protected AbstractFileSystemElement(Type t) {
        priority = Priority.NONE;
        indexNamedByPath = true;
        tags = new LinkedList<>();
        changeHistoryList = new LinkedList<>();
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
     * Solution taken from www.stackoverflow.com
     * Link : http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
     * Author : http://stackoverflow.com/users/1284661/maybewecouldstealavan
     * 
     * @return 
     */
    
    public String calculatePathSha256() {
        return DigestUtils.getSha256DigestForString(currentPath);
    }
    
    public abstract void recalculateSha256();
    
    /**
     * @return the currentPath
     */
    public String getCurrentPath() {
        return currentPath;
    }

    /**
     * @param currentPath the currentPath to set
     */
    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    /**
     * @return the indexedDate
     */
    public Date getIndexedDate() {
        return indexedDate;
    }

    /**
     * @param indexedDate the indexedDate to set
     */
    public void setIndexedDate(Date indexedDate) {
        this.indexedDate = indexedDate;
    }

    /**
     * @return the lastChangedDate
     */
    public Date getLastChangedDate() {
        return lastChangedDate;
    }

    /**
     * @param lastChangedDate the lastChangedDate to set
     */
    public void setLastChangedDate(Date lastChangedDate) {
        this.lastChangedDate = lastChangedDate;
    }
    
    /**
     * Gets the priority of this FileSystemElement
     * 
     * @return The priority of this FileSystemElement
     */
    public Priority getPriority() {
        return priority;
    }
    
    /**
     * Sets the priority of this FileSystemElement
     * 
     * @param pri The priority
     */
    public void setPriority(Priority pri) {
        this.priority = pri;
    }
    
    /**
     * Gets the notes for this Element.
     * 
     * @return The fixedColor
     */
    public String getNotes() {
        return notes;
    }
    
    /**
     * Sets the notes for this Element
     * 
     * @param nt The color to set
     */
    public void setNotes(String nt) {
        notes = nt;
    }

    /**
     * @return the tags
     */
    public List<Tag> getTags() {
        return tags;
    }

    /**
     * @param tags the tags to set
     */
    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    /**
     * @return the changeHistoryList
     */
    public List<ChangeHistory> getChangeHistoryList() {
        return changeHistoryList;
    }
    
    /**
     * Set a new changeHistoryList
     * 
     * @param changes The change list
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
     * @return the sha256
     */
    public String getSha256() {
        return sha256;
    }

    /**
     * @param sha256 the sha256 to set
     */
    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    /**
     * Adds a tag to this FileSystemElement
     *  
     * @param t The tag to add
     */
    public void addTag(Tag t) {
        tags.add(t);
    }

    /**
     * Removes a tag from this FileSystemElement
     * 
     * @param t The tag to remove
     */
    public void removeTag(Tag t) {
        tags.remove(t);
    }

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
                priority.equals(other.priority) &&
                notes.equals(other.notes) &&
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
        hash = 37 * hash + Objects.hashCode(this.priority);
        hash = 37 * hash + Objects.hashCode(this.notes);
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
    
    public Type getType() {
        return type;
    }
    
}