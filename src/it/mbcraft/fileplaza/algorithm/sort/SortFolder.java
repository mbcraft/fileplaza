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

package it.mbcraft.fileplaza.algorithm.sort;

import it.mbcraft.fileplaza.data.models.FileElement;
import it.mbcraft.fileplaza.data.models.FolderElement;
import it.mbcraft.fileplaza.data.models.Tag;
import java.io.File;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * This helper class is mapped 1-1 with the FolderElement instances in order
 * to detect which FolderElement is the best match for the FileElement to sort.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortFolder {
    
    private SortFolder myParent = null;
    private final FolderElement myFolderElement;
    private final List<SortFolder> myChildren = new ArrayList<>();
    
    /**
     * Creates a new SortFolder that maps the FolderElement parameter.
     * 
     * @param el The FolderElement to map
     */
    public SortFolder(FolderElement el) {
        myFolderElement = el;
    }
    
    /**
     * Adds a child to the current folder, and optionally moves the
     * existing children inside the new folder if needed. Only
     * SortFolder which are mapped to child or sub-child FolderElement can be added in
     * this way.
     * If a sub-child is added all the parent SortFolders are added to the tree
     * in order to have a consistent 1-1 mapping with the folders in the filesystem.
     * 
     * @param f The folder to add as a SortFolder instance
     */
    public void addChild(SortFolder f) {
        //the SortFolder must have a valid FolderElement
        if (f.myFolderElement==null)
            throw new InvalidParameterException("The SortFolder must have an associated FolderElement.");
        //the SortFolder must be a direct or indirect child of this one
        if (!isParentOf(f))
            throw new InvalidParameterException("Only direct or indirect childs can be added.");
        
        //goes ap and creates a branch to attach to this SortFolder
        File thisPath = new File(myFolderElement.getCurrentPath());
        SortFolder toAdd = f;
        File currentPath = new File(f.myFolderElement.getCurrentPath());
        while (!currentPath.equals(thisPath)) {
            SortFolder tmp = toAdd;
            toAdd = new SortFolder(null);
            toAdd.addDirectChild(tmp);
            currentPath = currentPath.getParentFile();
        }
        
        //attaches all the branch
        addDirectChild(toAdd);
    }
    
    private void addDirectChild(SortFolder f) {
        myChildren.add(f);
        f.setParent(this);
    }
    
    /**
     * Checks if this SortFolder has children.
     * 
     * @return true if this SortFolder has children, false otherwise
     */
    public boolean hasChildren() {
        return !myChildren.isEmpty();
    }
    
    /**
     * Gets the parent SortFolder. Can be null.
     * 
     * @return the parent SortFolder
     */
    public SortFolder getParent() {
        return myParent;
    }
    
    /**
     * Sets the parent SortFolder for this instance
     * 
     * @param parent the SortFolder instance to set as a parent of this one
     */
    public void setParent(SortFolder parent) {
        myParent = parent;
    }
    
    /**
     * Returns the matching score for this FileElement on the 
     * FolderElement mapped inside this SortFolder.
     * 
     * @param el The FileElement to check.
     * @return An integer matching score, one point for each matched tag.
     */
    int match(FileElement el) {
        if (el==null) throw new InvalidParameterException("The FileElement must be not null.");
        int score = 0;
        if (myFolderElement!=null) {
            List<Tag> fileTags = el.getTags();
            List<Tag> folderTags = myFolderElement.getTags();
            for (Tag fileTag : fileTags) {
                
                for (Tag folderTag : folderTags) {
                    if (folderTag.getValue().equals(fileTag.getValue()) || 
                            folderTag.isPluralOf(fileTag)) 
                        score++;
                }
            }
        }
        return score;
    }

    /**
     * Returns true if the SortFolder f is parent (direct or indirect) 
     * of the current SortFolder, looking at the path of the referenced
     * folders inside the respective FolderElement s.
     * 
     * @param f The SortFolder to compare.
     * @return true if the folder is parent of the current folder.
     */
    boolean isParentOf(SortFolder f) {
        Path current = new File(myFolderElement.getCurrentPath()).toPath();
        Path other = new File(f.myFolderElement.getCurrentPath()).toPath();
        return !current.equals(other) && other.startsWith(current);
    }

    /**
     * Gets the list of children of this SortFolder
     * 
     * @return the list of children as a List of SortFolder s.
     */
    List<SortFolder> getChildren() {
        return myChildren;
    }

    /**
     * Gets the path of the FolderElement mapped in this SortFolder. This
     * is a shortcut method.
     * 
     * @return The path of the mapped folder, as a File instance
     */
    File getPath() {
        if (myFolderElement==null)
            throw new IllegalStateException("This folder has no FolderElement.");
        return new File(myFolderElement.getCurrentPath());
    }
} 
