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

package it.mbcraft.fileplaza.ui.panels.preview.providers;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.data.misc.PreviewData;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * This class created a panel used when the preview is disabled.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DisabledPreviewProvider extends AbstractPreviewProvider {

    private final BorderPane content;
    
    public DisabledPreviewProvider(ObjectProperty<PreviewData> previewProperty) {
        super(previewProperty);
        
        content = new BorderPane();
        
        Label l = new Label(L(this,"DisabledPreview_Label"));
        l.setDisable(true);
        
        content.setCenter(l);
    }
    
    @Override
    public boolean canPreview() {
        //to check if in disabled list
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
