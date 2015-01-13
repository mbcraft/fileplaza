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

package com.guigarage.fx.grid;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 * @param <T> The type contained in this model
 */
public class GridViewMultipleSelectionModel<T> extends MultipleSelectionModel<T> {

        private final ReadOnlyObjectProperty<ObservableList<T>> itemsProperty;
        private final ObservableList<Integer> selectedIndices;

        public GridViewMultipleSelectionModel(ReadOnlyObjectProperty<ObservableList<T>> itemsProp) {
            itemsProperty = itemsProp;

            selectedIndices = FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.unmodifiableObservableList(selectedIndices);
        }

        @Override
        public ObservableList<T> getSelectedItems() {
            ObservableList selectedItems = FXCollections.observableArrayList();
            ObservableList items = itemsProperty.get();
            for (Integer i : selectedIndices)
                selectedItems.add(items.get(i));
            
            return FXCollections.unmodifiableObservableList(selectedItems);
        }

        @Override
        public void selectIndices(int i, int... ints) {
            for (Integer in : ints) {
                select(in);
            }
        }

        @Override
        public void selectAll() {
            clearSelection();
            int index = 0;
            int totalSize = itemsProperty.get().size();
            while (index<totalSize) {
                selectedIndices.add(index++);
            }
        }

        @Override
        public void selectFirst() {
            select(0);
        }

        @Override
        public void selectLast() {
            select(itemsProperty.get().size() - 1);
        }

        @Override
        public void clearAndSelect(int i) {
            clearSelection();
            select(i);
        }

        @Override
        public void select(int i) {
            if (!selectedIndices.contains(i) && i > -1 && i < itemsProperty.get().size()) {
                selectedIndices.add(i);
            }
        }

        @Override
        public void select(T t) {
            ObservableList items = itemsProperty.get();
            if (items.contains(t)) {
                int index = items.indexOf(t);
                select(index);
            }
        }

        @Override
        public void clearSelection(int i) {
            if (selectedIndices.contains(i)) {
                selectedIndices.remove((Object)i);
            }
        }

        @Override
        public void clearSelection() {
            selectedIndices.clear();
        }

        @Override
        public boolean isSelected(int i) {
            return selectedIndices.contains(i);
        }

        @Override
        public boolean isEmpty() {
            return selectedIndices.isEmpty();
        }

        @Override
        public void selectPrevious() {
            if (getSelectedIndex() > 1) {
                select(getSelectedIndex() - 1);
            }
        }

        @Override
        public void selectNext() {
            if (getSelectedIndex() < itemsProperty.get().size() - 1) {
                select(getSelectedIndex() + 1);
            }
        }

    }
