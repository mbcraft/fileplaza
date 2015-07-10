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

package it.mbcraft.fileplaza.data.serialization.managers.meta;

import it.mbcraft.fileplaza.data.serialization.storages.local_dir.LocalDirObjectStorage;
import it.mbcraft.fileplaza.data.serialization.engines.IStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.engines.ImageStreamSerializer;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.storages.local_dir.StorageConstants;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

/**
 * Model manager class for BufferedImage used for previews.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewManager extends AbstractModelManager<BufferedImage> {
    
    private final Random rnd = new Random();
    
    public PreviewManager(String prefix) {
        super(new LocalDirObjectStorage(new File(prefix+StorageConstants.DEFAULT_PREVIEW_PATH)));
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public String getModelKey(BufferedImage obj) {
        long nextId = rnd.nextLong();
        while (nextId<=0 || hasKey(nextId+"."+ImageStreamSerializer.DEFAULT_IMAGE_EXTENSION)) {
            nextId = rnd.nextLong();
        }
        
        return nextId+"."+ImageStreamSerializer.DEFAULT_IMAGE_EXTENSION;
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
    public IStreamSerializer getSerializer() {
        return new ImageStreamSerializer();
    }
    
}
