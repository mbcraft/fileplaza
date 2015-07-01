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

package com.guigarage.fx.grid.util;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.util.Callback;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.sun.javafx.collections.ObservableListWrapper;

public class GridPaginationHelper<T, U extends GridView<T>> {

	private Pagination pagination;

	private ObservableList<T> items;

	private Callback<GridView<T>, GridCell<T>> gridCellFactory;

	private Callback<Integer, Node> pageFactory;

	private DoubleProperty cellWidth;

	private DoubleProperty cellHeight;

	private DoubleProperty horizontalCellSpacing;

	private DoubleProperty verticalCellSpacing;

	private ChangeListener<Number> defaultUpdateListener;
	
	@SuppressWarnings("unchecked")
	public GridPaginationHelper(Pagination pagination,
			final ObservableList<T> items,
			Callback<GridView<T>, GridCell<T>> gridCellFactory) throws InstantiationException, IllegalAccessException {
            this(pagination, items, gridCellFactory, (Class) GridView.class);
	}

	public GridPaginationHelper(Pagination pagination,
			final ObservableList<T> items,
			Callback<GridView<T>, GridCell<T>> gridCellFactory,
			final Class<U> gridViewClass) throws InstantiationException, IllegalAccessException {
		this.pagination = pagination;
		this.items = items;
		this.gridCellFactory = gridCellFactory;
		
		defaultUpdateListener = new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> obs,
					Number oldValue, Number newValue) {
				update();
			}
		};

		pageFactory = new Callback<Integer, Node>() {

			@Override
			public Node call(Integer arg0) {
				GridView<T> gridView = null;
				try {
					gridView = gridViewClass.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					throw new RuntimeException(e);
				}
				int startIndex = getCellStartIndexForPage(arg0);
				ObservableList<T> currentItems = new ObservableListWrapper<>(
						items.subList(
								startIndex,
								Math.min(startIndex
										+ calcMaxVisibleCellsPerPage(),
										items.size())));
				gridView.setItems(currentItems);
				gridView.setCellFactory(GridPaginationHelper.this.gridCellFactory);

                                gridView.horizontalCellSpacingProperty().bind(GridPaginationHelper.this.horizontalCellSpacingProperty());
				gridView.verticalCellSpacingProperty().bind(GridPaginationHelper.this.verticalCellSpacingProperty());
				
                                gridView.horizontalCellSpacingProperty().addListener(defaultUpdateListener);
				gridView.verticalCellSpacingProperty().addListener(defaultUpdateListener);
				
				return gridView;
			}
		};

		//TODO: Initialisierung der Werte, hier bin ich noch nicht ganz mit zufrieden...
		U dummyGridView = gridViewClass.newInstance();

		verticalCellSpacingProperty().setValue(dummyGridView.getVerticalCellSpacing());
		horizontalCellSpacingProperty().setValue(dummyGridView.getHorizontalCellSpacing());
		
		items.addListener(new ListChangeListener<T>() {

			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends T> arg0) {
				update();
			}
		});

		//TODO: Eigentlich ist das nicht richtig, da die Pagination auch die NavigationNodes beinhaltet und die Seite daher kleiner ist. Task bei JavaFX-Jira aufmachen
		pagination.widthProperty().addListener(defaultUpdateListener);

		//TODO: Eigentlich ist das nicht richtig, da die Pagination auch die NavigationNodes beinhaltet und die Seite daher kleiner ist. Task bei JavaFX-Jira aufmachen
		pagination.heightProperty().addListener(defaultUpdateListener);

		pagination.setPageFactory(pageFactory);
	}

	private void update() {
		int firstCellOnPage = calcMaxVisibleCellsPerPage() * pagination.getCurrentPageIndex();
		pagination.setPageCount(calcPageCount());
		pagination.setCurrentPageIndex((int) Math.floor(firstCellOnPage / calcMaxVisibleCellsPerPage()));
	}

	private int calcMaxVisibleCellsPerPage() {
		return Math.max(1, computeMaxCellsInOneRow() * computeMaxRowsPerPage());
	}

	private int computeMaxRowsPerPage() {
		double cellHeight = getCellHeight() + getVerticalCellSpacing() + getVerticalCellSpacing();
		return (int) Math.floor((pagination.getHeight() - 64) / cellHeight);
	}
	
	private int computeMaxCellsInOneRow() {
		double cellWidth = getHorizontalCellSpacing() + getCellWidth() + getHorizontalCellSpacing();
		return (int) Math.floor(pagination.getWidth() / cellWidth);
	}
	
	private int getCellStartIndexForPage(int pageIndex) {
		return calcMaxVisibleCellsPerPage() * pageIndex;
	}

	private int calcPageCount() {
		return (int) Math.floor(items.size() / calcMaxVisibleCellsPerPage());
	}

	public void setHorizontalCellSpacing(double value) {
		horizontalCellSpacingProperty().set(value);
	}

	public double getHorizontalCellSpacing() {
		return horizontalCellSpacing == null ? null : horizontalCellSpacing
				.get();
	}

	public final DoubleProperty horizontalCellSpacingProperty() {
		if (horizontalCellSpacing == null) {
			horizontalCellSpacing = new SimpleDoubleProperty(this,
					"horizontalCellSpacing");
		}
		return horizontalCellSpacing;
	}

	public void setVerticalCellSpacing(double value) {
		verticalCellSpacingProperty().set(value);
	}

	public double getVerticalCellSpacing() {
		return verticalCellSpacing == null ? null : verticalCellSpacing.get();
	}

	public final DoubleProperty verticalCellSpacingProperty() {
		if (verticalCellSpacing == null) {
			verticalCellSpacing = new SimpleDoubleProperty(this,
					"verticalCellSpacing");
		}
		return verticalCellSpacing;
	}

	public final DoubleProperty cellWidthProperty() {
		if (cellWidth == null) {
			cellWidth = new SimpleDoubleProperty(this, "cellWidth");
		}
		return cellWidth;
	}

	public void setCellWidth(double value) {
		cellWidthProperty().set(value);
	}

	public double getCellWidth() {
		return cellWidth == null ? null : cellWidth.get();
	}

	public final DoubleProperty cellHeightProperty() {
		if (cellHeight == null) {
			cellHeight = new SimpleDoubleProperty(this, "cellHeight");
		}
		return cellHeight;
	}

	public void setCellHeight(double value) {
		cellHeightProperty().set(value);
	}

	public double getCellHeight() {
		return cellHeight == null ? null : cellHeight.get();
	}
}
