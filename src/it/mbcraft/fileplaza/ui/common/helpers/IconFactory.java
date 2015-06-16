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

import it.mbcraft.fileplaza.ui.common.IconReference;
import it.mbcraft.fileplaza.Main;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.HashMap;
import javafx.scene.CacheHint;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class IconFactory {
    
    //caches for images (features and files)
    private static final HashMap<String,Image> featureIconImageCache = new HashMap<>();
    private static final HashMap<String,Image> fileIconImageCache = new HashMap<>();

    /**
     * Returns an icon that can be used inside the program.
     * TODO: To evaluate if i should buy another license of this icon. 
     * For testing is OK.
     * 
     * @param name The name of the icon
     * @param size The size
     * @return The icon to use
     */
    public static ImageView getFeatureIcon(String name,double size) {
        
        Image im;
        if (featureIconImageCache.containsKey(name))
            im = featureIconImageCache.get(name);
        else
        {
            InputStream is = Main.class.getResourceAsStream("graphics/official/icons/misc/"+name+".png");
            if (is==null)
                is = Main.class.getResourceAsStream("graphics/stub/icons/misc/"+name+".png");

            if (is==null)
                throw new IllegalStateException("Feature icon image not found : name:"+name+" size:"+size);
            im = new Image(is);
            featureIconImageCache.put(name, im);
        }
        
        ImageView result = new ImageView(im);
        result.setFitHeight(size);
        result.setFitWidth(size);
        
        result.setCache(true);
        result.setCacheHint(CacheHint.SPEED);
        
        return result;
    }
    
    /**
     * Returns an icon for a file
     * 
     * @param extension The extension of the file, lower case
     * @param size the size of the icon
     * @return The ImageView with the icon
     */
    
    private static boolean allowedFileIconSize(int size) {
        return (size==16 || size==24 || size==32 || size== 48 || size== 96 || size==128 || size==256);
    }
    
    /**
     * Returns an icon for a file
     * 
     * @param extension The extension of the file, lower case
     * @param size the size of the icon
     * @return The ImageView with the icon
     */
    public static ImageView getFileIconByExtension(String extension, int size) {
        if (!allowedFileIconSize(size)) 
            throw new InvalidParameterException("Icon size not admitted : "+size);
        Image im;
        if (fileIconImageCache.containsKey(extension)) {
            im = fileIconImageCache.get(extension);
        } else {
            
            InputStream stream = Main.class.getResourceAsStream("graphics/official/icons/file/"+size+"px/"+extension+"_"+size+".png");
            if (stream==null)
                stream = Main.class.getResourceAsStream("graphics/official/icons/file/"+size+"px/default_"+size+".png");

            if (stream==null) {
                stream = Main.class.getResourceAsStream("graphics/stub/icons/file/"+size+"px/"+extension+"_"+size+".png");
                if (stream==null)
                    stream = Main.class.getResourceAsStream("graphics/stub/icons/file/"+size+"px/default_"+size+".png");
            }
        
            if (stream==null)
                throw new IllegalStateException("File icon or default icon image file not found : extension:"+extension+" size:"+size);
            
            im = new Image(stream);
            fileIconImageCache.put(extension, im);
        }
        
        ImageView result = new ImageView(im);
        result.setFitWidth(size);
        result.setFitWidth(size);
        
        result.setCache(true);
        result.setCacheHint(CacheHint.SPEED);
        
        return result;
    }
    
    /**
     * @param countryCode
     * @return 
     */
    public static ImageView getFlagIconByCountryCode(String countryCode) {
        InputStream stream = Main.class.getResourceAsStream("graphics/official/icons/languages/"+countryCode+".png");
        if (stream==null)
            throw new IllegalStateException("Language flag image not found : " + countryCode);
        
        ImageView result = new ImageView(new Image(stream));
        return result;
    }

    public static Image getAppIconAsImage(int size) {
        InputStream is = Main.class.getResourceAsStream("graphics/official/icons/logo/fileplaza_"+size+".jpg");
        if (is==null)
            is = Main.class.getResourceAsStream("graphics/stub/icons/logo/fileplaza_"+size+".jpg");
        
        if (is==null)
            throw new IllegalStateException("App icon image not found.");
        
        return new Image(is);
    }

    public static ImageView getIconByReference(IconReference ref, int size) {
        switch (ref.getIconType()) {
            case FILE : return getFileIconByExtension(ref.getIconName(), size);
            case FLAG : return getFlagIconByCountryCode(ref.getIconName());
            case MISC : return getFeatureIcon(ref.getIconName(), size);
            default : throw new IllegalStateException("IconCategory not implemented!");
        }
        
    }
}
