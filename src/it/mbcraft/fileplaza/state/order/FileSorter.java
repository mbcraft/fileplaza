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

package it.mbcraft.fileplaza.state.order;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Sort files using a comparator and an option about file and folders handling.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileSorter {
    
    private static final Comparator ALPHABETICAL_ASCENDING_COMPARATOR = new AlphabeticalAscendingFileComparator();
    private static final Comparator ALPHABETICAL_DESCENDING_COMPARATOR = new AlphabeticalDescendingFileComparator();
    private static final Comparator DATE_ASCENDING_COMPARATOR = new DateAscendingFileComparator();
    private static final Comparator DATE_DESCENDING_COMPARATOR = new DateDescendingFileComparator();
    
    private static final ArrayList mergeList = new ArrayList();
    
    private static final ArrayList filesList = new ArrayList();
    private static final ArrayList foldersList = new ArrayList();
    
    /**
     * Sort the given files. A FileSortMode and a FileSortOption are needed.
     * 
     * @param files The files to sort as a File array.
     * @param mode The FileSortMode to use for sorting these files.
     * @param option The FileSortOption to use for sorting these files.
     * @return a sorted File array.
     */
    public static File[] sort(File[] files, FileSortMode mode, FileSortOption option) {
        Comparator cmp = getComparatorByMode(mode);
        return sortWithComparatorByOption(files, cmp, option);
    }
    
    private static File[] sortWithComparatorByOption(File[] files, Comparator cmp, FileSortOption opt) {
        
        foldersList.clear();
        filesList.clear();
        if (opt!=FileSortOption.MIXED) {
            for (File f : files) {
                if (f.isDirectory()) 
                    foldersList.add(f);
                else
                    filesList.add(f);
            }
            
            Collections.sort(foldersList,cmp);
            Collections.sort(filesList,cmp);
            
            mergeList.clear();
            if (opt==FileSortOption.FOLDERS_THEN_FILES) {
                mergeList.addAll(foldersList);
                mergeList.addAll(filesList);
            } else {
                mergeList.addAll(filesList);
                mergeList.addAll(foldersList);
            }
            return (File[])mergeList.toArray(files);
        }
        else {
           List fList = Arrays.asList(files);
           Collections.sort(fList,cmp);
           return (File[])fList.toArray(files);
        }
    }

    private static Comparator getComparatorByMode(FileSortMode mode) {
        switch (mode) {
            case ALPHABETICAL_ASCENDING : return ALPHABETICAL_ASCENDING_COMPARATOR;
            case ALPHABETICAL_DESCENDING : return ALPHABETICAL_DESCENDING_COMPARATOR;
            case DATE_ASCENDING : return DATE_ASCENDING_COMPARATOR;
            case DATE_DESCENDING : return DATE_DESCENDING_COMPARATOR;
            default : throw new IllegalStateException("Comparator not found for mode : "+mode);
        }
    }
}
