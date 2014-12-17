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

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ChangeHistory {
    
    
    public ChangeHistory(Date changeDate, ChangeType changeType, Object oldValue) {
        this.changeDate = changeDate;
        this.changeType = changeType;
        this.oldValue = oldValue;
    }
    
    public ChangeHistory() {
        
    }
    
    private Date changeDate;
    private ChangeType changeType;
    private Object oldValue;

    /**
     * @return the changeDate
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * @param changeDate the changeDate to set
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
    
    public ChangeType getChangeType() {
        return changeType;
    }
    
    public void setChangeType(ChangeType type) {
        this.changeType = type;
    }

    /**
     * @return the oldValue
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * @param oldValue the oldValue to set
     */
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    
    public static enum ChangeType {
        CONTENT, PATH
    }
    
    @Override
    public boolean equals(Object o) {
        if (o==null) return false;
        if (!(o instanceof ChangeHistory)) return false;
        ChangeHistory other = (ChangeHistory) o;
        return changeDate.equals(other.changeDate) && changeType.equals(other.changeType) && oldValue.equals(other.oldValue);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.changeDate);
        hash = 97 * hash + Objects.hashCode(this.changeType);
        hash = 97 * hash + Objects.hashCode(this.oldValue);
        return hash;
    }

}
