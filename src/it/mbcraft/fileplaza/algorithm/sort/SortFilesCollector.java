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

package it.mbcraft.fileplaza.algorithm.sort;

import it.mbcraft.fileplaza.data.dao.fs.IFileElementDAO;
import it.mbcraft.fileplaza.data.models.FileElement;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class collects the FileElement that will be sorted.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortFilesCollector {
    
    private final IFileElementDAO dao;
    private final SortOptions options;
    private List<FileElement> collectedFiles;
    
    /**
     * Creates a SortFilesCollector. Needs IFileElementDAO reference
     * for reading FileElement s and SortOption instance for sorting option.
     * 
     * @param dataSource the DAO for reading FileElement instances.
     * @param sortOptions the sort options as a SortOption instance
     */
    public SortFilesCollector(IFileElementDAO dataSource, SortOptions sortOptions) {
        dao = dataSource;
        options = sortOptions;
    }
    
    /**
     * Returns the FileElement list of the files that are going to be
     * sorted.
     * 
     * @return The list of FileElement.
     */
    public List<FileElement> getFileElementList() {
        return collectedFiles;
    
    }

    /**
     * Calculates the file list to be sorted that can be returned
     * calling getFileElementList().
     */
    public void collect() {
        List<FileElement> allFiles = dao.findAll();
        
        //optionally filter for where to pick files (source folder)
        List<FileElement> allTaggedFiles;
        if (options.getSourceRootFolder()==null)
            allTaggedFiles = allFiles;
        else {
            Path sourcePath = options.getSourceRootFolder().toPath();
            allTaggedFiles = new ArrayList();
            for (FileElement el : allFiles) {
                Path p = new File(el.getCurrentPath()).toPath();
                if (p.startsWith(sourcePath))
                    allTaggedFiles.add(el);
            }
        }
        //end filtering, in allTaggedFiles i got 
        //only correcty folder filtered elements
        
        //filter by 'sort type'
        if (options.getSortType() == SortOptions.SortType.NEWLY_TAGGED) {
            List<FileElement> newlyTagged = new ArrayList();
            for (FileElement el : allTaggedFiles) {
                if (el.getChangeHistoryList().isEmpty())
                    newlyTagged.add(el);
            }
            collectedFiles = newlyTagged;
        } else
            collectedFiles = allTaggedFiles;
        //collected files now contains the files available for sort
        System.out.println("Collected files : "+collectedFiles.size());
        for (FileElement f : collectedFiles)
            System.out.println(f.getCurrentPath());
    }
}
