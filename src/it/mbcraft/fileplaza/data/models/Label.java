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

package it.mbcraft.fileplaza.data.models;

/**
 * This class models a label for a file (maybe in the future can be on other
 * entities)
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Label {
    private String name;
    private String colorAsString;
    
    /**
     * Sets the name of this label
     * 
     * @param newName The name of this label as a string
     */
    public void setName(String newName) {
        this.name = newName;
    }
    
    /**
     * Gets the name of this label as a string
     * 
     * @return The name of this label
     */
    public String getName() {
        return name;
    }
    
    /**
     * Sets the color of this label as a string
     * 
     * @param colorString The string color for this label
     */
    public void setColorAsString(String colorString) {
        this.colorAsString = colorString;
    }
    
    /**
     * Gets the color of this label as a string
     * 
     * @return the string rapresenting the color of this label
     */
    public String getColorAsString() {
        return colorAsString;
    }
}
