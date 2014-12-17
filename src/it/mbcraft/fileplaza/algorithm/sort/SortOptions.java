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

import java.io.File;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortOptions {
    
    private Type sortType;
    private File sourceFolder;
    private File targetFolder;

    public SortOptions() {
    }

    /**
     * @return the sortType
     */
    public Type getSortType() {
        return sortType;
    }

    /**
     * @param sortType the sortType to set
     */
    public void setSortType(Type sortType) {
        this.sortType = sortType;
    }

    /**
     * @return the sourceFolder
     */
    public File getSourceFolder() {
        return sourceFolder;
    }

    /**
     * @param sourceFolder the sourceFolder to set
     */
    public void setSourceFolder(File sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    /**
     * @return the targetFolder
     */
    public File getTargetFolder() {
        return targetFolder;
    }

    /**
     * @param targetFolder the targetFolder to set
     */
    public void setTargetFolder(File targetFolder) {
        this.targetFolder = targetFolder;
    }
    
    public enum Type {
        NEWLY_TAGGED, ALL_TAGGED
    }
        
}
