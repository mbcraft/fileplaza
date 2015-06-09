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
