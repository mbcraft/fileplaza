/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
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
        sz = new FileElementManager("");
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
