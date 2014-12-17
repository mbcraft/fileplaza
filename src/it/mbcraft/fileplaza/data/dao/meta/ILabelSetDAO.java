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

import it.mbcraft.fileplaza.data.models.LabelSet;
import java.io.File;
import java.util.List;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface ILabelSetDAO {

    List<LabelSet> findAll();

    /**
     * Searches for a value in all enabled label sets.
     *
     * @param value The value to check for
     *
     * @return The found type name, or null if not found
     */
    String findSetNameForValue(String value);

    LabelSet importFromFile(File f);

    void replaceAll(List<LabelSet> items);
    
}
