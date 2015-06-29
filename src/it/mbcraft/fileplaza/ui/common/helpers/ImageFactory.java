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

package it.mbcraft.fileplaza.ui.common.helpers;

import it.mbcraft.fileplaza.Main;
import java.io.InputStream;
import javafx.scene.image.Image;

/**
 * Returns an image from the images folder embedded in the software.
 * It first searches on the 'official' folder. If the path is not valid (eg. there is not an 'official' folder)
 * it checks in the 'stub' folder.
 * 
 * An image must always be returned.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ImageFactory {
    public static final Image getImage(String name) {
        InputStream is = Main.class.getResourceAsStream("graphics/official/images/"+name+".png");
        if (is==null)
            is = Main.class.getResourceAsStream("graphics/stub/images/"+name+".png");
        if (is==null)
            throw new IllegalStateException("No image named "+name+" has been found on official or stub folders.");
        return new Image(is);
    }
}
