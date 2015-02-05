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
