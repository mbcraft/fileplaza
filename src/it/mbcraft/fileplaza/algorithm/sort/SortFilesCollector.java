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

import it.mbcraft.fileplaza.data.dao.fs.IFileElementDAO;
import it.mbcraft.fileplaza.data.models.FileElement;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class collects the file that will be sorted.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortFilesCollector {
    
    private final IFileElementDAO dao;
    private final SortOptions options;
    private List<FileElement> collectedFiles;
    
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
    public List<FileElement> getFileList() {
        return collectedFiles;
    
    }

    /**
     * Calculates the file list to be sorted.
     */
    public void collect() {
        List<FileElement> allFiles = dao.findAll();
        
        //optionally filter for where to pick files (source folder)
        List<FileElement> allTaggedFiles;
        if (options.getSourceFolder()==null)
            allTaggedFiles = allFiles;
        else {
            Path sourcePath = options.getSourceFolder().toPath();
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
        if (options.getSortType() == SortOptions.Type.NEWLY_TAGGED) {
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
