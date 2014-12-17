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

package it.mbcraft.fileplaza.ui.main.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class AbstractMenu implements IMenuProvider {

    private final Menu myMenu;
    
    protected AbstractMenu(String title) {
        myMenu = new Menu(title);
    }
    
    public void addItem(String title,EventHandler<ActionEvent> handler) {
        MenuItem item = new MenuItem(title);
        item.setOnAction(handler);
        myMenu.getItems().add(item);
    }
    
    public void addSeparator() {
        myMenu.getItems().add(new SeparatorMenuItem());
    }
    
    @Override
    public Menu getMenu() {
        return myMenu;
    }
    
}
