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
