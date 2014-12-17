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

import it.mbcraft.fileplaza.data.models.config.RootFolders;
import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;

/**
 * This class detects device changes based on RootFolders configuration
 * and fires event about this.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DeviceDetector implements Runnable {
    
    private static DeviceDetector instance;
    
    private static final FileFilter FOLDERS_ONLY_FILTER = new FoldersOnlyFileFilter();
    
    private RootFolders myFolders = null;
    private long sleepTimeMillis = 2000;
    private boolean started = false;
    
    private final EventListenerList listeners = new EventListenerList();
    
    
    public static DeviceDetector getInstance() {
        if (instance==null)
            instance = new DeviceDetector();
        
        return instance;
    }
    
    public void setRootFolders(RootFolders rootFolders) {
        myFolders = rootFolders;
    }
    
    public RootFolders getRootFolders() {
        return myFolders;
    }
    
    public long getSleepTimeMillis() {
        return sleepTimeMillis;
    }
    
    public void setSleepTimeMillis(long timeMillis) {
        sleepTimeMillis = timeMillis;
    }
    
    public void start() {
        if (started) throw new IllegalStateException("DeviceDetector already started!");
        if (myFolders==null) throw new IllegalStateException("RootFolders are not set up!");
        started = true;
        Thread th = new Thread(this);
        th.setDaemon(true);
        th.setName("DeviceDetector");
        th.start();
    }
    
    public void stop() {
        started = false;
    }
    

    /**
     * Fires all device change events 
     * 
     * @param previousSet The previous set of folders
     * @param currentSet  The current set of folders
     */
    private void fireAllDeviceChangeEvents(Set<File> previousSet,Set<File> currentSet) {
        Set<File> toRemoveP = new HashSet(previousSet);
        Set<File> deviceAdded = new HashSet(currentSet);
        
        Set<File> deviceRemoved = new HashSet(previousSet);
        Set<File> toRemoveC = new HashSet(currentSet);
        
        deviceRemoved.removeAll(toRemoveC);
        deviceAdded.removeAll(toRemoveP);
        
        for (File f : deviceRemoved) {
            fireDeviceRemovedEvent(new DeviceChangeEvent(f));
        }
        
        for (File f : deviceAdded) {
            fireDeviceAddedEvent(new DeviceChangeEvent(f));
        }
    }
    
    private void fireDeviceAddedEvent(DeviceChangeEvent ev) {
        for (Object l : listeners.getListenerList()) {
            IDeviceChangeListener listener = (IDeviceChangeListener) l;
            listener.deviceAdded(ev);
        }
    }
    
    private void fireDeviceRemovedEvent(DeviceChangeEvent ev) {
        for (Object l : listeners.getListenerList()) {
            IDeviceChangeListener listener = (IDeviceChangeListener) l;
            listener.deviceRemoved(ev);
        }
    }
    
    /**
     * Updates the current folders set based on RootFolders configuration.
     * The current set is saved into the previous set before update.
     * 
     * @param previousFolders The previous folders set
     * @param currentFolders The current folders set
     */
    private void updateCurrentFolders(Set<File> previousFolders, Set<File> currentFolders) {
        //previous set to current
        previousFolders.clear();
        previousFolders.addAll(currentFolders);
        
        //update current folders
        currentFolders.clear();
        for (String folderPath : myFolders.getWatchedFolders()) {
            File f = new File(folderPath);
            File[] listed = f.listFiles(FOLDERS_ONLY_FILTER);
            currentFolders.addAll(Arrays.asList(listed));
        }
    }

    @Override
    public void run() {
        Set<File> previouslyDetectedFolders = new HashSet();
        Set<File> currentDetectedFolders = new HashSet();

        while (started) {
            try {
                //do actual folder listing based on current configuration
                updateCurrentFolders(previouslyDetectedFolders,currentDetectedFolders);

                //fire events
                fireAllDeviceChangeEvents(previouslyDetectedFolders, currentDetectedFolders);
                                
                //sleeping
                Thread.sleep(sleepTimeMillis);
            } catch (InterruptedException ex) {
                Logger.getLogger(DeviceDetector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public void addDeviceChangeListener(IDeviceChangeListener listener) {
        listeners.add(IDeviceChangeListener.class, listener);
    }
    
    public void removeDeviceChangeListener(IDeviceChangeListener listener) {
        listeners.remove(IDeviceChangeListener.class, listener);
    }
    
    
}
