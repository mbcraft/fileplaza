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

package it.mbcraft.fileplaza.state.license;

import it.mbcraft.fileplaza.utils.StringUtils;
import it.mbcraft.fileplaza.utils.SystemUtils;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LicenseFactory {    
    
    private static final byte OS_CODE_LENGTH = 8;
    private static final byte[] OS_WINDOWS = {0x5b,0x0a,0x18,0x7b,0x4e,0x3e,0x78,0x05};
    private static final byte[] OS_MAC = {0x2f,0x40,0x55,0x0a,0x07,0x36,0x2e,0x64};
    private static final byte[] OS_LINUX = {0x47,0x02,0x3c,0x46,0x49,0x02,0x1c,0x63};
    private static final byte[] OS_LINUX_X64 = {0x12,0x62,0x7d,0x2b,0x0c,0x52,0x09,0x21};
    
    /**
     * Now license is simply a code meaning "Free", "Standard", "Professional", "Enterprise".
     * 
     * @param licenseCode The license code
     * @return The license
     * @throws LicenseException If there was some error in creating the license
     */
    public static AbstractLicense getCurrentLicense(String licenseCode) throws LicenseException {
        if (licenseCode==null || licenseCode.equals(""))
            throw new LicenseException("No license found.");
        String base64 = StringUtils.deobfuscateBase64String(licenseCode);
        byte[] data = Base64.decodeBase64(base64);
        String licensePrefix = new String(data);
        
        if (licensePrefix.equals("Free"))
            return new FreeLicense(licensePrefix);
        if (licensePrefix.equals("Standard"))
            return new StandardLicense(licensePrefix);
        if (licensePrefix.equals("Professional"))
            return new ProfessionalLicense(licensePrefix);
        if (licensePrefix.equals("Enterprise"))
            return new EnterpriseLicense(licensePrefix);
        throw new LicenseException("No valid license prefix found : "+licensePrefix);
    }
    
    /**
     * Returns an os marker array for the current os.
     * @return a byte array
     */
    private static byte[] getOSIdentifier() {
        switch (SystemUtils.getCurrentOS()) {
            case SystemUtils.OS_WINDOWS : return LicenseFactory.OS_WINDOWS;
            case SystemUtils.OS_MAC : return LicenseFactory.OS_MAC;
            case SystemUtils.OS_LINUX : return LicenseFactory.OS_LINUX;
            case SystemUtils.OS_LINUX_X64 : return LicenseFactory.OS_LINUX_X64;
            default : throw new IllegalStateException("Unable to get a valid OS marker byte array.");
        }
    }
    
    /**
     * Returns the machine code with os information as string
     * @param email The registration email
     * @return The machine code as String
     */
    public static String getMachineCode(String email) {

        String base64email = Base64.encodeBase64String(email.getBytes());
        try {
            //read eth0 mac address
            byte[] address = NetworkInterface.getByName("eth0").getHardwareAddress();
            //final data array
            byte[] full_code = new byte[address.length+OS_CODE_LENGTH];
            int i=0;
            //copy mac
            for (;i<address.length;i++)
                full_code[i] = address[i];
            byte[] os_array = getOSIdentifier();
            //copy os marker
            for (int j=0;i<full_code.length;i++,j++)
                full_code[i] = os_array[j];
            //return base64 encoded data
            String partialCode = Base64.encodeBase64String(full_code);
            String realCodeData = partialCode+base64email;
            return StringUtils.obfuscateBase64String(realCodeData);
            
        } catch (SocketException ex) {
            Logger.getLogger(LicenseFactory.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("Impossible machine code generation.");
        }
    }
}
