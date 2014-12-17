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
