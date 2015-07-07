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

package it.mbcraft.fileplaza.service.drivewatch.os;

import java.nio.file.FileStore;
import java.util.List;

/**
 * Interfaces shared by DriveListUpdater implementations.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IDriveListUpdater {
    
    /**
     * Update the DriveList given the iterable FileStore.
     * @param stores The iterable FileStore.
     * @param driveListProperty The drive list property to update.
     */
    public void updateDriveList(Iterable<FileStore> stores,List<DriveIdentifier> driveListProperty);
}
