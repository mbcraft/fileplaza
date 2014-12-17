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

package it.mbcraft.fileplaza.tmp;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class TrayIconManager {
    
    private TrayIcon myIcon;
    
    public boolean isSystemTraySupported()
    {
        return SystemTray.isSupported();
    }
    
    public void init()
    {
     
         // get the SystemTray instance
         SystemTray tray = SystemTray.getSystemTray();
         // load an image
         //TODO : Fix tray icon, old method
         Image image = null;//IconImageFactory.getAppIcon();
         // create a action listener to listen for default action executed on the tray icon
         ActionListener listener = new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 // execute default action of the application
                 // ...
             }
         };
         // create a popup menu
         PopupMenu popup = new PopupMenu();
         // create menu item for the default action
         MenuItem defaultItem = new MenuItem("Hello!");
         defaultItem.addActionListener(listener);
         popup.add(defaultItem);
         /// ... add other items
         // construct a TrayIcon
         myIcon = new TrayIcon(image, "FileFlow", popup);
         // set the TrayIcon properties
         myIcon.addActionListener(listener);
         // ...
         // add the tray image
         try {
             tray.add(myIcon);
         } catch (AWTException e) {
             System.err.println(e);
         }
         // ...

    }
    
    public void dispose()
    {
        // get the SystemTray instance
         SystemTray tray = SystemTray.getSystemTray();
         tray.remove(myIcon);
    }
}
