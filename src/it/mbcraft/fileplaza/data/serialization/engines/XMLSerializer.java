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

package it.mbcraft.fileplaza.data.serialization.engines;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * Implementation of ISerializer interface producing reading and writind data 
 * in xml format.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * 
 * @param <T> the class of the model to be serialized
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
