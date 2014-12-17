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

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DigestUtils {

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static String getSha256DigestForString(String toDigest) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] result = md.digest(toDigest.getBytes());
            return bytesToHex(result);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigestUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to calculate sha256.");
        }
    }

    public static String getSha256ForFile(File f) {
        try (FileChannel ch = FileChannel.open(f.toPath(), StandardOpenOption.READ)) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            ByteBuffer buf = ByteBuffer.allocateDirect(1024 * 1024);
            while (ch.read(buf, 0) >= 0) {
                md.update(buf);
            }
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigestUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to calculate sha256.");
        } catch (IOException ex) {
            Logger.getLogger(DigestUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to read from file : " + f.getAbsolutePath());
        }

    }

    public static String getMD5DigestForString(String toDigest) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] result = md.digest(toDigest.getBytes());
            return bytesToHex(result);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigestUtils.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Unable to calculate md5.");
        }
    }

}
