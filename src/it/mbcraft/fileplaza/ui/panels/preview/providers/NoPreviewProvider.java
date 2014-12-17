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

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.ui.panels.preview.providers.screenshot.ScreenshotHelper;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class NoPreviewProvider extends AbstractPreviewProvider implements EventHandler<ActionEvent> {
    
    private final Node content;
    
    public NoPreviewProvider(ObjectProperty<PreviewData> currentPreview) {
        super(currentPreview);

        BorderPane pane = new BorderPane();
        
        
        final Button captureButton = new Button(L(this,"Capture_Button"));
        captureButton.setOnAction(this);
        
        pane.setCenter(captureButton);
        
        content = pane;  
    }
    
    @Override
    public boolean canPreview() {
        PreviewData p = previewDataProperty.getValue();
        if (p.getFile()==null)
            return false;
        
        return true;
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

    @Override
    public void handle(ActionEvent t) {
        String result = ScreenshotHelper.getInstance().pick(previewDataProperty);
        if (result!=null) {
            previewDataProperty.setValue(new PreviewData(previewDataProperty.getValue().getFile(),result));
        }
    }


    
}
