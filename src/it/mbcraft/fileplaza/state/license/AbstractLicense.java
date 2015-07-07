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

/**
 * Abstract class for all license modes.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public abstract class AbstractLicense {
    
    private final String licenseCode;
    
    private String registrationEmail;
    private byte allowedOsMask;
    private byte licenseType;
    
    /**
     * Creates a license with the provided license code.
     * 
     * @param code The license code
     */
    public AbstractLicense(String code) {
        licenseCode = code;
    }
    
    /**
     * Returns the registration email for this license.
     * 
     * @return The registration email
     */
    public String getRegistrationEmail() {
        return registrationEmail;
    }
    
    /**
     * Returns the license code for this license.
     * 
     * @return The license code
     */
    public String getLicenseCode() {
        return licenseCode;
    }

    /**
     * Return the name inside this license.
     * 
     * @return The license name found inside this license
     */
    public abstract String getLicenseName();
    
    /**
     * Returns trie if file and directory tagging is enabled for 
     * this license.
     * 
     * @return true if file and folder tagging is enabled, false otherwise.
     */
    public abstract boolean isFileAndDirectoryTaggingEnabled();
    
    /**
     * Returns true if the online catalog connection is enabled for
     * this license.
     * 
     * @return true if the online catalog connection is enabled, false otherwise
     */
    public abstract boolean isOnlineCatalogConnectionEnabled();

    /**
     * Returns true if the unlimited os installation is enabled for
     * this license.
     * 
     * @return true if the unlimited os installation is enabled, false otherwise
     */
    public abstract boolean isUnlimitedOSInstallationEnabled();
}
