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

package it.mbcraft.fileplaza.data.dao.config;

import it.mbcraft.fileplaza.data.models.config.ApplicationReview;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IFeedbackDAO {

    /**
     * Loads the singleton instance
     *
     * @return The instance
     */
    ApplicationReview load();

    /**
     * Saves the singleton instance
     *
     * @param review The instance to save
     */
    void save(ApplicationReview review);
    
}
