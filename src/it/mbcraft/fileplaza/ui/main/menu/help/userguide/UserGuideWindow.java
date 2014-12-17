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

package it.mbcraft.fileplaza.ui.main.menu.help.userguide;

import it.mbcraft.fileplaza.i18n.Lang;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.AbstractPresenterWindow;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.scene.web.WebView;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.userguide.UserGuideWindow")
public class UserGuideWindow extends AbstractPresenterWindow {
    
    private WebView view;
    
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
