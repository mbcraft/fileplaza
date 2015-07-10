/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.mbcraft.fileplaza.data.serialization.engines.stream;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Implementation of IStreamSerializer reading and writing images in jpeg format.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ImageStreamSerializer implements IStreamSerializer {
    public static final String DEFAULT_IMAGE_EXTENSION = "jpg";
    
    @Override
    public BufferedImage deserialize(InputStream is) {    
        try {
            if (is==null) throw new InvalidParameterException("The stream to deserialize is null!");
             
            return ImageIO.read(is);
        } catch (IOException ex) {
            Logger.getLogger(ImageStreamSerializer.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void serialize(Object o, OutputStream st) {
        try {
            if (o==null || !(o instanceof Image))
                throw new InvalidParameterException("Object to serialize is not a valid Image");
            if (st==null)
                throw new InvalidParameterException("The stream is null!");
            
            BufferedImage img = (BufferedImage) o;
            ImageIO.write(img, DEFAULT_IMAGE_EXTENSION, st);
        } catch (IOException ex) {
            Logger.getLogger(ImageStreamSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
