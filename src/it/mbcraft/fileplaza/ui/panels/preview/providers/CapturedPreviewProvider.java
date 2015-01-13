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
