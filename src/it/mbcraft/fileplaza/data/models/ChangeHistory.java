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

import java.util.Date;
import java.util.Objects;

/**
 * Contains a single change done to a single filesystem element.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ChangeHistory {
    
    /**
     * Creates a new instance.
     * 
     * @param changeDate The date when the change occurred
     * @param changeType The type of change that occurred
     * @param oldValue The old value before the change occurred
     */
    public ChangeHistory(Date changeDate, ChangeType changeType, Object oldValue) {
        this.changeDate = changeDate;
        this.changeType = changeType;
        this.oldValue = oldValue;
    }
    
    /**
     * Creates an uninitialized instance.
     */
    public ChangeHistory() {        
    }
    
    private Date changeDate;
    private ChangeType changeType;
    private Object oldValue;

    /**
     * Gets the change date of this ChangeHistory.
     * 
     * @return the changeDate as a Date object
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * Sets the change date of this ChangeHistory
     * 
     * @param changeDate the changeDate as a Date object
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
    
    /**
     * Gets the change type of this ChangeHistory
     * 
     * @return the ChangeType of this ChangeHistory
     */
    public ChangeType getChangeType() {
        return changeType;
    }
    
    /**
     * Sets the ChangeType of this ChangeHistory
     * 
     * @param type the type of this change as a ChangeType instance
     */
    public void setChangeType(ChangeType type) {
        this.changeType = type;
    }

    /**
     * Gets the value before this change occurred.
     * 
     * @return the oldValue as a generic Object
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * Sets the old value before this change occurred.
     * 
     * @param oldValue the oldValue as a generic Object instance
     */
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    
    /**
     * Static enumeration containing the type of changes that are recorded. Actually
     * only two changes are recorded into history :
     * - CONTENT : the content of the element is changed
     * - PATH : the path of the element is changed
     */
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
