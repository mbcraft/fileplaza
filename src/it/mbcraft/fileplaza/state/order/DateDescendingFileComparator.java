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

package it.mbcraft.fileplaza.state.order;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class implements the FileSortMode.DATE_DESCENDING comparator for sorting
 * files.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DateDescendingFileComparator implements Comparator<File> {

    @Override
    public int compare(File a, File b) {
            try {
            FileTime a_date = Files.getLastModifiedTime(a.toPath());
            FileTime b_date = Files.getLastModifiedTime(b.toPath());
            
            return b_date.compareTo(a_date);
            
        } catch (IOException ex) {
            Logger.getLogger(DateAscendingFileComparator.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to get file time.");
        }
    }

    
}
