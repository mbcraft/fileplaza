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

import java.io.File;

/**
 * Options to use for collecting the SortScore for the files to be
 * sorted.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortOptions {
    
    private SortType sortType;
    private File sourceRootFolder;
    private File targetRootFolder;

    /**
     * Creates a new SortOption instance.
     */
    public SortOptions() {
    }

    /**
     * Returns the type of sort to be done on files.
     * 
     * @return the sortType as a Type enum value
     */
    public SortType getSortType() {
        return sortType;
    }

    /**
     * Sets the type of sort to be done
     * 
     * @param sortType the sortType to do as a Type enum value
     */
    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    /**
     * Returns the source folder root in which look for files to sort.
     * 
     * @return the sourceFolder as a File instance
     */
    public File getSourceRootFolder() {
        return sourceRootFolder;
    }

    /**
     * Sets the source folder root for the files to be sorted.
     * 
     * @param sourceFolder the sourceFolder to set as a File instance
     */
    public void setSourceRootFolder(File sourceFolder) {
        this.sourceRootFolder = sourceFolder;
    }

    /**
     * Returns the target folder root in which the files can be placed
     * 
     * @return the targetFolder
     */
    public File getTargetRootFolder() {
        return targetRootFolder;
    }

    /**
     * Sets the target folder root in which the files can be placed in this sort operation
     * 
     * @param targetFolder the targetFolder to set as a File instance
     */
    public void setTargetRootFolder(File targetFolder) {
        this.targetRootFolder = targetFolder;
    }
    
    public enum SortType {
        NEWLY_TAGGED, ALL_TAGGED
    }
        
}
