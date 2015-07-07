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

package it.mbcraft.fileplaza.ui.common.components.windows;

import static it.mbcraft.fileplaza.i18n.Lang.L;

/**
 * This abstract window class helps the creation of windows that
 * have an 'editing' behaviour.
 * It actually features a localized window title, and two editing modes :
 * EDIT and NEW.
 * 
 * TO FIX : edit title and new title should be passed as parameters and should
 * not depend on 'EditDictionary_Window'.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public abstract class AbstractEntityEditorWindow extends AbstractSettingsWindow {
    
    private boolean needsSave = false;
    
    public AbstractEntityEditorWindow(String title) {
        super(title,false);
        
    }
    
    public void setMode(AbstractEntityEditorWindow.Mode mode) {
        if (mode == AbstractEntityEditorWindow.Mode.EDIT) {
            setWindowTitle(L(this, "EditDictionary_Window"));
        } else {
            setWindowTitle(L(this, "NewDictionary_Window"));
        }
    }
    
    @Override
    protected void loadData() {
        needsSave = false;
    }

    @Override
    protected void saveData() {
        needsSave = true;
    }

    public boolean needsSave() {
        return needsSave;
    }
    
    public enum Mode {

        EDIT, NEW;
    }
}
