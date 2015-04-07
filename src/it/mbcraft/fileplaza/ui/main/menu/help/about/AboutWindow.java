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

package it.mbcraft.fileplaza.ui.main.menu.help.about;

import it.mbcraft.fileplaza.Main;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractPresenterWindow;
import it.mbcraft.fileplaza.ui.common.helpers.ImageFactory;
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
        Image image = ImageFactory.getImage("logo_mbcraft_black_very_small");
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
