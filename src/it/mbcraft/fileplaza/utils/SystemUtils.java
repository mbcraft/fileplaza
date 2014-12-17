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

package it.mbcraft.fileplaza.utils;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SystemUtils {
    
    public static final byte OS_WINDOWS = 0x01;
    public static final byte OS_MAC = 0x02;
    public static final byte OS_LINUX = 0x04;
    public static final byte OS_LINUX_X64 = 0x08;
    
    /*
    Some help from www.stackoverflow.com :
    http://stackoverflow.com/questions/228477/how-do-i-programmatically-determine-operating-system-in-java
    http://stackoverflow.com/users/13/chris-jester-young
    http://stackoverflow.com/users/348189/tacb0ss
    */
    public static byte getCurrentOS() {
        
        String os = System.getProperty("os.name");
        String lowercase_os = os.toLowerCase();
        String arch = System.getProperty("os.arch");
        String lowercase_arch = arch.toLowerCase();

        if (lowercase_os.contains("windows"))
            return OS_WINDOWS;
        if (lowercase_os.contains("mac") || lowercase_os.contains("darwin"))
            return OS_MAC;
        if (lowercase_os.contains("nux") && lowercase_arch.contains("64"))
            return OS_LINUX_X64;
        if (lowercase_os.contains("nux"))
            return OS_LINUX;
        
        throw new IllegalStateException("Unable to correctly detect OS.");
        
    }
}
