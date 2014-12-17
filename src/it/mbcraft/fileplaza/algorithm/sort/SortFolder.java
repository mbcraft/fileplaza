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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortFolder {
    
    private SortFolder myParent = null;
    private final FolderElement myFolderElement;
    private final List<SortFolder> myChildren = new ArrayList<>();
    
    public SortFolder(FolderElement el) {
        myFolderElement = el;
    }
    
    /**
     * Adds a child to the current folder, and optionally moves the
     * existing childs inside the new folder if needed.
     * 
     * @param f The folder to add.
     */
    public void addChild(SortFolder f) {
        if (f.myFolderElement==null)
            throw new InvalidParameterException("The SortFolder must have an associated FolderElement.");

        if (!isParentOf(f))
            throw new InvalidParameterException("Only direct or indirect childs can be added.");
        
        File thisPath = new File(myFolderElement.getCurrentPath());
        SortFolder toAdd = f;
        File currentPath = new File(f.myFolderElement.getCurrentPath());
        while (!currentPath.equals(thisPath)) {
            SortFolder tmp = toAdd;
            toAdd = new SortFolder(null);
            toAdd.addDirectChild(tmp);
            currentPath = currentPath.getParentFile();
        }
        
        addDirectChild(toAdd);
    }
    
    private void addDirectChild(SortFolder f) {
        myChildren.add(f);
        f.setParent(this);
    }
    
    public boolean hasChildren() {
        return !myChildren.isEmpty();
    }
    
    public SortFolder getParent() {
        return myParent;
    }
    
    public void setParent(SortFolder parent) {
        myParent = parent;
    }
    
    /**
     * Returns the matching score for this folder.
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
     * Returns true if the SortFolder f is parent of the current folder.
     * 
     * @param f The SortFolder to compare.
     * @return true if the folder is parent of the current folder.
     */
    boolean isParentOf(SortFolder f) {
        Path current = new File(myFolderElement.getCurrentPath()).toPath();
        Path other = new File(f.myFolderElement.getCurrentPath()).toPath();
        return !current.equals(other) && other.startsWith(current);
    }

    List<SortFolder> getChildren() {
        return myChildren;
    }

    File getPath() {
        if (myFolderElement==null)
            throw new IllegalStateException("This folder has no FolderElement.");
        return new File(myFolderElement.getCurrentPath());
    }
} 
