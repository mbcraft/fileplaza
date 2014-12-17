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

package it.mbcraft.fileplaza.data.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * This class models an enumeration list. Its key is given as an md5 hash
 * of its type name.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class LabelSet {
    
    /**
     * The shortTitle of this user defined enum
     */
    private String shortTitle;
    
    /**
     * The enabled state of this user defined enum
     */
    private boolean enabled;
    /**
     * The set name of this label set.
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
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
     
    public String getSetName() {
        return this.setName;
    }
    
    public void setSetName(String setName) {
        this.setName = setName;
    }
            
    public Map<String,String> getLabels() {
        return values;
    }

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
