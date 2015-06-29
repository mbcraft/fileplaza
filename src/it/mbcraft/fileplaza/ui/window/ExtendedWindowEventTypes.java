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

package it.mbcraft.fileplaza.ui.window;

import javafx.event.EventType;
import javafx.stage.WindowEvent;

/**
 * This class contains extended window event types for the current window
 * implementation. Adds the 'half or full screen' and 'left or right' events.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ExtendedWindowEventTypes {
    
    static EventType<WindowEvent> HALF_OR_FULLSCREEN = new EventType<>();
    static EventType<WindowEvent> LEFT_OR_RIGHT = new EventType<>();
    
}
