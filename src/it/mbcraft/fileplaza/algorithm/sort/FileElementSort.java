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
import it.mbcraft.fileplaza.data.dao.fs.IFolderElementDAO;
import it.mbcraft.fileplaza.data.models.FileElement;
import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation completed.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileElementSort {
    
    private Map<File,SortScore> previewed = null;
    private final IFileElementDAO fileDAO;
    private final IFolderElementDAO folderDAO;
    private final SortOptions sortOptions;
    
        
    public FileElementSort(IFolderElementDAO folderDao,IFileElementDAO fileDao, SortOptions options) {
        folderDAO = folderDao;
        fileDAO = fileDao;
        sortOptions = options;
    }
    
    /**
     * Calculates and returns the previewed list of files to move
     * 
     */
    public void preview() {
        if (previewed!=null)
            throw new IllegalStateException("The preview list has already been processed!");
        
        previewed = new HashMap();
        
        //initializes the target folders
        SortFoldersCollector foldersCollector = new SortFoldersCollector(folderDAO,sortOptions);
        foldersCollector.collect();
        
        //initializes the source files (should be swapped?)
        SortFilesCollector filesCollector = new SortFilesCollector(fileDAO,sortOptions);
        filesCollector.collect();
        
        //initialize the sorter
        Sorter sorter = new Sorter();
        sorter.setSortOptions(sortOptions);
        sorter.setTargetRoots(foldersCollector.getRoots());
        sorter.setTargetLeaves(foldersCollector.getLeaves());
        
        //actually collects scores and fills the preview list
        for (FileElement fe : filesCollector.getFileList()) {
            SortScore score = sorter.getScoreForFileElement(fe);
            if (score!=null) {
                previewed.put(new File(fe.getCurrentPath()), score);
            }
        }
    }
    
    /**
     * Returns the score object for this sort.
     * 
     * @param f The file to be moved
     * 
     * @return the score
     */
    public SortScore getSortScore(File f) {
        checkFileInPreviewList(f);
        
        return previewed.get(f);
    }
    
    /**
     * Removes a file from the list of the "to-be-moved" files.
     * 
     * @param f The files to move.
     */
    public void remove(File f) {
        checkFileInPreviewList(f);
        
        previewed.remove(f);
    }
    
    /**
     * Sort a previewed file.
     * 
     * @param f The file to sort
     * @return true if the file has been succesfully sorted, false otherwise
     */
    public boolean sort(File f) {
        checkFileInPreviewList(f);
        

        SortScore score = previewed.get(f);
        File source = f;
        File dest = score.getTargetFolder();
        File finalPath = new File(dest,source.getName());
        boolean result = fileDAO.move(score.getElement(),finalPath);
        if (result)
            previewed.remove(source);    
        return result;
    }
    
    /**
     * Actually performs the previewed moves and removes the entries from
     * the previewed list. Operations are performed on entries that was not
     * previously removed. It throws an unchecked exception if error occurs.
     */
    public void sortAll() {
        Set<File> toSort = previewed.keySet();
        for (File f : toSort)
            sort(f);
    }
    
    /**
     * Returns the actual files inside the preview list, or the remaining
     * as with errors after a sort.
     * 
     * @return The file list
     */
    public List<File> getFileList() {
        List<File> result = new ArrayList();
        result.addAll(previewed.keySet());
        return result;
    }
    
    private void checkFileInPreviewList(File f) {
        if (previewed==null)
            throw new IllegalStateException("preview() must be called prior calling this method.");
        if (f==null)
            throw new InvalidParameterException("The file parameter can't be null.");
        if (!previewed.containsKey(f))
            throw new InvalidParameterException("The file must be inside the preview list.");
    }

    public boolean hasResults() {
        return !previewed.isEmpty();
    }
}
