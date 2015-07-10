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

package it.mbcraft.fileplaza.data.serialization.engines.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Implementation of IFileSerializer reading and writing images in jpeg format.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ImageFileSerializer implements IFileSerializer {

    public static final String DEFAULT_IMAGE_EXTENSION = "jpg";
    
    @Override
    public BufferedImage deserialize(File f) {    
        if (f==null) throw new InvalidParameterException("The file to deserialize is null!");
        try {
            if (f.canRead())
                return ImageIO.read(f);
            else 
            {
                //TODO report anomaly
                return null;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageFileSerializer.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvalidParameterException("Image file not found.");
        } catch (IOException ex) {
            Logger.getLogger(ImageFileSerializer.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to read image.");
        }
    }

    @Override
    public void serialize(Object o, File f) {
        try {
            if (o==null || !(o instanceof Image))
                throw new InvalidParameterException("Object to serialize is not a valid Image");
            if (f==null)
                throw new InvalidParameterException("The file is null or not writable!");
            
            BufferedImage img = (BufferedImage) o;
            ImageIO.write(img, DEFAULT_IMAGE_EXTENSION, f);
        } catch (IOException ex) {
            Logger.getLogger(ImageFileSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
