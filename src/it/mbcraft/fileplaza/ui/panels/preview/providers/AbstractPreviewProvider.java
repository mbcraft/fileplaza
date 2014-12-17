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
