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

package it.mbcraft.fileplaza.ui.main.menu.help.userguide;

import it.mbcraft.fileplaza.i18n.Lang;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractPresenterWindow;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.web.WebView;

/**
 * Creates the the user guide window.
 * The window actually shows html files, depending on the current language.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.userguide.UserGuideWindow")
public class UserGuideWindow extends AbstractPresenterWindow {
    
    private WebView view;
    
    /**
     * Builds the UserGuideWindows. It doesn't need any parameter, the user guide
     * path is hardcoded.
     */
    public UserGuideWindow() {
        super(L(UserGuideWindow.class,"UserGuide_Window"));
        setPadding(new Insets(5));
    }
    
    @Override
    protected void initMiddleContent() {
        view = new WebView();
        view.setContextMenuEnabled(false);
        addToWindow(view);
    }

    @Override
    protected void reset() {
        try {
            String lang = Lang.getCurrentLanguage();
            
            Path currentRelativePath = Paths.get("docs/user_guide/"+lang+"/index.html");
            
            URL u = new URL("file://"+currentRelativePath.toAbsolutePath().toString());
            
            view.getEngine().load(u.toExternalForm());
        } catch (MalformedURLException ex) {
            Logger.getLogger(UserGuideWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

}
