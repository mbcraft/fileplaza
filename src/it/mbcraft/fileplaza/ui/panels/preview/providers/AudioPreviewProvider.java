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
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class AudioPreviewProvider extends AbstractPreviewProvider {

    private final BorderPane content;
    
    public AudioPreviewProvider(ObjectProperty<PreviewData> previewProperty) {
        super(previewProperty);
        
        content = new BorderPane();
    }
    
    @Override
    public boolean canPreview() {
        PreviewData p = previewDataProperty.getValue();
        if (p.getFile()==null)
            return false;
        
        return false;
    }

    @Override
    public void updatePreview() {
    
    }
    
    @Override
    public void clear() {
    }

    @Override
    public Node getNode() {
        return content;
    }
    
}
