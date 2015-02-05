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

import javafx.scene.control.MenuBar;

/**
 * This class handles the creation of the whole menubar.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class MainMenuBar implements IMenuBarProvider {
        
    private final MenuBar menuBar_;
        
    public MainMenuBar() {
        menuBar_ = new MenuBar();
        
        addMenuFromProvider(new FileMenu()); 
       
        addMenuFromProvider(new HelpMenu());    
    }
    
    public final void addMenuFromProvider(IMenuProvider provider) {
        menuBar_.getMenus().add(provider.getMenu());
    }
        
    @Override
    public MenuBar getMenuBar() {
        return menuBar_;
    }
}
