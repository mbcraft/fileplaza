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

package it.mbcraft.fileplaza.state.devices;


import java.io.File;
import java.security.InvalidParameterException;

/**
 * This class rapresents a DeviceChangeEvent. It contains the root File
 * of the changed device.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DeviceChangeEvent {
    
    private final File deviceRootFile;
    
    public DeviceChangeEvent(File deviceRootFile) {
        if (!deviceRootFile.isDirectory()) 
            throw new InvalidParameterException("Only folders can be device roots.");
        this.deviceRootFile = deviceRootFile;
    }
    
    public File getDeviceRootFile() {
        return deviceRootFile;
    }
}
