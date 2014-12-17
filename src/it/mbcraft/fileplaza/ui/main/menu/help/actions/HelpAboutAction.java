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

package it.mbcraft.fileplaza.ui.main.menu.help.actions;

import it.mbcraft.fileplaza.i18n.LangResource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import it.mbcraft.fileplaza.ui.main.menu.help.about.AboutWindow;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.actions.HelpAboutAction")
public class HelpAboutAction implements EventHandler<ActionEvent> {
    
    private final AboutWindow window = new AboutWindow();
    
    @Override
    public void handle(ActionEvent t) { 
        window.showAndWait();
    }
    
}
