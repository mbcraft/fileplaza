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

package it.mbcraft.fileplaza.ui.common.components;

import javafx.scene.control.TableView;

/**
 * Improved table view with refresh support.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ImprovedTableView<T> extends TableView<T> implements IRefreshable<T> {

    /**
     * Refreshes all the items in the list view
     */
    @Override
    public void refreshAllItems() {
        int count = getItems().size();
        for (int i=0;i<count;i++)
            refreshIndex(i);
    }
    
    /**
     * Refreshes the item at position i inside the TableView.
     * 
     * @param index The index of the item to refresh
     */
    @Override
    public void refreshIndex(int index) {
        T item = getItems().get(index);
        getItems().remove(index);
        getItems().add(index, item);
    }
    
    /**
     * Refreshes an arbitrary item on the TableView
     * 
     * @param item The item to refresh
     */
    @Override
    public void refreshItem(T item) {
        int index = getItems().indexOf(item);
        getItems().remove(index);
        getItems().set(index, item);
    }
    
    /**
     * Refreshes the current selected item
     */
    @Override
    public void refreshSelectedItem() {
        refreshIndex(getSelectionModel().getSelectedIndex());
    }
    
}
