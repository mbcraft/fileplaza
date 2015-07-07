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
 * This class can be used for doing searches of file system elements.
 * It actually allows the search to be done with many different options.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileSystemElementSearch {
    
    private final List<File> results = new ArrayList();
    
    private final List<Tag> searchTags = new ArrayList<>();
    private Priority fromPriority = null;
    private File withinFolder = null;
    private SearchType searchType;
    
    /**
     * The types of search, detailing the kind of elements to be searched
     */
    public enum SearchType {
        FILES, FOLDERS, BOTH
    }
    
    /**
     * Sets the type of search to be done
     * 
     * @param t the type of search as a SearchType enum value 
     */
    public void setType(SearchType t) {
        searchType = t;
    }
    
    /**
     * Adds a tag to be matched in the elements found in this search
     * 
     * @param t the tag to match as a Tag instance
     */
    public void addTag(Tag t) {
        searchTags.add(t);
    }
    
    /**
     * Removes a tag to be matched in the elements found in this search
     * 
     * @param t the tag to remove
     */
    public void removeTag(Tag t) {
        searchTags.remove(t);
    }
    
    /**
     * Sets the starting priority of the elements that needs to be 
     * matched in this search.
     * 
     * @param p the priority 
     */
    public void setFromPriority(Priority p) {
        fromPriority = p;
    }
    
    /**
     * Sets the folder which will limit the search.
     * 
     * @param f the folder in which execute the search as a File instance
     */
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
    
    /**
     * Executes the search with the previously set parameters.
     * 
     * @return a List of File instances matching the previously specified criteria
     */
    public List<File> execute() {
        if (searchType==SearchType.BOTH || searchType==SearchType.FOLDERS) {
            List<FolderElement> folders = FolderElementDAO.getInstance().findAll();
            collectResults(folders);
        }
        
        if (searchType==SearchType.BOTH || searchType==SearchType.FILES) {
            List<FileElement> files = FileElementDAO.getInstance().findAll();
            collectResults(files);
        }
        
        return results;
    }
    
}
