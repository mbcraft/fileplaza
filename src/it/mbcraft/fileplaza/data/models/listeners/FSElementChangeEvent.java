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

package it.mbcraft.fileplaza.data.models.listeners;

import it.mbcraft.fileplaza.data.models.AbstractFileSystemElement;

/**
 * This class contains details about the change that
 * occurred to a file system element.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FSElementChangeEvent {
    
    private final AbstractFileSystemElement _fsElement;

    /**
     * Construct a FSElementChangeEvent. Needs a reference to the
     * file system element that changed
     * 
     * @param _currentFse the instance of the AbstractFileSystemElement that changed
     */
    public FSElementChangeEvent(AbstractFileSystemElement _currentFse) {
        _fsElement = _currentFse;
    }
    
    /**
     * Gets the AbstractFileSystemElement that changed.
     * 
     * @return the instance of the element that changed
     */
    public AbstractFileSystemElement getAbstractFileSystemElement() {
        return _fsElement;
    }

}
