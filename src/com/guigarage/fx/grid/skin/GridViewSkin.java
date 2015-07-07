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
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.guigarage.fx.grid.skin;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Node;

import com.guigarage.fx.grid.GridCell;
import com.guigarage.fx.grid.GridView;
import com.guigarage.fx.grid.behavior.GridViewBehavior;
import com.guigarage.fx.grid.cell.DefaultGridCell;
import com.sun.javafx.scene.control.skin.SkinBase;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.CacheHint;

public class GridViewSkin<T> extends SkinBase<GridView<T>, GridViewBehavior<T>> {

    private final ListChangeListener<T> itemsListener;

    private final ChangeListener<Number> layoutListener;

    private final ChangeListener<ObservableList<T>> itemListChangedListener;
    
    private final GridCell<T> calcCellWidth = createCell();
    private final GridCell<T> calcCellHeight = createCell();
    
    private final List<GridCell<T>> usedCells = new ArrayList<>();
    private final List<GridCell<T>> unusedCells = new ArrayList<>();

    public GridViewSkin(GridView<T> control) {
        super(control, new GridViewBehavior<>(control));

        setCache(true);
        setCacheHint(CacheHint.SPEED);
        //preparing cache ...
        for (int i=0;i<300;i++) {
            unusedCells.add(createCell());
        }
        
        layoutListener = new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> ov,
                    Number oldNumber, Number newNumber) {
                requestLayout();
            }
        };

        itemsListener = new ListChangeListener<T>() {
            @Override
            public void onChanged(Change<? extends T> change) {
                
                while (change.next()) {
                    if (change.wasPermutated()) {
                        for (int i = change.getFrom();i<change.getTo();i++)
                            updateCell(i);
                    } else if (change.wasUpdated()) {
                        for (int i=change.getFrom();i<change.getTo();i++)
                            updateCell(i);
                    } else {
                        if (change.wasRemoved()) {
                            for (int i=change.getFrom();i<change.getFrom()+change.getRemovedSize();i++) {
                                removeCell(change.getFrom());
                            }
                            
                        }
                        //was replaced is removed+added
                        if (change.wasAdded()) {
                            for (int i = change.getFrom();i<change.getTo();i++)
                                addCell(i);
                        }
                    }
                }
                
                requestLayout();
            }
        };

        itemListChangedListener = new ChangeListener<ObservableList<T>>() {

            @Override
            public void changed(
                    ObservableValue<? extends ObservableList<T>> obs,
                    ObservableList<T> oldList, ObservableList<T> newList) {
                if (oldList != null) {
                    oldList.removeListener(itemsListener);
                }
                if (newList != null) {
                    newList.addListener(itemsListener);
                }

                updateAllCells();
            }
        };

        getSkinnable()
                .itemsProperty().addListener(itemListChangedListener);
        ObservableList<T> currentList = getSkinnable().itemsProperty().get();
        if (currentList
                != null) {
            currentList.addListener(itemsListener);
        }

        getSkinnable()
                .verticalCellSpacingProperty()
                .addListener(layoutListener);
        getSkinnable()
                .horizontalCellSpacingProperty().addListener(
                        layoutListener);

        updateAllCells();
    }

    public final void updateAllCells() {
        
        for (Node n : getChildren())
        {
            getChildren().remove(n);
            unusedCells.add((GridCell<T>) n);
        }
            
        ObservableList<T> items = getSkinnable().getItems();

        ArrayList<Node> toAdd = new ArrayList();
        
        if (items != null) {
            for (int index = 0; index < items.size(); index++) {
                T item = items.get(index);
                GridCell<T> cell = getCachedCell();
                cell.setItem(item);
                cell.updateIndex(index);
                toAdd.add(cell);
            }
            getChildren().addAll(toAdd);
        }
        
        requestLayout();
    }

    private void removeCell(int index) {
        GridCell<T> removed = (GridCell<T>) getChildren().remove(index);
        usedCells.remove(removed);
        unusedCells.add(removed);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void updateCell(int index) {
        T item = getSkinnable().getItems().get(index);
        ((GridCell) getChildren().get(index)).setItem(item);
    }

    private void addCell(int index) {
        T item = getSkinnable().getItems().get(index);
        GridCell<T> cell = getCachedCell();

        cell.setItem(item);
        cell.updateIndex(index);

        getChildren().add(index, cell);
    }
        
    private GridCell<T> getCachedCell() {
        GridCell<T> cell;
        if (unusedCells.isEmpty()) {
            cell = createCell();
        } else {
            cell = unusedCells.remove(0);
        }
        usedCells.add(cell);        
        return cell;
    }
    
    private GridCell<T> createCell() {
        GridCell<T> cell;
        if (getSkinnable().getCellFactory() != null) {
            cell = getSkinnable().getCellFactory().call(getSkinnable());
        } else {
            cell = createDefaultCellImpl();
        }

        return cell;
    }

    protected GridCell<T> createDefaultCellImpl() {
        return new DefaultGridCell<>();
    }

    /*
    Compute the required cell width with spacing included
    */
    protected double computeRequiredCellWidthWithSpacing() {
        double cellWidth = 0;

        for (T n : getSkinnable().getItems()) {
            calcCellWidth.setItem(n);
            if (calcCellWidth.requiredCellWidthProperty().get() > cellWidth) {
                cellWidth = calcCellWidth.requiredCellWidthProperty().get();
            }
        }
        //computing cell width with spacing between cells included
        double finalCellWidth = cellWidth + 2 * getSkinnable().getHorizontalCellSpacing();
        
        return finalCellWidth;
    }
    
    /**
     * Compute the required cell height with spacing included
     * 
     * @param cellIndex the index of the cell
     * @return 
     */
    protected double computeRequiredCellHeightWithSpacing(int cellIndex) {
        double cellHeight = 0;
         
        calcCellHeight.setItem(getSkinnable().getItems().get(cellIndex));
        if (calcCellHeight.requiredCellHeightProperty().get() > cellHeight) {
            cellHeight = calcCellHeight.requiredCellHeightProperty().get();
        }
        
        //computing cell height with spacing between cells included
        double finalCellHeight = cellHeight + 2 * getSkinnable().getVerticalCellSpacing();
        
        return finalCellHeight;
    }

    /**
     * Compute the required row height of the given row
     * @param rowIndex The index of the row
     * @return The height of the row
     */
    protected double computeRowHeight(int rowIndex, double width, double requiredCellWidthWithSpacing) {
        int maxCellsInRow = computeMaxCellCountInRow(width, requiredCellWidthWithSpacing);
        int start = maxCellsInRow * rowIndex;
        int totalItemCount = getSkinnable().getItems().size();
        int end = (start + maxCellsInRow) < totalItemCount ? (start + maxCellsInRow) : totalItemCount;
        double height = 0;

        for (int i = start; i < end; i++) {

            if (height < computeRequiredCellHeightWithSpacing(i)) {
                height = computeRequiredCellHeightWithSpacing(i);
            }
        }
        
        return height;
    }

    /*
    Vertical alignment of cells is always TOP.
    Horizontal alignment can be LEFT,CENTER or RIGHT.
    Horizontal and vertical spacing is always included.
    */
    @Override
    protected void layoutChildren() {
        double currentWidth = getWidth();
        double xPos = 0;
        double yPos = 0;

        double requiredCellWidthWithSpacing = computeRequiredCellWidthWithSpacing();
        int maxCellCountInRow = computeMaxCellCountInRow(currentWidth,requiredCellWidthWithSpacing);
        double actualCellWidth = Math.floor(currentWidth / maxCellCountInRow);
        double halfRemainingWidthForCell = (actualCellWidth-requiredCellWidthWithSpacing) / 2;
        
        HPos currentHorizontalAlignment = getSkinnable().getHorizontalAlignment();

        int cellIndex = 0;
        int rowIndex = 0;
        double rowHeight = computeRowHeight(rowIndex, currentWidth, requiredCellWidthWithSpacing);
        //before doing children layout we must resize the container height, or at least
        //set the minimum size for it
        setMinHeight((getChildren().size()%maxCellCountInRow)*rowHeight);
        
        for (Node child : getChildren()) {
            
            //check for vertical spacing and update row if needed
            if (cellIndex!=0 && cellIndex%maxCellCountInRow==0) {
                xPos = 0;
                yPos+=rowHeight;
                rowIndex++;
                rowHeight = computeRowHeight(rowIndex, currentWidth, requiredCellWidthWithSpacing);
            }

            //before cell horizontal spacing
            if (currentHorizontalAlignment != null) {
                if (currentHorizontalAlignment.equals(HPos.CENTER)) {
                    xPos += halfRemainingWidthForCell;
                } else if (currentHorizontalAlignment.equals(HPos.RIGHT)) {
                    xPos += halfRemainingWidthForCell*2;
                } else {    //HPos.LEFT
                    //nothing to do
                }
            } else  //centered as default
                xPos += halfRemainingWidthForCell;
            
            //locating and resizing cell
            child.relocate(xPos, yPos);
            child.resize(requiredCellWidthWithSpacing, rowHeight);

            xPos+=requiredCellWidthWithSpacing;
            
            //after cell horizontal spacing
            if (currentHorizontalAlignment != null) {
                if (currentHorizontalAlignment.equals(HPos.CENTER)) {
                    xPos += halfRemainingWidthForCell;
                } else if (currentHorizontalAlignment.equals(HPos.RIGHT)) {
                    xPos += halfRemainingWidthForCell*2;
                } else {    //HPos.LEFT
                    //nothing to do
                }
            } else  //centered as default
                xPos += halfRemainingWidthForCell;
            
            cellIndex++;
        }
        
        prefHeightProperty().set(yPos+rowHeight);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see javafx.scene.layout.StackPane#getContentBias() Höhe des Grids ist
     * von dessen Breite abhängig. Je mehr Zellen in eine Zeile passen desto
     * geringer wird die Höhe
     */
    @Override
    public Orientation getContentBias() {
        return Orientation.HORIZONTAL;
    } 
    
    protected double computePrefCellWidth(double width,int maxCellCountInRow) {
        return Math.floor(width / maxCellCountInRow);
    }
    
    public int computeRowIndexForItem(int itemIndex, int maxCellCountInRow) {
        return itemIndex / maxCellCountInRow;
    }

    /*
    Gli elementi sono elencati da sinistra verso destra, dall'alto verso il basso
    */
    public int computeColumnIndexForItem(int itemIndex, int maxCellCountInRow) {
        return itemIndex % maxCellCountInRow;
    }

    /**
     * Compute the max number of cells in a row
     * 
     * @return The number of cells in a row
     */
    public int computeMaxCellCountInRow(double width, double cellWidthWithSpacing) {
        return Math.max((int) Math.floor(width / cellWidthWithSpacing), 1);
    }

    /**
     * Compute the current row count
     * 
     * @return The number of required rows
     */
    public int computeCurrentRowCount(int maxCellCountInRow) {
        return (int) Math.ceil((double) getSkinnable().getItems().size() / maxCellCountInRow);
    }
    
}
