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

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class XMLSerializer<T> extends XStream implements ISerializer<T> {

    /**
     * Some help from https://www.stackoverflow.com
     * http://stackoverflow.com/questions/10835835/xstream-crashes-when-there-are-more-fields-in-the-xml
     * http://stackoverflow.com/users/2829988/ajit-kumar
     * 
     */
    
    public XMLSerializer() {
        omitField(getClass(), "__sourceFile");
        ignoreUnknownElements();
    }
    
    @Override
    public T deserialize(File f) {
        if (f==null) throw new InvalidParameterException("The file to deserialize is null!");
        T result = (T) this.fromXML(f);
        return result;
    }

    @Override
    public void serialize(T o, File f) {
        if (o==null)
            throw new InvalidParameterException("Object to serialize is not a valid AbstractModel or is null!");
        if (f==null)
            throw new InvalidParameterException("The file is null or not writable!");
        
        try {
            FileWriter fw = new FileWriter(f);
            this.toXML(o, fw);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to serialize object to valid file.");
        }
    }
}
