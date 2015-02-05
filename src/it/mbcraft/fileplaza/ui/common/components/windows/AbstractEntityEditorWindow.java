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

package it.mbcraft.fileplaza.ui.common.components.windows;

import static it.mbcraft.fileplaza.i18n.Lang.L;

/**
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
