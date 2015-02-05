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
