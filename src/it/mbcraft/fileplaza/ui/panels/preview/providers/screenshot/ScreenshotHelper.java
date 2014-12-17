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

package it.mbcraft.fileplaza.ui.panels.preview.providers.screenshot;

import it.mbcraft.fileplaza.data.models.CustomPreviewFactory;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.data.serialization.managers.meta.PreviewManager;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.awt.image.BufferedImage;
import javafx.beans.property.ObjectProperty;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ScreenshotHelper {
    
    private final CustomPreviewFactory screenshotDetector;
    private final ScreenshotCutterWindow screenshotCutter;
    
    private static ScreenshotHelper instance = null;
    
    private ScreenshotHelper() {
        screenshotDetector = new CustomPreviewFactory();
        screenshotCutter = new ScreenshotCutterWindow();
    }
    
    public static ScreenshotHelper getInstance() {
        if (instance==null)
            instance = new ScreenshotHelper();
        return instance;
    }
    
    public String pick(ObjectProperty<PreviewData> previewDataProperty) {
        WindowStack.top().hide();
        BufferedImage img = screenshotDetector.fetchCustomPreview();
        screenshotCutter.updateInputScreenshot(img);
        screenshotCutter.waitForSelection();
        BufferedImage finalPreview = screenshotCutter.getOutputScreenshot();
        String key = null;
        if (finalPreview!=null) {
            //save and attach to current file
            AbstractModelManager ser = new PreviewManager();
            key = ser.saveOrUpdate(finalPreview);
        }
        WindowStack.top().show();

        return key;
    }
}
