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

public class GridViewSkin<T> extends SkinBase<GridView<T>, GridViewBehavior<T>> {

    private final ListChangeListener<T> itemsListener;

    private final ChangeListener<Number> layoutListener;

    private final ChangeListener<ObservableList<T>> itemListChangedListener;

    public GridViewSkin(GridView<T> control) {
        super(control, new GridViewBehavior<>(control));

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
                    int start = change.getFrom();
                    int end = change.getTo();
                    for (int i = start; i < end; i++) {
                        if (change.wasPermutated()) {
                            // TODO: what to do know??
                            //System.out.println("change: Permutation");
                            updateAllCells();
                        } else if (change.wasUpdated()) {
                            //System.out.println("change: Update");
                            updateCell(i);
                        } else {
                            if (change.wasRemoved()) {
                                //System.out.println("change: Removed");
                                removeCell(i);
                            }
                            if (change.wasAdded()) {
                                //System.out.println("change: Added," + i);
                                //this method is called after zoom ... why??
                                addCell(i);
                            }
                        }
                    }
                }
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
        getChildren().clear();
        ObservableList<T> items = getSkinnable().getItems();
        if (items != null) {
            for (int index = 0; index < items.size(); index++) {
                T item = items.get(index);
                GridCell<T> cell = createCell();
                cell.setItem(item);
                cell.updateIndex(index);
                getChildren().add(cell);
            }
        }
        
        requestLayout();
    }

    private void removeCell(int index) {
        getSkinnable().getItems().remove(index);
        getChildren().remove(index);

        requestLayout();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void updateCell(int index) {
        T item = getSkinnable().getItems().get(index);
        ((GridCell) getChildren().get(index)).setItem(item);
        
        requestLayout();
    }

    private void addCell(int index) {
        T item = getSkinnable().getItems().get(index);
        GridCell<T> cell = createCell();

        cell.setItem(item);
        cell.updateIndex(index);

        getChildren().add(index, cell);

        requestLayout();
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
        GridCell<T> cell = createCell();
        double cellWidth = 0;
        for (T n : getSkinnable().getItems()) {
            cell.setItem(n);
            if (cell.requiredCellWidthProperty().get() > cellWidth) {
                cellWidth = cell.requiredCellWidthProperty().get();
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
        GridCell<T> cell = createCell();
        double cellHeight = 0;
         
        cell.setItem(getSkinnable().getItems().get(cellIndex));
        if (cell.requiredCellHeightProperty().get() > cellHeight) {
            cellHeight = cell.requiredCellHeightProperty().get();
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
    protected double computeRowHeight(int rowIndex) {
        int maxCellsInRow = computeMaxCellCountInRow();
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
        super.layoutChildren();

        double currentWidth = getWidth();
        double xPos = 0;
        double yPos = 0;

        double requiredCellWidth = computeRequiredCellWidthWithSpacing();
        double actualCellWidth = Math.floor(currentWidth / computeMaxCellCountInRow());
        double halfRemainingWidthForCell = (actualCellWidth-requiredCellWidth) / 2;
        
        HPos currentHorizontalAlignment = getSkinnable().getHorizontalAlignment();

        int cellIndex = 0;
        int rowIndex = 0;
        double rowHeight = computeRowHeight(rowIndex);
        for (Node child : getChildren()) {
            
            //check for vertical spacing and update row if needed
            if (cellIndex!=0 && cellIndex%computeMaxCellCountInRow()==0) {
                xPos = 0;
                yPos+=rowHeight;
                rowIndex++;
                rowHeight = computeRowHeight(rowIndex);
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
            child.resize(requiredCellWidth, rowHeight);

            xPos+=requiredCellWidth;
            
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
    
    protected double computePrefCellWidth() {
        return Math.floor(getWidth() / computeMaxCellCountInRow());
    }
    
    public int computeRowIndexForItem(int itemIndex) {
        int maxCellsInRow = computeMaxCellCountInRow();
        return itemIndex / maxCellsInRow;
    }

    /*
    Gli elementi sono elencati da sinistra verso destra, dall'alto verso il basso
    */
    public int computeColumnIndexForItem(int itemIndex) {
        int cellsInRow = computeMaxCellCountInRow();
        return itemIndex % cellsInRow;
    }

    /**
     * Compute the max number of cells in a row
     * 
     * @return The number of cells in a row
     */
    public int computeMaxCellCountInRow() {
        return Math.max((int) Math.floor(getWidth() / computeRequiredCellWidthWithSpacing()), 1);
    }

    /**
     * Compute the current row count
     * 
     * @return The number of required rows
     */
    public int computeCurrentRowCount() {
        return (int) Math.ceil((double) getSkinnable().getItems().size() / (double) computeMaxCellCountInRow());
    }
    
}
