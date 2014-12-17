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

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public interface IRefreshable<T> {

    /**
     * Refreshes all the items in the list view
     */
    void refreshAllItems();

    /**
     * Refreshes the item at position i inside the listview.
     *
     * @param index The index of the item to refresh
     */
    void refreshIndex(int index);

    /**
     * Refreshes an arbitrary item on the list
     *
     * @param item The item to refresh
     */
    void refreshItem(T item);

    /**
     * Refreshes the current selected item
     */
    void refreshSelectedItem();
    
}
