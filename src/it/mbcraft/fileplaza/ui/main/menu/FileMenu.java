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
import it.mbcraft.fileplaza.ui.main.menu.file.actions.FileDictionariesSettingsAction;
import it.mbcraft.fileplaza.ui.main.menu.file.actions.FileLabelSetsSettingsAction;
import it.mbcraft.fileplaza.ui.main.menu.file.actions.FileQuitAction;
import it.mbcraft.fileplaza.ui.main.menu.file.actions.FileSettingsAction;

/**
 * This class handles the creation of the File menu for FilePlaza.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.FileMenu")
public class FileMenu extends AbstractMenu {
    
    public FileMenu() {
        super(L(FileMenu.class, "File_Menu"));
        
        addItem(L(this,"Dictionaries_Item"),new FileDictionariesSettingsAction());
        addItem(L(this,"LabelSets_Item"),new FileLabelSetsSettingsAction());
        
        addSeparator();
        
        addItem(L(this,"Settings_Item"),new FileSettingsAction());
        
        addSeparator();
        
        addItem(L(this,"Quit_Item"),FileQuitAction.getInstance());
    }
}
