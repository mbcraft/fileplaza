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

package it.mbcraft.fileplaza.ui.main.search;

import java.io.File;
import java.util.List;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SearchState {
    
    private final SetProperty<File> elementsFound = new SimpleSetProperty(FXCollections.observableSet());

    public void clear() {
        elementsFound.clear();
    }
    
    public void addResultList(List<File> results) {
       elementsFound.addAll(results);
    }

    public SetProperty<File> elementsFoundProperty() {
        return elementsFound;
    }
}
