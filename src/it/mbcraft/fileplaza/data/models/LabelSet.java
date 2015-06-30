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

package it.mbcraft.fileplaza.data.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class models an enumeration of labels. Its key is given as an md5 hash
 * of its type name.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LabelSet {
    
    /**
     * The shortTitle of this label set
     */
    private String shortTitle;
    
    /**
     * The enabled state of this label set
     */
    private boolean enabled;
    /**
     * The name of this label set.
     */
    private String setName;
            
    /**
     * The map of values of this user label set
     */
    private Map<String,String> values = new HashMap<>();
        
    public String getShortTitle() {
        return shortTitle;
    }
    
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
    
    
    /**
     * Returns the enabled state of this label set
     * 
     * @return true if this label set is enabled, false otherwise
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled state of this label set
     * 
     * @param enabled the state to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
     
    /**
     * Returns the label set name
     * 
     * @return the name of this set as a string
     */
    public String getSetName() {
        return this.setName;
    }
    
    /**
     * Sets the label set name as a string
     * 
     * @param setName the name of the label set
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }
            
    /**
     * Return the labels in this label set
     * 
     * @return the label as a map
     */
    public Map<String,String> getLabels() {
        return values;
    }

    /**
     * Sets the labels in this label set
     * 
     * @param values The map containing all the labels of this set.
     */
    public void setLabels(Map<String,String> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (o==null) return false;
        if (!(o instanceof LabelSet)) return false;
        LabelSet other = (LabelSet) o;
        
        boolean fieldMatched = shortTitle.equals(other.shortTitle) && setName.equals(other.setName) && values.size()==other.values.size();
        if (!fieldMatched) return false;
        
        Set<String> otherKeys = other.getLabels().keySet();
        
        for (String key : values.keySet()) {
            if (!otherKeys.contains(key)) return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.setName);
        return hash;
    }


}
