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

import java.io.File;

/**
 * This interface is implemented by classes that read and writes
 * object to a file. 
 * One object = one file
 * 
 * Actually serialization is done only from/to files, but maybe in the future
 * this will be changed to a generic InputStream/OutputStream interface allowing
 * different storage types.
 * 
 * TO FIX : refactor this interface to allow read and write data
 * from the web, in order to have a centralized place for data and rely on
 * the same mechanism for models.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T> the class of the model to be serialized
 */
public interface ISerializer<T> {
    
    /**
     * Deserialize the object from the given file.
     * 
     * @param f the file to use as a source of data
     * @return the deserialized object as an instance
     */
    public T deserialize(File f);
    
    /**
     * Serialize an obhect to a given file.
     * 
     * @param o an instance of the object to serialize
     * @param dest the file to use as a destination for the serialized data
     */
    public void serialize(T o, File dest);
    
}
