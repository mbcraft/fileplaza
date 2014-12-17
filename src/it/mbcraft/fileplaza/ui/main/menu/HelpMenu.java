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

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.main.menu.help.actions.HelpAboutAction;
import it.mbcraft.fileplaza.ui.main.menu.help.actions.HelpSuggestionsAction;
import it.mbcraft.fileplaza.ui.main.menu.help.actions.HelpUserGuideAction;

/**
 * This class handles the state of the Help menu.  
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.HelpMenu")
public class HelpMenu extends AbstractMenu {
    
    public HelpMenu() {
        
        super(L("main.menu.HelpMenu", "Help_Menu"));
        
        addItem(L(this,"UserGuide_Item"),new HelpUserGuideAction());
        
        addItem(L(this,"Suggestions_Item"),new HelpSuggestionsAction());
        
        //no registration needed
        //addItem(L(this,"Register_Item"),new HelpRegisterAction());
        
        addSeparator();
        
        addItem(L(this,"About_Item"),new HelpAboutAction());
        
    }
}
