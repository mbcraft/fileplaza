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
