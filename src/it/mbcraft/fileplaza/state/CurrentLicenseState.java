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

package it.mbcraft.fileplaza.state;

import it.mbcraft.fileplaza.Main;
import it.mbcraft.fileplaza.state.license.AbstractLicense;
import it.mbcraft.fileplaza.state.license.LicenseException;
import it.mbcraft.fileplaza.state.license.LicenseFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the CurrentLicenseState of this class.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CurrentLicenseState {
    
    private static CurrentLicenseState instance = null;
    
    private AbstractLicense currentLicense;
    
    /**
     * Returns the current instance of this class.
     * 
     * @return The current instance of this class.
     */
    public static CurrentLicenseState getInstance() {
        if (instance==null)
            instance = new CurrentLicenseState();
        return instance;
    }
    
    /**
     * Creates an instance of this class.
     */
    private CurrentLicenseState() {
        try {
            InputStream is = Main.class.getResourceAsStream("license.properties");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String licenseCode = br.readLine();
            currentLicense = LicenseFactory.getCurrentLicense(licenseCode);
        }
        catch (LicenseException ex) {
            
        } catch (IOException ex) {
            Logger.getLogger(CurrentLicenseState.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the current license in use for FilePlaza.
     * 
     * @return An AbstractLicense in use containing the current license
     */
    public AbstractLicense getLicense() {
        return currentLicense;
    }  
   
}
