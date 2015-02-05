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
