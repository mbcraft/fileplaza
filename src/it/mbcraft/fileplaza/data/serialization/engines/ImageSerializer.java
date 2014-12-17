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

package it.mbcraft.fileplaza.data.serialization.engines;

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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ImageSerializer implements ISerializer {

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
            Logger.getLogger(ImageSerializer.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvalidParameterException("Image file not found.");
        } catch (IOException ex) {
            Logger.getLogger(ImageSerializer.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ImageSerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
