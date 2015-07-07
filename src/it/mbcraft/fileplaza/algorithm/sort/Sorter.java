/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
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
