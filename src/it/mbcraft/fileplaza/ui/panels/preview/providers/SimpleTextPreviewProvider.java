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

import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.utils.FileUtils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * This class provides a panel for showing a text preview of the file.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SimpleTextPreviewProvider extends AbstractPreviewProvider {

    private final BorderPane content;
    
    private final TextArea area;
    
    public SimpleTextPreviewProvider(ObjectProperty<PreviewData> previewProperty) {
        super(previewProperty);
        
        content = new BorderPane();
        
        area = new TextArea();
        area.setWrapText(true);
        
        content.setCenter(area);
    }
    
    @Override
    public boolean canPreview() {
        PreviewData p = previewDataProperty.getValue();
        if (p.getFile()==null) return false;
        String extension = FileUtils.getExtensionFromFile(p.getFile());
        if (extension.equals("txt") || extension.startsWith("log"))
            return true;
        else 
            return false;
    }
    
    @Override
    public void clear() {
    }

    @Override
    public void updatePreview() {
        try {
            char[] cbuf = new char[1024];
            FileReader fr = new FileReader(previewDataProperty.getValue().getFile());
            fr.read(cbuf);
            area.setText(new String(cbuf));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SimpleTextPreviewProvider.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SimpleTextPreviewProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Node getNode() {
        return content;
    }


    
}
