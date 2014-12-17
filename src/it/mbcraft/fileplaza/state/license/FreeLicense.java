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

package it.mbcraft.fileplaza.state.license;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("ext.license.Licenses")
public class FreeLicense extends AbstractLicense {

    public FreeLicense(String licenseCode) {
        super(licenseCode);
    }

    @Override
    public boolean isFileAndDirectoryTaggingEnabled() {
        return false;
    }
    
    @Override
    public boolean isOnlineCatalogConnectionEnabled() {
        return false;
    }

    @Override
    public boolean isUnlimitedOSInstallationEnabled() {
        return false;
    }

    @Override
    public String getLicenseName() {
        return L(this,"FreeLicense_Label");
    }


    
}
