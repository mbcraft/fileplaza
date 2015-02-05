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
