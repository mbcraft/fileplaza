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

package it.mbcraft.fileplaza.ui.panels.preview.providers.screenshot;

import it.mbcraft.fileplaza.data.models.CustomPreviewFactory;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.misc.PreviewData;
import it.mbcraft.fileplaza.data.serialization.managers.meta.PreviewManager;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.awt.image.BufferedImage;
import javafx.beans.property.ObjectProperty;

/**
 * This class contains methods useful for capturing a screenshot
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
    
    /**
     * Gets the current screenshot helper.
     * 
     * @return The current instance of ScreenshotHelper
     */
    public static ScreenshotHelper getInstance() {
        if (instance==null)
            instance = new ScreenshotHelper();
        return instance;
    }
    
    /**
     * 
     * @param previewDataProperty
     * @return 
     */
    public String pick(ObjectProperty<PreviewData> previewDataProperty) {
        WindowStack.top().hide();
        BufferedImage img = screenshotDetector.fetchCustomPreview();
        screenshotCutter.updateInputScreenshot(img);
        screenshotCutter.waitForSelection();
        BufferedImage finalPreview = screenshotCutter.getOutputScreenshot();
        String key = null;
        if (finalPreview!=null) {
            //save and attach to current file
            AbstractModelManager ser = new PreviewManager("");
            key = ser.saveOrUpdate(finalPreview);
        }
        WindowStack.top().show();

        return key;
    }
}
