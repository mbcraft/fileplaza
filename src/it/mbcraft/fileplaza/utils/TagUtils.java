/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package it.mbcraft.fileplaza.utils;

import it.mbcraft.fileplaza.data.models.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class TagUtils {
    public static Properties toProperties(List<Tag> tags) {
        Properties pt = new Properties();
        for (Tag t : tags) {
            
        }
        
        return pt;
    }
    
    public static List<Tag> fromProperties(Properties pt) {
        List<Tag> tags = new ArrayList<>();
        boolean moreTags = true;
        int index = 1;
        while (moreTags) {
            String key = "tag."+index;
            if (pt.containsKey(key)) {
                Tag t = buildTag(index,pt.getProperty(key));
                tags.add(t);
            } else
                moreTags = false;
            index++;
        }
        
        return tags;
    }
    
    public static Tag buildTag(int index,String clazz) {
        return null;
    }
}
