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

package it.mbcraft.fileplaza.ui.common.helpers;

import it.mbcraft.fileplaza.Main;
import java.net.URL;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CssFactory {
    
    private static String getCss(String name) {
        URL cssUrl = Main.class.getResource("graphics/official/css/"+name+".css");
        if (cssUrl==null)
            cssUrl = Main.class.getResource("graphics/stub/css/"+name+".css");
                
        if (cssUrl==null) throw new IllegalStateException("CSS Resource not found : "+name+".css");
        
        return cssUrl.toExternalForm();
    }
    
   public static String getIconButtonCss() {
       return getCss("icon_button");
   } 
}
