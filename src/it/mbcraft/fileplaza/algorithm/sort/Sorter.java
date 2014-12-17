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
import java.util.List;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Sorter {
    
    private SortOptions options = null;
    
    private List<SortFolder> targetRoots = null;
    private List<SortFolder> targetLeaves = null;
       
    public SortOptions getSortOptions() {
        return options;
    }
    
    public void setSortOptions(SortOptions sortOptions) {
        options = sortOptions;
    }
    
    public List<SortFolder> getTargetRoots() {
        return targetRoots;
    }
    
    /**
     * Sets the target roots for this Sorter.
     * 
     * @param roots The roots of target folder trees.
     */
    public void setTargetRoots(List<SortFolder> roots) {
        targetRoots = roots;
    }
    
    public List<SortFolder> getTargetLeaves() {
        return targetLeaves;
    }
    
    /**
     * Sets the target leaves for this Sorter.
     * 
     * @param leaves The leaves of target folder trees.
     */
    public void setTargetLeaves(List<SortFolder> leaves) {
        targetLeaves = leaves;
    }
    
    /**
     * Returns the score for a FileElement using the collected tree
     * of SortFolder.
     * 
     * @param fe The FileElement to check
     * @return The best score for that FileElement
     */
    public SortScore getScoreForFileElement(FileElement fe) {
        int bestScore = 0;
        SortFolder bestFolder = null;
        for (SortFolder f : targetLeaves) {
            int currentScore = collectScore(f,fe);
            if (currentScore>bestScore) {
                bestScore = currentScore;
                bestFolder = f;
            }
        }
        File currentElementFolder = new File(fe.getCurrentPath()).getParentFile();
        SortScore score = null;
        if (bestScore>0 && !currentElementFolder.equals(bestFolder.getPath())) {
            score = new SortScore(fe, bestScore, bestFolder.getPath());
        } 
        return score;
    }
    
    private int collectScore(SortFolder leaf,FileElement el) {
        SortFolder current = leaf;
        int score = 0;
        while (current!=null) {
            score+=current.match(el);
            current = current.getParent();
        }
        return score;
    }
    
}
