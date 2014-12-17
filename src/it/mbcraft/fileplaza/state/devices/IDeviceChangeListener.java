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

package it.mbcraft.fileplaza.state.devices;

import java.util.EventListener;



/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IDeviceChangeListener extends EventListener {
    
    /**
     * Gets called when a new device is added
     * 
     * @param ev The event containing the added file device
     */
    public void deviceAdded(DeviceChangeEvent ev);
    
    /**
     * Gets called when a device is removed
     * 
     * @param ev The event containing the removed file device
     */
    public void deviceRemoved(DeviceChangeEvent ev);
    
}
