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
