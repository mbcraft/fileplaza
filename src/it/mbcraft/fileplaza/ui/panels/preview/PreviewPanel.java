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
import it.mbcraft.fileplaza.ui.common.helpers.StackableNodeProviderSelector;
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
    
    private final StackableNodeProviderSelector selector = new StackableNodeProviderSelector();
    
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
