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
