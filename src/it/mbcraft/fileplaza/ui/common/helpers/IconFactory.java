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

package it.mbcraft.fileplaza.ui.common.helpers;

import it.mbcraft.fileplaza.Main;
import java.io.InputStream;
import java.security.InvalidParameterException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class IconFactory {
    
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
        ImageView result = new ImageView(new Image(Main.class.getResourceAsStream("graphics/icons/misc/"+name+".png")));
        result.setFitHeight(size);
        result.setFitWidth(size);

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
        
        InputStream stream = Main.class.getResourceAsStream("graphics/icons/file/"+size+"px/"+extension+"_"+size+".png");
        if (stream==null)
            stream = Main.class.getResourceAsStream("graphics/icons/file/"+size+"px/default_"+size+".png");
        
        ImageView result = new ImageView(new Image(stream));
        result.setFitWidth(size);
        result.setFitWidth(size);
        
        return result;
    }
    
    /**
     * @param countryCode
     * @return 
     */
    public static ImageView getFlagIconByCountryCode(String countryCode) {
        InputStream stream = Main.class.getResourceAsStream("graphics/icons/languages/"+countryCode+".png");
        if (stream==null)
            throw new InvalidParameterException("Language flag not found : " + countryCode);
        ImageView result = new ImageView(new Image(stream));
        return result;
    }

    public static Image getAppIconAsImage(int size) {
        return new Image(Main.class.getResourceAsStream("graphics/icons/logo/fileplaza_"+size+".jpg"));
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
