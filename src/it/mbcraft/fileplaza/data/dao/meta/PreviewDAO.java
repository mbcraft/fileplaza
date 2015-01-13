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

package it.mbcraft.fileplaza.data.dao.meta;

import it.mbcraft.fileplaza.data.serialization.managers.meta.PreviewManager;
import java.awt.image.BufferedImage;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class PreviewDAO implements IPreviewDAO {
    private static PreviewDAO _instance = null;
    
    private final PreviewManager _manager;
    
    public static PreviewDAO getInstance() {
        if (_instance==null)
            _instance = new PreviewDAO();
        return _instance;
    }
    
    private PreviewDAO() {
        _manager = new PreviewManager("");
    }
    
    @Override
    public BufferedImage find(String key) {
        return _manager.find(key);
    }
    
    @Override
    public String saveOrUpdate(BufferedImage image) {
        return _manager.saveOrUpdate(image);
    }
}
