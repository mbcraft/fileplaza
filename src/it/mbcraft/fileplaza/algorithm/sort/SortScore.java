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
