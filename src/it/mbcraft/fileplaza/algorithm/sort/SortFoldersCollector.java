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

import it.mbcraft.fileplaza.data.dao.fs.IFolderElementDAO;
import it.mbcraft.fileplaza.data.models.FolderElement;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class collects the folder that will be used as target for sorting
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortFoldersCollector {

    private final IFolderElementDAO dao;
    private final SortOptions options;
    
    private List<SortFolder> roots;
    private List<SortFolder> leaves;
    
    public SortFoldersCollector(IFolderElementDAO dataSource, SortOptions sortOptions) {
        dao = dataSource;
        options = sortOptions;
    }
    
    public List<SortFolder> getRoots() {
        return roots;
    }
    
    public List<SortFolder> getLeaves() {
        return leaves;
    }
        
    /**
     * Collects all the valid folders to be used for file ordering.
     * A list of roots and a list of leaves are initialized.
     */
    public void collect() {
        initRoots();
        initLeaves();
        
        System.out.println("Collected roots : "+roots.size());
        for (SortFolder sf : roots) 
                System.out.println(sf.getPath().getAbsolutePath());
           
        System.out.println("Collected leaves : "+leaves.size());
        for (SortFolder sf : leaves) 

                System.out.println(sf.getPath().getAbsolutePath());
            
    }
    
    private void initRoots() {
        if (roots!=null) 
            throw new IllegalStateException("Collect already called.");
        
        //init source path filter
        Path targetFolderPath = null;
        if (options.getTargetFolder()!=null) 
            options.getTargetFolder().toPath();
        
        List<FolderElement> folders = dao.findAll();
        
        roots = new ArrayList();
        
        for (FolderElement el : folders) {
            Path elementPath = new File(el.getCurrentPath()).toPath();
            if (targetFolderPath==null || elementPath.startsWith(targetFolderPath)) {
                SortFolder current = new SortFolder(el);

                if (roots.isEmpty()) {
                    roots.add(current);
                    
                }
                else 
                    updateRoots(current);
            } 
        }
    }
    
    private void initLeaves() {
        if (leaves!=null)
            throw new IllegalStateException("Collect already called.");
        
        leaves = new ArrayList();
        
        for (SortFolder sf : roots) {
            recursiveUpdateLeaves(sf,leaves);
        }
    }
    
    private void updateRoots(SortFolder f) {
        for (SortFolder sf : roots) {
            if (sf.isParentOf(f))
                sf.addChild(f);
            if (f.isParentOf(sf)) {
                roots.remove(sf);
                f.addChild(sf);
                roots.add(f);
            }       
        }
    }

    private void recursiveUpdateLeaves(SortFolder current,List<SortFolder> leaves) {
        if (current.hasChildren()) {
            for (SortFolder sf : current.getChildren())
                recursiveUpdateLeaves(sf, leaves);
        } else
            leaves.add(current);
    }
    
}
