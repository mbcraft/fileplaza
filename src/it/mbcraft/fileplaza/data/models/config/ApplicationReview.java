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

package it.mbcraft.fileplaza.data.models.config;


/**
 * Contains the application review for this application
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ApplicationReview {
    
    private int stars;
    private String reviewText;
    
    /**
     * Sets the number of stars (from 1 to 5) for this application.
     * 
     * @param stars the number of stars as an integer
     */
    public void setStars(int stars) {
        this.stars = stars;
    }
    
    /**
     * Gets the number of stars for this application
     * 
     * @return the number of stars as an integer
     */
    public int getStars() {
        return stars;
    }
    
    /**
     * Sets the review text for this application
     * 
     * @param text the review text as a string
     */
    public void setReviewText(String text) {
        this.reviewText = text;
    }
    
    /**
     * Gets the review text for this application
     * 
     * @return the review text as a string
     */
    public String getReviewText() {
        return reviewText;
    }

}
