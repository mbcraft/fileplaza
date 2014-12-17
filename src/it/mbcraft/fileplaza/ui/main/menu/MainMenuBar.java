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
