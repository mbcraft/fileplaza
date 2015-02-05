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

package it.mbcraft.fileplaza.data.dao.config;

import it.mbcraft.fileplaza.data.models.config.ApplicationReview;
import it.mbcraft.fileplaza.data.serialization.managers.AbstractModelManager;
import it.mbcraft.fileplaza.data.serialization.managers.config.FeedbackManager;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FeedbackDAO implements IFeedbackDAO {
    
    private static FeedbackDAO instance;
    
    private final AbstractModelManager sz;
    
    private FeedbackDAO() {
        sz = new FeedbackManager("");
    }
    
    public static FeedbackDAO getInstance() {
        if (instance==null)
            instance = new FeedbackDAO();
        return instance;
    }
    
    /**
     * Loads the singleton instance
     * 
     * @return The instance
     */
    @Override
    public ApplicationReview load() {
        String key = sz.getModelKey(null);
        if (sz.hasKey(key))
            return (ApplicationReview) sz.find(key);
        else 
            return new ApplicationReview();
    }
    
    
    /**
     * Saves the singleton instance
     * 
     * @param review The instance to save
     */
    @Override
    public void save(ApplicationReview review) {
        sz.saveOrUpdate(review);
    }
}
