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
package it.mbcraft.fileplaza.ui.common.components.tileview;

import com.guigarage.fx.grid.GridView;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 *
 * @param <T> The type of element viewed inside this view.
 */
public class TileView<T> {
  

    /*
    private class TileViewUpdater<T> implements ListChangeListener<T>,InvalidationListener {

        private final TileView<T> myView;
        private final ObjectProperty<Callback<TileView<T>, TileCell<T>>> internalCellFactoryProperty;
        private final ObjectProperty<ObservableList<T>> internalItemsProperty;
        private final ObjectProperty<MultipleSelectionModel<T>> internalSelectionModelProperty;
        private final ObservableList<Node> tileChildrens;

        private TileViewUpdater(TileView<T> view) {
            myView = view;
            internalCellFactoryProperty = view.cellFactoryProperty;
            internalItemsProperty = view.itemsProperty;
            internalSelectionModelProperty = view.selectionModelProperty;
            tileChildrens = view.root.getChildren();
        }
        
        private void moveElement(int from,int to) {
            Node n = tileChildrens.remove(from);
            tileChildrens.add(to, n);
            
            ObservableList<Integer> selected = internalSelectionModelProperty.get().getSelectedIndices();
            if (selected.contains(from)) {
                internalSelectionModelProperty.get().clearSelection(from);
                internalSelectionModelProperty.get().select(to);
            }
        }
        
        private void updateElement(int index) {
            TileCell cell = (TileCell) tileChildrens.get(index);
            cell.setItem(internalItemsProperty.get().get(index));
        }
        
        private void removeElement(T toRemove, int pos) {
            tileChildrens.remove(pos);
            ObservableList<Integer> selected = internalSelectionModelProperty.get().getSelectedIndices();
            if (selected.contains(pos)) {
                internalSelectionModelProperty.get().clearSelection(pos);
            }
        }
        
        private void addElement(T toAdd, int pos) {
            TileCell cell = internalCellFactoryProperty.get().call(myView);
            cell.setItem(toAdd);
            tileChildrens.add(pos, cell);
        }

        @Override
        public void onChanged(Change<? extends T> ch) {
            System.out.println("Data is changed!!");
            while (ch.next()) {
                if (ch.wasPermutated()) {
                     for (int i = ch.getFrom(); i < ch.getTo(); ++i) {
                          moveElement(i,ch.getPermutation(i));
                     }
                 } else if (ch.wasUpdated()) {
                    for (int i = ch.getFrom(); i < ch.getTo(); ++i)
                        updateElement(i);
                 } else {
                     int index = ch.getFrom();
                     for (T remitem : ch.getRemoved()) {
                         removeElement(remitem,index++);
                     }
                     for (T additem : ch.getAddedSubList()) { 
                         addElement(additem,index++);
                     }
                 }
            }

        }

        @Override
        public void invalidated(Observable o) {
            System.out.println("The element list was invalidated!");
        }

    }
    */

    private class TileViewModelPropertyChangeListener implements ChangeListener {

        private final ListChangeListener itemListener;

        public TileViewModelPropertyChangeListener(ListChangeListener l) {
            itemListener = l;
        }

        @Override
        public void changed(ObservableValue ov, Object oldValue, Object newValue) {
            System.out.println("A change occurred in the element list!");
            ObservableList oldList = (ObservableList) oldValue;
            if (oldList != null) {
                oldList.removeListener(itemListener);
            }
            ObservableList newList = (ObservableList) newValue;
            newList.addListener(itemListener);
        }

    }

    private class TileViewMultipleSelectionModel<T> extends MultipleSelectionModel<T> {

        private final ObjectProperty<ObservableList<T>> itemsProperty;

        private final ObservableList<Integer> selectedIndices;
        private final ObservableList<T> selectedItems;

        public TileViewMultipleSelectionModel(ObjectProperty<ObservableList<T>> itemsProp) {
            itemsProperty = itemsProp;

            selectedIndices = FXCollections.observableArrayList();
            selectedItems = FXCollections.observableArrayList();
        }

        @Override
        public ObservableList<Integer> getSelectedIndices() {
            return FXCollections.unmodifiableObservableList(selectedIndices);
        }

        @Override
        public ObservableList<T> getSelectedItems() {
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
            for (T item : itemsProperty.get()) {
                selectedIndices.add(index);
                selectedItems.add(item);
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
                selectedItems.add(itemsProperty.get().get(i));
            }
        }

        @Override
        public void select(T t) {
            if (!selectedItems.contains(t) && itemsProperty.get().contains(t)) {
                int index = itemsProperty.get().indexOf(t);
                selectedIndices.add(index);
                selectedItems.add(t);
            }
        }

        @Override
        public void clearSelection(int i) {
            if (selectedIndices.contains(i)) {
                T selected = itemsProperty.get().get(i);
                selectedIndices.remove(i);
                selectedItems.remove(selected);
            }
        }

        @Override
        public void clearSelection() {
            selectedIndices.clear();
            selectedItems.clear();
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

}
