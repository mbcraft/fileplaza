/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza.tmp;

import java.awt.IllegalComponentStateException;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Locale;
import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.accessibility.AccessibleRole;
import javax.accessibility.AccessibleStateSet;
import javax.accessibility.AccessibleText;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class AccessibleTest extends AccessibleContext implements  AccessibleText{


    private final String TEST = "This is an accessible text!";

    @Override
    public int getIndexAtPoint(Point p) {
        return 0;
    }

    @Override
    public Rectangle getCharacterBounds(int i) {
        return null;
    }

    @Override
    public int getCharCount() {
        return TEST.length();
    }

    @Override
    public int getCaretPosition() {
        return 0;
    }

    @Override
    public String getAtIndex(int part, int index) {
        return TEST;
    }

    @Override
    public String getAfterIndex(int part, int index) {
        return TEST;
    }

    @Override
    public String getBeforeIndex(int part, int index) {
        return TEST;
    }

    @Override
    public AttributeSet getCharacterAttribute(int i) {
        return new SimpleAttributeSet();
    }

    @Override
    public int getSelectionStart() {
        return 0;
    }

    @Override
    public int getSelectionEnd() {
        return 0;
    }

    @Override
    public String getSelectedText() {
        return "";
    }
    
    @Override
    public AccessibleRole getAccessibleRole() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AccessibleStateSet getAccessibleStateSet() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAccessibleIndexInParent() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAccessibleChildrenCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Accessible getAccessibleChild(int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Locale getLocale() throws IllegalComponentStateException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
