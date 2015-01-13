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
