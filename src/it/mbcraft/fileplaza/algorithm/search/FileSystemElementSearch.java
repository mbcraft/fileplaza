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

package it.mbcraft.fileplaza.algorithm.search;

import it.mbcraft.fileplaza.data.dao.fs.FileElementDAO;
import it.mbcraft.fileplaza.data.dao.fs.FolderElementDAO;
import it.mbcraft.fileplaza.data.models.AbstractFileSystemElement;
import it.mbcraft.fileplaza.data.models.FileElement;
import it.mbcraft.fileplaza.data.models.FolderElement;
import it.mbcraft.fileplaza.data.models.Priority;
import it.mbcraft.fileplaza.data.models.Tag;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Executes the search.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileSystemElementSearch {
    
    private final List<File> results = new ArrayList();
    
    private final List<Tag> searchTags = new ArrayList<>();
    private Priority fromPriority = null;
    private File withinFolder = null;
    private Type searchType;
    
    public enum Type {
        FILES, FOLDERS, BOTH
    }
    
    public void setType(Type t) {
        searchType = t;
    }
    
    public void addTag(Tag t) {
        searchTags.add(t);
    }
    
    public void removeTag(Tag t) {
        searchTags.remove(t);
    }
    
    public void setFromPriority(Priority p) {
        fromPriority = p;
    }
    
    public void setWithinFolder(File f) {
        withinFolder = f;
    }
    

    
    private void collectResults(List<? extends AbstractFileSystemElement> elements) {
        for (AbstractFileSystemElement el : elements) {
            if (withinFolder!=null && !el.getCurrentPath().startsWith(withinFolder.getAbsolutePath())) {
                continue;
            }
            
            if (fromPriority!=null && fromPriority!=Priority.NONE &&
                    fromPriority.compareTo(el.getPriority())>0) {
                    continue;
            }
            
            boolean skip = false;
            for (Tag t : searchTags) {
                if (!el.getTags().contains(t))
                    skip = true;
            }
            
            if (!skip)
                results.add(new File(el.getCurrentPath()));
        }
    }
    
        
    public List<File> execute() {
        if (searchType==Type.BOTH || searchType==Type.FOLDERS) {
            List<FolderElement> folders = FolderElementDAO.getInstance().findAll();
            collectResults(folders);
        }
        
        if (searchType==Type.BOTH || searchType==Type.FILES) {
            List<FileElement> files = FileElementDAO.getInstance().findAll();
            collectResults(files);
        }
        
        return results;
    }
    
}
