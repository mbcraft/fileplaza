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
