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

package it.mbcraft.fileplaza.data.misc;

import java.io.File;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewData {
    
    private final File myFile;
    private final String myPreviewKey;
    
    public PreviewData(File f,String previewKey) {
        myFile = f;
        myPreviewKey = previewKey;
    }
    
    public File getFile() {
        return myFile;
    }
    
    public String getPreviewKey() {
        return myPreviewKey;
    }
        
    public boolean hasValidPreviewKey() {
        return myPreviewKey!=null && !myPreviewKey.equals("");
    }
}
