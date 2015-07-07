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

package it.mbcraft.fileplaza.data.models.config;

/**
 * This class contains settings for the whole application.
 * It is used as a model to read them from/to application storage.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Settings {
    
    private String initialFolder = null;
    private String registrationEmail = null;
    private String licenseCode = null;
    private String currentLanguage = null;
    
    /**
     * Gets the initial folder in which the file browsers starts.
     * 
     * @return The initial folder as a string
     */
    public String getInitialFolder() {
        return initialFolder;
    }
    
    /**
     * Sets the initial folder in which the file browsers starts.
     * @param initialFolder 
     */
    public void setInitialFolder(String initialFolder) {
        this.initialFolder = initialFolder;
    } 

    /**
     * Gets the registration email for this application.
     * 
     * @return the registration email as a string
     */
    public String getRegistrationEmail() {
        return registrationEmail;
    }

    /**
     * Sets the registration email for this application.
     * 
     * @param registrationEmail the registration email as a string
     */
    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }
    
    /**
     * Gets the registered license code for this application.
     * 
     * @return the license code as a string
     */
    public String getLicenseCode() {
        return licenseCode;
    }
    
    /**
     * Sets the license code for this application
     * 
     * @param licenseCode the license code as a string
     */
    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }
    
    /**
     * Gets the current language set for this application
     * 
     * @return the current language as a string
     */
    public String getCurrentLanguage() {
        return currentLanguage;
    }
    
    /**
     * Sets the current language for this application as a string
     * 
     * @param currentLanguage the current language as a string
     */
    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

}