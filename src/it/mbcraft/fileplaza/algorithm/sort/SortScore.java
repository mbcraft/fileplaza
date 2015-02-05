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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SortScore implements Comparable<SortScore> {
    
    private final FileElement myElement;
    private final int myScore;
    private final File myTargetFolder;
    
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
    
    public FileElement getElement() {
        return myElement;
    }
    
    public int getScore() {
        return myScore;
    }
    
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
