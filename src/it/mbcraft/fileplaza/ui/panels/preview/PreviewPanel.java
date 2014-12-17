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

package it.mbcraft.fileplaza.ui.panels.preview;

import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.ui.panels.preview.providers.AbstractPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.AudioPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.CapturedPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.DisabledPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.FolderPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.NoPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.NoSelectionPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.SimpleTextPreviewProvider;
import it.mbcraft.fileplaza.ui.panels.preview.providers.VideoPreviewProvider;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.StackablePanelSelector;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

/**
 * This class handles the preview of the current file content.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewPanel implements INodeProvider {
    
    private final ObjectProperty<PreviewData> previewDataProperty;
    
    private final StackPane filePreviewPanel_;
    
    private final StackablePanelSelector selector = new StackablePanelSelector();
    
    private final List<AbstractPreviewProvider> previewers = new ArrayList();
    
    public PreviewPanel(ObjectProperty<PreviewData> currentPreviewData) {
        previewDataProperty = currentPreviewData;
        previewDataProperty.addListener(new ChangeListener<PreviewData>() {

            @Override
            public void changed(ObservableValue<? extends PreviewData> ov, PreviewData oldValue, PreviewData newValue) {
                updateCurrentPreview();
            }
        });
                
        filePreviewPanel_ = new StackPane();
        
        addPreviewer(new NoSelectionPreviewProvider(previewDataProperty));
        addPreviewer(new CapturedPreviewProvider(previewDataProperty));
        addPreviewer(new DisabledPreviewProvider(previewDataProperty));
        addPreviewer(new FolderPreviewProvider(previewDataProperty));
        addPreviewer(new VideoPreviewProvider(previewDataProperty));
        addPreviewer(new AudioPreviewProvider(previewDataProperty));
        addPreviewer(new SimpleTextPreviewProvider(previewDataProperty));
        addPreviewer(new NoPreviewProvider(previewDataProperty));
        
        updateCurrentPreview();
    }
    
    /**
     * Adds a previewer to the previewer list
     * 
     * @param provider The preview provider to add
     */
    private void addPreviewer(AbstractPreviewProvider provider) {
        previewers.add(provider);
        selector.add(provider);
        filePreviewPanel_.getChildren().add(provider.getNode());
    }
        
    /**
     * Updates the current preview with the one found for the File f.
     * 
     * @param f The file to preview
     */
    private void updateCurrentPreview() {
        int i = 0;
        for (AbstractPreviewProvider provider : previewers) {
            if (provider.canPreview()) {
                previewers.get(selector.getLastSelectionIndex()).clear();
                provider.updatePreview();
                selector.select(i);
                return;
            }
            i++;
        }
    }
    
    @Override
    public Node getNode() {
        return filePreviewPanel_;
    }

}
