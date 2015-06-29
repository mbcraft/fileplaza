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

package it.mbcraft.fileplaza.ui.common.helpers;

import java.util.Stack;
import javafx.stage.Stage;

/**
 * This class represents the stack of windows of the current application.
 * It is used to show windows stacked on top of each other.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class WindowStack {
    
    private static final Stack<Stage> windowStack = new Stack();
    
    /**
     * Pushes a window on top of the window stack.
     * 
     * @param st The stage representing the window to push.
     */
    public static void push(Stage st) {
        windowStack.push(st);
    }
    
    /**
     * Removes the topmost window from the window stack.
     */
    public static void pop() {
        windowStack.pop();
    }
    
    /**
     * Gets the top window from the window stack.
     * 
     * @return The topmost window as a Stage instance
     */
    public static Stage top() {
        return windowStack.peek();
    }
    
    /**
     * Returns the root Stage window from the window stack.
     * 
     * @return The Stage instance of the root window in this window stack.
     */
    public static Stage getRoot() {
        return windowStack.firstElement();
    }
}
