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

package it.mbcraft.fileplaza.data.models.config;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Settings {
    
    private String initialFolder = null;
    private String registrationEmail = null;
    private String licenseCode = null;
    private String currentLanguage = null;
    
    public String getInitialFolder() {
        return initialFolder;
    }
    
    public void setInitialFolder(String initialFolder) {
        this.initialFolder = initialFolder;
    } 

    public String getRegistrationEmail() {
        return registrationEmail;
    }

    public void setRegistrationEmail(String registrationEmail) {
        this.registrationEmail = registrationEmail;
    }
    
    public String getLicenseCode() {
        return licenseCode;
    }
    
    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }
    
    public String getCurrentLanguage() {
        return currentLanguage;
    }
    
    public void setCurrentLanguage(String currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

}