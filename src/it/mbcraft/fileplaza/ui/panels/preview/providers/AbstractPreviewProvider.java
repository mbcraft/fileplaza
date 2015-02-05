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

package it.mbcraft.fileplaza.ui.panels.preview.providers;

import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public abstract class AbstractPreviewProvider implements INodeProvider {
    
    protected final ObjectProperty<PreviewData> previewDataProperty;
    
    AbstractPreviewProvider(ObjectProperty<PreviewData> previewProperty) {
        previewDataProperty = previewProperty;
    }
    
    /**
     * Returns true if this provider can preview the file.
     * 
     * @return true if this file can be previewed from this provider, false otherwise.
     */
    public abstract boolean canPreview();
    
    /**
     * Updates the preview inside this provider
     * 
     */
    public abstract void updatePreview();
    
    /**
     * Called after showing preview, before hiding or setting another one
     * 
     */
    public abstract void clear();
}
