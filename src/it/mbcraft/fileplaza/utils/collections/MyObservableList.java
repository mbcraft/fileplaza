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

package it.mbcraft.fileplaza.utils.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class MyObservableList implements ObservableList {

    private final List<ListChangeListener> listChangeListeners = new ArrayList<>();
    
    private void fireChange(ListChangeListener.Change c) {
        for (ListChangeListener l : listChangeListeners)
            l.onChanged(c);
    }
    
    private final List myList;
    
    public MyObservableList(List embedded) {
        myList = embedded;
    }
    
    @Override
    public void addListener(ListChangeListener ll) {
        listChangeListeners.add(ll);
    }

    @Override
    public void removeListener(ListChangeListener ll) {
        listChangeListeners.remove(ll);
    }
    
    @Override
    public boolean addAll(Object... es) {
        Collection c = Arrays.asList(es);
        return myList.addAll(c);
    }

    @Override
    public boolean setAll(Object... es) {
        Collection c = Arrays.asList(es);
        myList.clear();
        return myList.addAll(c);
    }

    @Override
    public boolean setAll(Collection clctn) {
        myList.clear();
        return myList.addAll(clctn);
    }

    @Override
    public boolean removeAll(Object... es) {
        Collection c = Arrays.asList(es);
        for (Object o : es)
            c.add(o);
        return myList.removeAll(c);
    }

    @Override
    public boolean retainAll(Object... es) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(int i, int i1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int size() {
        return myList.size();
    }

    @Override
    public boolean isEmpty() {
        return myList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return myList.contains(o);
    }

    @Override
    public Iterator iterator() {
        return myList.iterator();
    }

    @Override
    public Object[] toArray() {
        return myList.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return myList.toArray(a);
    }

    @Override
    public boolean add(Object e) {
        return myList.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return myList.remove(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return myList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        return myList.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection c) {
        return myList.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return myList.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return myList.removeAll(c);
    }

    @Override
    public void clear() {
        myList.clear();
    }

    @Override
    public Object get(int index) {
        return myList.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        return myList.set(index, element);
    }

    @Override
    public void add(int index, Object element) {
        myList.add(index,element);
    }

    @Override
    public Object remove(int index) {
        Object result = myList.remove(index);
        
        return result;
    }

    //ok
    @Override
    public int indexOf(Object o) {
        return myList.indexOf(o);
    }

    //ok
    @Override
    public int lastIndexOf(Object o) {
        return myList.lastIndexOf(o);
    }

    //ok
    @Override
    public ListIterator listIterator() {
        return myList.listIterator();
    }

    //ok
    @Override
    public ListIterator listIterator(int index) {
        return myList.listIterator(index);
    }

    //ok
    @Override
    public List subList(int fromIndex, int toIndex) {
        return myList.subList(fromIndex, toIndex);
    }

    //ok
    @Override
    public void addListener(InvalidationListener il) {
        throw new UnsupportedOperationException("Invalidation listener is not supported.");
    }

    //ok
    @Override
    public void removeListener(InvalidationListener il) {
        throw new UnsupportedOperationException("Invalidation listener is not supported.");
    }
    
}
