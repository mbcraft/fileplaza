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

package it.mbcraft.fileplaza.service.drivewatch.os;

/**
 *
 * Factory class for IDriveListUpdater implementations.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DriveListUpdaterFactory {
    
    private static final IDriveListUpdater WINDOWS_DRIVE_LIST_UPDATER = new WindowsDriveListUpdater();
    private static final IDriveListUpdater LINUX_DRIVE_LIST_UPDATER = new LinuxDriveListUpdater();
    private static final IDriveListUpdater MACOS_DRIVE_LIST_UPDATER = new MacOSDriveListUpdater();
    
    /**
     * Gets the drive list updater for the current OS.
     * 
     * @return An object implementing IDriveListUpdater
     */
    public static IDriveListUpdater getDriveListUpdater() {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.equals("win"))
            return WINDOWS_DRIVE_LIST_UPDATER;
        if (os.equals("mac"))
            return MACOS_DRIVE_LIST_UPDATER;
        if (os.equals("linux"))
            return LINUX_DRIVE_LIST_UPDATER;
        
        throw new IllegalStateException("OS not supported : "+os);
    }
}