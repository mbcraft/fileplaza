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

package it.mbcraft.fileplaza.ui.common.helpers;

import it.mbcraft.fileplaza.Main;
import java.net.URL;

/**
 * Factory class to read css from files as a single string.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CssFactory {
    
    /**
     * Reads the css from file. It tries the official folder first, and if
     * not css are found, the stub folder is tried.
     * If no css file is found an exception is thrown.
     * 
     * @param name The css name
     * @return The css string
     */
    private static String getCss(String name) {
        URL cssUrl = Main.class.getResource("graphics/official/css/"+name+".css");
        if (cssUrl==null)
            cssUrl = Main.class.getResource("graphics/stub/css/"+name+".css");
                
        if (cssUrl==null) throw new IllegalStateException("CSS Resource not found : "+name+".css");
        
        return cssUrl.toExternalForm();
    }
    
    /**
     * Gets the css for the icon button.
     * 
     * @return The css as a string
     */
   public static String getIconButtonCss() {
       return getCss("icon_button");
   } 
}
