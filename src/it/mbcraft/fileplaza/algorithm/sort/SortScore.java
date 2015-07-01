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

import it.mbcraft.fileplaza.data.models.FileElement;
import java.io.File;
import java.security.InvalidParameterException;

/**
 * Contains the sort score for a FileElement and its target folder.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortScore implements Comparable<SortScore> {
    
    private final FileElement myElement;
    private final int myScore;
    private final File myTargetFolder;
    
    /**
     * Creates a SortScore
     * 
     * @param el The FileElement this SortScore refers to
     * @param score The score collected for this sort
     * @param targetFolder The target folder this sort will move the file referenced by the FileElement to.
     */
    public SortScore(FileElement el, int score,File targetFolder) {
        if (el==null)
            throw new InvalidParameterException("FileElement must not be null.");
        if (score<=0)
            throw new InvalidParameterException("Score must be greater than zero.");
        if (targetFolder==null || !targetFolder.isDirectory())
            throw new InvalidParameterException("The target File must be a folder.");
        
        myElement = el;
        myScore = score;
        myTargetFolder = targetFolder;
    }
    
    /**
     * Returns the FileElement this SortScore refers to.
     * 
     * @return The FileElement for this SortScore
     */
    public FileElement getElement() {
        return myElement;
    }
    
    /**
     * Returns the score for this FileElement.
     * 
     * @return the integer score for this suggested sort
     */
    public int getScore() {
        return myScore;
    }
    
    /**
     * The new folder suggested for the file referenced by the FileElement
     * 
     * @return the folder suggested as a File instance
     */
    public File getTargetFolder() {
        return myTargetFolder;
    }

    @Override
    public int compareTo(SortScore o) {
        if (myScore==o.getScore()) return 0;
        if (myScore<o.myScore) 
            return -1;
        else
            return 1;
        
    }
}
