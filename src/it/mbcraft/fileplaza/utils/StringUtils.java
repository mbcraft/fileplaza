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

package it.mbcraft.fileplaza.utils;

/**
 * This class contains methods for encoding and decoding strings.
 * Used for license checks.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class StringUtils {
    
    public static final char OBF_CHARS[] = {'.','*',':','_','^','<','>','%','!','&',';','?','[',']','}','{'};
            
    /**
     * Obfuscates a Base64 String.
     * 
     * @param base64 The string to obfuscate, in Base64 format.
     * @return The obfuscated String.
     */
    public static final String obfuscateBase64String(String base64) {
        int j=0;
        String result = "0";    //current version
        for (int i=0;i<base64.length();i++) {
            result+=base64.charAt(i);
            if (i%3==0) {
                result+=OBF_CHARS[j];
                j++;
                j%=16;
            }
        }
        return result;
    }
    
    /**
     * De-obfuscates a previously obfuscated string.
     * 
     * @param obfuscatedBase64 The previously obfuscated string.
     * @return The de-obfuscated string.
     */
    public static final String deobfuscateBase64String(String obfuscatedBase64) {
        String toDecode = obfuscatedBase64.substring(1);
        for (int j=0;j<16;j++)
            toDecode = toDecode.replace(OBF_CHARS[j], ' ');
        toDecode = toDecode.replaceAll(" ", "");
        return toDecode;
    }
}
