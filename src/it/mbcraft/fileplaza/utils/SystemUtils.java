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

package it.mbcraft.fileplaza.utils;

/**
 * This class contains methods for fetching information about the current
 * os.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SystemUtils {
    
    public static final byte OS_WINDOWS = 0x01;
    public static final byte OS_MAC = 0x02;
    public static final byte OS_LINUX = 0x04;
    public static final byte OS_LINUX_X64 = 0x08;
    
    /*
    
    Return the current OS as a byte, as specified from the other constants inside this class.
    
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
