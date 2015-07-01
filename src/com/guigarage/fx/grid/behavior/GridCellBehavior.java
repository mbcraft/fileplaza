/**
 *  These classes were taken from an OLD guigarage repository release 
 *  and are NOT subject to any license now set inside their releases.
 *  (the packages moved and evolved inside the jfxlabs project).
 *  Anyway the jfxlabs project classes were not suitable for me since
 *  i needed the JavaFX 2.2 compatibility.
 *  Copyright were not present in the release i found (they were left in a 
 *  'do what you want' license). Anyway the package name will be left intact 
 *  in order to recognize the original author.
 *  There were heavily modified by MBCRAFT and the
 *  logic has almost completely rewritten to fit the desired behavior.
 * 
 *  -----------------------------------------------------------------------
 * 
 *  FilePlaza - a tag based file manager
 *  Copyright (C) 2015 - Marco Bagnaresi
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.guigarage.fx.grid.behavior;

import com.guigarage.fx.grid.GridCell;
import com.sun.javafx.scene.control.behavior.CellBehaviorBase;

public class GridCellBehavior<T> extends CellBehaviorBase<GridCell<T>> {
	public GridCellBehavior(GridCell<T> control) {
		super(control);
	}
}
