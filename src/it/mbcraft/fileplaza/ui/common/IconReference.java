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

package it.mbcraft.fileplaza.ui.common;

/**
 * This class represents a reference to a particular kind of icon of ANY size.
 * 
 * Size can change, depending on zoom level.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class IconReference {
    
    private final IconCategory category;
    private final String iconName;
    
    public enum IconCategory {
        FILE, FLAG, MISC
    }
    
    public IconReference(IconCategory category,String iconName) {
        this.category = category;
        this.iconName = iconName;
    }
    
    public IconCategory getIconType() {
        return category;
    }
    
    public String getIconName() {
        return iconName;
    }
}
