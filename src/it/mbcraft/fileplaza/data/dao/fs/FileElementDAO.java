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

import it.mbcraft.fileplaza.data.models.ChangeHistory;
import it.mbcraft.fileplaza.data.models.FileElement;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.fs.FileElementManager;
import it.mbcraft.fileplaza.utils.DigestUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileElementDAO implements IFileElementDAO {
    private static FileElementDAO instance = null;
    public static final String FILE_INDEX_EXTENSION_BY_CONTENT = ".dxi";
    public static final String FILE_INDEX_EXTENSION_BY_PATH = ".pxi";
    
    public static FileElementDAO getInstance() {
        if (instance==null)
            instance = new FileElementDAO();
        return instance;
    }

    public static String getFileKeyFromContent(String path) {
        return DigestUtils.getSha256ForFile(new File(path)) + FileElementDAO.FILE_INDEX_EXTENSION_BY_CONTENT;
    }

    public static String getFileKeyFromPath(String path) {
        return DigestUtils.getSha256DigestForString(path) + FileElementDAO.FILE_INDEX_EXTENSION_BY_PATH;
    }

    public static boolean isIndexForFile(File f) {
        String name = f.getName();
        String extension = name.substring(name.lastIndexOf("."));
        return extension.equals(FileElementDAO.FILE_INDEX_EXTENSION_BY_PATH) || extension.equals(FileElementDAO.FILE_INDEX_EXTENSION_BY_CONTENT);
    }

    public static boolean isIndexByPath(File f) {
        String name = f.getName();
        String extension = name.substring(name.lastIndexOf("."));
        return extension.equals(FileElementDAO.FILE_INDEX_EXTENSION_BY_PATH) || extension.equals(FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_PATH);
    }

    public static boolean isIndexByContent(File f) {
        String name = f.getName();
        String extension = name.substring(name.lastIndexOf("."));
        return extension.equals(FileElementDAO.FILE_INDEX_EXTENSION_BY_CONTENT) || extension.equals(FolderElementDAO.FOLDER_INDEX_EXTENSION_BY_CONTENT);
    }
    
    private final AbstractModelManager sz;
    
    private FileElementDAO() {
        sz = new FileElementManager();
    }
    
    @Override
    public List<FileElement> findAll() {
        return sz.findAll();
    }

    @Override
    public boolean move(FileElement el, File finalPath) {
        File source = new File(el.getCurrentPath());
        
        try {
            Files.move(source.toPath(), finalPath.toPath(), StandardCopyOption.REPLACE_EXISTING);
            el.setCurrentPath(finalPath.getAbsolutePath());
            el.addChangeHistory(new ChangeHistory(new Date(),ChangeHistory.ChangeType.PATH,source.getAbsolutePath()));
            sz.saveOrUpdate(el);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileElementDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public FileElement find(String key) {
        return (FileElement) sz.find(key);
    }
    
    @Override
    public boolean hasKey(String key) {
        return sz.hasKey(key);
    }
}
