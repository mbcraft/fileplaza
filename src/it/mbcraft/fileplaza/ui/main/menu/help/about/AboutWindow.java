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

package it.mbcraft.fileplaza.ui.main.menu.help.about;

import it.mbcraft.fileplaza.Main;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.AbstractPresenterWindow;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.about.AboutWindow")
public class AboutWindow extends AbstractPresenterWindow {
    
    public AboutWindow() {
        super(L(AboutWindow.class,"About_Window"));
        setSpacing(10);
        setPadding(new Insets(10));
    }
    
    @Override
    protected void initMiddleContent() {
        Image image = new Image(Main.class.getResourceAsStream("graphics/images/logo_mbcraft_black_very_small.png"));
        ImageView i = new ImageView(image);
        
        addToWindow(new Label(L(this,"PoweredBy_Text")));
        addToWindow(i);
        addToWindow(new Label(L(this,"Copyright_Text")));
        addToWindow(new Label(L(this,"CurrentVersion_Label")+Main.getVersion()));
        addToWindow(new Label(L(this,"ReleaseDate_Label")+Main.getReleaseDate()));
    }

    @Override
    protected void reset() {
        //empty
    }

}
