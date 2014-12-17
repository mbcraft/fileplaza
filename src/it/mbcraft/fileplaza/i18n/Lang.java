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

package it.mbcraft.fileplaza.i18n;

import java.lang.annotation.Annotation;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Language helper class.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Lang {
    
    private static final Logger logger = Logger.getLogger("it.mbcraft.fileplaza.i18n.Lang");
    
    private static final String COMMON_PREFIX = "it.mbcraft.fileplaza.ui.";
    
    private static String currentPath = null;
    private static ResourceBundle currentBundle = null;
    
    public static void init(String language) {
        logger.config("Configuring Localization class ...");
        getAllSupportedLanguages();
        
        String langCode = getShortLanguageCode(language);
        logger.info("Setting current locale to : " + langCode);
        setLocale(langCode);
    }
    
    public static List<String> getAllSupportedLanguages() {
        return Arrays.asList(new String[] {"Italiano".intern(),"English".intern()});
    }
    
    private static String getShortLanguageCode(String language) {
        switch (language) {
            case "Italiano" : return "it";
            case "English" : return "en";
            default : throw new IllegalStateException("Language not supported!");
        }
    }
    
    public static void setLocale(Locale loc) {
        Locale.setDefault(loc);
    }
    
    public static void setLocale(String language, String country) {
        Locale.setDefault(new Locale(language, country));
    }
    
    public static void setLocale(String language) {
        Locale.setDefault(new Locale(language));
    }   
        
    /**
     * Common setup method. Loads a resource bundle as current
     * 
     * @param path The path of the bundle to load.
     */
    private static void setup(String path) {
        if (!path.equals(currentPath)) {
            currentBundle = getBundle(path);
            currentPath = path;
        }
    }
    
    /**
     * Returns a bundle found in the bundle package 
     *  (it.mbcraft.fileplaza.i18n)
     * @param path The second part of the path
     * @return The ResourceBundle found, or an exception if not found.
     */
    private static ResourceBundle getBundle(String path) {
        if (path==null) {
            logger.severe("Unable to found ResourceBundle : "+path);
            throw new InvalidParameterException("Bundle path can't be null.");
        }
        String fullPath = Lang.class.getPackage().getName()+"."+path;
        ResourceBundle result = ResourceBundle.getBundle(fullPath, Locale.getDefault());
        if (result==null)
            throw new InvalidParameterException("Bundle not found : "+fullPath);
        return result;
    }
    
    private static String setupWithWithPathAndGet(String from, String key) {
        setup(from.toString());
        return get(key);
    }
    
    private static String setupWithClassAndGet(Class from,String key) {
        Annotation an = from.getAnnotation(LangResource.class);
        if (an==null) {
            String path = from.getCanonicalName().substring(COMMON_PREFIX.length());
            setup(path);
        }
        else
            setup(((LangResource)an).value());
        return get(key);
    }
    
    private static String setupWithInstanceAndGet(Object from,String key) {
        Annotation an = from.getClass().getAnnotation(LangResource.class);
        if (an==null) {
            String path = from.getClass().getCanonicalName().substring(COMMON_PREFIX.length());
            setup(path);
        }
        else
            setup(((LangResource)an).value());
        return get(key);
    }
        
    private static String get(String key) {
        if (currentBundle==null) {
            throw new IllegalStateException("Bundle not configured!");
        }
        if (!currentBundle.containsKey(key))
            return "[["+currentPath+":"+key+"]]";
        return currentBundle.getString(key);
    }
    /**
     * Gets localized string from bundle. This method can be called in 3 ways :
     * - Passing a direct path of the localization bundle, dot separated,
     *      of the bundle starting from folder it.mbcraft.fileshape.i18n
     * - Passing a class with is used to read the LangResource bundle location
     *      annotation
     * - Passing an object instance, that is again used to read the LangResource
     *      annotation
     * 
     * @param from A string bundle path, a class or an instance of a LangResource annotated class
     * @param key The key of the string to retrieve
     * @return The localized string
     */
    public static String L(Object from,String key) {
        //direct path provided
        if (from instanceof String)
            return setupWithWithPathAndGet((String)from, key);
        //path get from class using "common prefix"
        if (from instanceof Class)
            return setupWithClassAndGet((Class)from, key);
        
        //using instance -> annotation
        return setupWithInstanceAndGet(from, key);
    }
    
    private static String[] setupWithWithPathAndGetArray(String from, String key) {
        setup(from.toString());
        return getArray(key);
    }
    
    private static String[] setupWithClassAndGetArray(Class from,String key) {
        Annotation an = from.getAnnotation(LangResource.class);
        if (an==null) throw new IllegalStateException("Missing LangResource annotation on localized class/instance : "+from.getClass().getName());
        setup(((LangResource)an).value());
        return getArray(key);
    }
    
    private static String[] setupWithInstanceAndGetArray(Object from,String key) {
        Annotation an = from.getClass().getAnnotation(LangResource.class);
        if (an==null) throw new IllegalStateException("Missing LangResource annotation on localized class/instance : "+from.getClass().getName());
        setup(((LangResource)an).value());
        return getArray(key);
    }
    
    private static String[] getArray(String key) {
        if (currentBundle==null)
            throw new IllegalStateException("Bundle not configured!");
        if (!currentBundle.containsKey(key))
            return new String[]{"[["+currentPath+":"+key+"]]"};
        return currentBundle.getStringArray(key);
    }
    
    /**
     * Gets localized string array from bundle. This method can be called in 3 ways :
     * - Passing a direct path of the localization bundle, dot separated,
     *      of the bundle starting from folder it.mbcraft.fileshape.i18n
     * - Passing a class with is used to read the LangResource bundle location
     *      annotation
     * - Passing an object instance, that is again used to read the LangResource
     *      annotation
     * 
     * @param from A string bundle path, a class or an instance of a LangResource annotated class
     * @param key The key of the string array to retrieve
     * @return The localized string array
     */
    public static String[] LL(Object from, String key) {
        if (from instanceof String) 
            return setupWithWithPathAndGetArray((String)from, key);
        if (from instanceof Class)
            return setupWithClassAndGetArray((Class)from, key);
        
        return setupWithInstanceAndGetArray(from, key);
    }

    public static String getCurrentLanguage() {
        return Locale.getDefault().getLanguage();
    }

}