package com.guigarage.fx.grid;

import javafx.scene.control.IndexedCell;

import com.guigarage.fx.grid.skin.GridCellSkin;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class GridCell<T> extends IndexedCell<T> {

    private final DoubleProperty requiredCellHeightProperty = new SimpleDoubleProperty();
    private final DoubleProperty requiredCellWidthProperty = new SimpleDoubleProperty();
    
    public GridCell() {
        getStyleClass().add("grid-cell");
    }

    public void setCssDependency() {
        setSkinClassName(GridCellSkin.class.getName());
    }

    @Override
    protected String getUserAgentStylesheet() {
        return GridView.class.getResource("gridview.css").toExternalForm();
    }
    
    public DoubleProperty requiredCellHeightProperty() {
        return requiredCellHeightProperty;
    }
    
    public DoubleProperty requiredCellWidthProperty() {
        return requiredCellWidthProperty;
    }
}
