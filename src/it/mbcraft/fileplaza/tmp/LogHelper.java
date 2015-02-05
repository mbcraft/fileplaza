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

package it.mbcraft.fileplaza.tmp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LogHelper {
    
    private static final String COMMON_PREFIX = "it.mbcraft.fileshape.";
    
    private static Logger lastLogger;
    
    private static Logger getLoggerFromString(String name) {
        return Logger.getLogger(name);
    }
    
    private static Logger getLoggerFromClass(Class cl) {
        String fullClassName = cl.getCanonicalName();
        return Logger.getLogger(fullClassName.substring(COMMON_PREFIX.length()));
    }
    
    private static Logger getLoggerFromObject(Object ob) {
        return null;
    }
    
    
    public static boolean canLog(Object ob, Level level) {
        if (ob instanceof Class) {
            lastLogger = getLoggerFromClass((Class)ob);
            return lastLogger.isLoggable(level);
        }
        if (ob instanceof String) {
            lastLogger = getLoggerFromString((String)ob);
            return lastLogger.isLoggable(level);
        }
        
        lastLogger = getLoggerFromObject(ob);
        return lastLogger.isLoggable(level);
    }
    
    public static void log(String message) {
        
    }
}
