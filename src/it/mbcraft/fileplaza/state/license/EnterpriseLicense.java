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

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("ext.license.Licenses")
public class EnterpriseLicense extends AbstractLicense {

    public EnterpriseLicense(String licenseCode) {
        super(licenseCode);
    }

    @Override
    public boolean isFileAndDirectoryTaggingEnabled() {
        return true;
    }
    
    @Override
    public boolean isOnlineCatalogConnectionEnabled() {
        return true;
    }

    @Override
    public boolean isUnlimitedOSInstallationEnabled() {
        return true;
    }

    @Override
    public String getLicenseName() {
        return L(this,"EnterpriseLicense_Label");
    }


    
}
