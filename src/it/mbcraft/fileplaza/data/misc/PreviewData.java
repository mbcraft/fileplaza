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
 * This class does not contain data used for preview.
 * It just contains a reference to the file that needs to be previewed. The preview key is used to
 * identify this preview data and is saved inside the AbstractFileSystemElement.
 * PreviewData is saved in a way that is possible to get the preview data using the
 * preview key.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewData {
    
    private final File myFile;
    private final String myPreviewKey;
    
    /**
     * Builds a new instance of PreviewData.
     * 
     * @param f The file that needs to be previewed.
     * @param previewKey The unique key that identify this preview data, as a string
     */
    public PreviewData(File f,String previewKey) {
        myFile = f;
        myPreviewKey = previewKey;
    }
    
    /**
     * Returns the file containing the preview data
     * 
     * @return In instance of File containing the preview data.
     */
    public File getFile() {
        return myFile;
    }
    
    /**
     * Returns the preview key associated to this preview data.
     * 
     * @return The string key used as a preview
     */
    public String getPreviewKey() {
        return myPreviewKey;
    }
        
    /**
     * Returns true if this preview data has a valid key
     * 
     * @return true if it has a valid preview key, false otherwise
     */
    public boolean hasValidPreviewKey() {
        return myPreviewKey!=null && !myPreviewKey.equals("");
    }
}
