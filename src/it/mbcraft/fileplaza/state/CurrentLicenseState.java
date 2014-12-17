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
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CurrentLicenseState {
    
    private static CurrentLicenseState instance = null;
    
    private AbstractLicense currentLicense;
    
    public static CurrentLicenseState getInstance() {
        if (instance==null)
            instance = new CurrentLicenseState();
        return instance;
    }
    
    public CurrentLicenseState() {
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
    
    public AbstractLicense getLicense() {
        return currentLicense;
    }  
   
}
