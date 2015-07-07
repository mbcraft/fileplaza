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
