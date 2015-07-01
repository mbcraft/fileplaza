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

package it.mbcraft.fileplaza.data.dao.meta;

import it.mbcraft.fileplaza.data.serialization.managers.meta.PreviewManager;
import java.awt.image.BufferedImage;

/**
 * This DAO is used to read and write the preview for files.
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
