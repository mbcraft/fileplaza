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

package it.mbcraft.fileplaza.data.serialization.managers.meta;

import it.mbcraft.fileplaza.data.serialization.storages.DirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.ISerializer;
import it.mbcraft.fileplaza.data.serialization.engines.ImageSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.StorageHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewManager extends AbstractModelManager<BufferedImage> {
    
    private final Random rnd = new Random();
    
    public PreviewManager() {
        super(new DirObjectStorage(new File(StorageHelper.DEFAULT_PREVIEW_PATH)));
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public String getModelKey(BufferedImage obj) {
        long nextId = rnd.nextLong();
        while (nextId<=0 || hasKey(nextId+"."+ImageSerializer.DEFAULT_IMAGE_EXTENSION)) {
            nextId = rnd.nextLong();
        }
        
        return nextId+"."+ImageSerializer.DEFAULT_IMAGE_EXTENSION;
    }

    @Override
    protected String getRootName() {
        throw new IllegalStateException("Can't ask about root node of Image.");
    }

    @Override
    public Class getDataClass() {
        return BufferedImage.class;
    }

    @Override
    public ISerializer getSerializer() {
        return new ImageSerializer();
    }
    
}
