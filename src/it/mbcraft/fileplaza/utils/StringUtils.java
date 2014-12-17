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
public class StringUtils {
    
    public static final char OBF_CHARS[] = {'.','*',':','_','^','<','>','%','!','&',';','?','[',']','}','{'};
            
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
    
    public static final String deobfuscateBase64String(String obfuscatedBase64) {
        String toDecode = obfuscatedBase64.substring(1);
        for (int j=0;j<16;j++)
            toDecode = toDecode.replace(OBF_CHARS[j], ' ');
        toDecode = toDecode.replaceAll(" ", "");
        return toDecode;
    }
}
