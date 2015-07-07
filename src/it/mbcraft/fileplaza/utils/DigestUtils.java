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
 * This class contains methods for calculating digest of files or strings.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DigestUtils {

    final private static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Converts the bytes to String hex format.
     * 
     * @param bytes The bytes to convert
     * @return The String in hex format.
     */
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Calculates the Sha256 digest for a string.
     * 
     * @param toDigest The string to digest.
     * @return The sha256 digest of the string.
     */
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

    /**
     * Calculates the sha256 digest of the file.
     * 
     * @param f The file instance to pick content from.
     * @return The sha256 digest of the file.
     */
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

    /**
     * Calculates the md5 digest of the string parameter.
     * 
     * @param toDigest The string to digest
     * @return The md5 digest of the parameter
     */
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
