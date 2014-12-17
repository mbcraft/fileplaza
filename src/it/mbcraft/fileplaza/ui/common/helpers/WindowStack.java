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

package it.mbcraft.fileplaza.ui.common.helpers;

import java.util.Stack;
import javafx.stage.Stage;

/**
 * This class rapresents the stack of windows of the current application.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class WindowStack {
    
    private static final Stack<Stage> windowStack = new Stack();
    
    public static void push(Stage st) {
        windowStack.push(st);
    }
    
    public static void pop() {
        windowStack.pop();
    }
    
    public static Stage top() {
        return windowStack.peek();
    }
    
    public static Stage getRoot() {
        return windowStack.firstElement();
    }
}
