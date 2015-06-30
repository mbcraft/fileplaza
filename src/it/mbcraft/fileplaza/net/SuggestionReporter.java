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

package it.mbcraft.fileplaza.net;

import it.mbcraft.fileplaza.data.models.config.ApplicationReview;
import java.io.File;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * This class is used to send suggestions to the fileplaza web site, in order
 * to collect the data useful for improving the software.
 * 
 * TO BE IMPLEMENTED
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SuggestionReporter {
    
    /**
     * Reports a bug the the FilePlaza web site.
     * @param type the type of bug reported
     * @param title the title for the bug
     * @param description A brief description of the bug
     * @param attach1 First attachment, can be null
     * @param attach2 Second attachment, can be null
     * @param attach3 Third attachment, can be null
     */
    public void reportBug(int type,String title, String description, 
            File attach1, File attach2, File attach3) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
    }
    
    /**
     * Suggests a feature sending report data to the FilePlaza web site.
     * 
     * @param type The type of the feature to suggest
     * @param title The feature title
     * @param description The a brief description of the feature
     */
    public void suggestFeature(int type,String title, String description) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
    }
    
    /**
     * Updates the application review for this user, sending data to 
     * the FilePlaza web site.
     * 
     * @param review The ApplicationReview instance containing the application 
     * review data
     */
    public void updateApplicationReview(ApplicationReview review) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
    }
}
