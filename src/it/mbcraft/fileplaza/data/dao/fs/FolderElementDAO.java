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

package it.mbcraft.fileplaza.data.dao.fs;

import it.mbcraft.fileplaza.data.models.FolderElement;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.fs.FolderElementManager;
import it.mbcraft.fileplaza.utils.DigestUtils;

import java.io.File;
import java.util.List;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FolderElementDAO implements IFolderElementDAO {
    private static FolderElementDAO instance = null;
    public static String FOLDER_INDEX_EXTENSION_BY_CONTENT = ".cxi";
    public static String FOLDER_INDEX_EXTENSION_BY_PATH = ".fxi";
    
    public static FolderElementDAO getInstance() {
        if (instance==null)
            instance = new FolderElementDAO();
        return instance;
    }

    public static String getFolderKeyFromContent(String path) {
        File dir = new File(path);
        String fullContentString = "";
        for (File f : dir.listFiles()) {
            fullContentString += f.getName();
            fullContentString += "|";
            fullContentString += f.lastModified();
            fullContentString += "|";
        }
        return DigestUtils.getSha256DigestForString(fullContentString) + FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_CONTENT;
    }

    public static boolean isIndexForFolder(File f) {
        String name = f.getName();
        String extension = name.substring(name.lastIndexOf("."));
        return extension.equals(FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_PATH) || extension.equals(FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_CONTENT);
    }

    public static String getFolderKeyFromPath(String path) {
        return DigestUtils.getSha256DigestForString(path) + FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_PATH;
    }
    
    private final AbstractModelManager sz;
    
    private FolderElementDAO() {
        sz = new FolderElementManager("");
    }
    
    @Override
    public List<FolderElement> findAll() {
        return sz.findAll();
    }
    
    @Override
    public FolderElement find(String key) {
        return (FolderElement) sz.find(key);
    }

    @Override
    public boolean hasKey(String key) {
        return sz.hasKey(key);
    }
}
