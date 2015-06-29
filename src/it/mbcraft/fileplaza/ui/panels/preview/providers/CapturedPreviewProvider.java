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

import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.data.serialization.managers.meta.PreviewManager;
import it.mbcraft.fileplaza.ui.panels.preview.providers.screenshot.ScreenshotHelper;
import it.mbcraft.fileplaza.ui.common.components.misc.ScrollableImageViewer;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import java.awt.image.BufferedImage;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

/**
 * This panel is used for showing a 'captured' preview.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CapturedPreviewProvider extends AbstractPreviewProvider {

    private final BorderPane content;
    private final ScrollableImageViewer viewer;

    public CapturedPreviewProvider(ObjectProperty<PreviewData> previewProperty) {
        super(previewProperty);

        content = new BorderPane();
        viewer = new ScrollableImageViewer();
        
        content.setCenter(viewer.getNode());

        FlowPane commandPane = new FlowPane();
        commandPane.setAlignment(Pos.CENTER);
        commandPane.setHgap(10);
        final Button updatePreviewButton = new Button(L(this, "UpdatePreview_Button"));
        updatePreviewButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                String result = ScreenshotHelper.getInstance().pick(previewDataProperty);
                if (result!=null) {
                    previewDataProperty.setValue(new PreviewData(previewDataProperty.getValue().getFile(), result));
                }
            }
        });

        final Button deletePreviewButton = new Button(L(this, "DeletePreview_Button"));
        deletePreviewButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                previewDataProperty.setValue(new PreviewData(previewDataProperty.getValue().getFile(), null));
            }
        });

        commandPane.getChildren().addAll(updatePreviewButton, deletePreviewButton);
        content.setBottom(ComponentFactory.newPaddingPane(commandPane, 10));
    }

    @Override
    public boolean canPreview() {
        return previewDataProperty.getValue() != null && previewDataProperty.getValue().hasValidPreviewKey();
    }

    @Override
    public void updatePreview() {
        AbstractModelManager sz = new PreviewManager("");

        viewer.updateImage(SwingFXUtils.toFXImage((BufferedImage) sz.find(previewDataProperty.getValue().getPreviewKey()), null));
    }

    @Override
    public void clear() {
        content.setCenter(null);
    }

    @Override
    public Node getNode() {
        return content;
    }

}
