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
