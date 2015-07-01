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

package com.guigarage.fx.grid;

import java.util.HashMap;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Builder;
import javafx.util.Callback;

public class GridViewBuilder<B extends GridViewBuilder<B,T>, T> implements
		Builder<GridView<T>> {
	private static final String ITEMS = "items";
	private static final String CELL_FACTORY = "cellFactory";
	private static final String HORIZONTAL_CELL_SPACING = "horizontalCellSpacing";
	private static final String VERTICAL_CELL_SPACING = "verticalCellSpacing";
	@SuppressWarnings("rawtypes")
	private HashMap<String, Property> properties = new HashMap<String, Property>();

	protected GridViewBuilder() {
		super();
	};

	public static final <T, U extends GridViewBuilder<U,T>> GridViewBuilder<U, T> create(Class<T> cls) {
		return new GridViewBuilder<>();
	}

	public final GridViewBuilder<B,T> items(ObservableList<T> items) {
		properties.put(ITEMS, new SimpleObjectProperty<ObservableList<T>>(items));
		return this;
	}

	public final GridViewBuilder<B,T> cellFactory(Callback<GridView<T>, GridCell<T>> cellFactory) {
		properties.put(CELL_FACTORY, new SimpleObjectProperty<Callback<GridView<T>, GridCell<T>>>(cellFactory));
		return this;
	}
	
	public final GridViewBuilder<B,T> horizontalCellSpacing(double horizontalCellSpacing) {
		properties.put(HORIZONTAL_CELL_SPACING, new SimpleObjectProperty<Double>(horizontalCellSpacing));
		return this;
	}
	
	public final GridViewBuilder<B,T> verticalCellSpacing(double verticalCellSpacing) {
		properties.put(VERTICAL_CELL_SPACING, new SimpleObjectProperty<Double>(verticalCellSpacing));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public GridView<T> build() {
		final GridView<T> control = new GridView<>();
		for (String key : properties.keySet()) {
			if (ITEMS.equals(key)) {
				control.setItems(((SimpleObjectProperty<ObservableList<T>>) properties
						.get(key)).get());
			} else if (CELL_FACTORY.equals(key)) {
				control.setCellFactory(((SimpleObjectProperty<Callback<GridView<T>, GridCell<T>>>) properties
						.get(key)).get());
			} else if (HORIZONTAL_CELL_SPACING.equals(key)) {
				control.setHorizontalCellSpacing(((SimpleObjectProperty<Double>) properties
						.get(key)).get());
			} else if (VERTICAL_CELL_SPACING.equals(key)) {
				control.setVerticalCellSpacing(((SimpleObjectProperty<Double>) properties
						.get(key)).get());
			}
		}
		return control;
	}

}