package com.guigarage.fx.grid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.WritableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.control.Control;
import javafx.util.Callback;

import com.guigarage.fx.grid.util.SimpleStyleableDoubleProperty;
import com.sun.javafx.css.StyleableObjectProperty;
import com.sun.javafx.css.StyleableProperty;
import com.sun.javafx.css.converters.EnumConverter;
import com.sun.javafx.css.converters.SizeConverter;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.MultipleSelectionModel;

public class GridView<T> extends Control {

    // Inhalt des Grid
    private ObjectProperty<ObservableList<T>> itemsProperty;

    // Factory für die Zellen
    private ObjectProperty<Callback<GridView<T>, GridCell<T>>> cellFactoryProperty;

    private DoubleProperty cellWidthProperty;

    private DoubleProperty cellHeightProperty;

    private DoubleProperty horizontalCellSpacingProperty;

    private DoubleProperty verticalCellSpacingProperty;

    private ObjectProperty<HPos> horizontalAlignmentProperty;

    private ObjectProperty<MultipleSelectionModel<T>> selectionModelProperty;

    public GridView() {
        this(FXCollections.<T>observableArrayList());
    }

    public GridView(ObservableList<T> items) {
        getStyleClass().add("grid-view");
        setItems(items);
        selectionModelProperty = new SimpleObjectProperty(new GridViewMultipleSelectionModel(itemsProperty));
    }

    public void setHorizontalCellSpacing(double value) {
        horizontalCellSpacingProperty().set(value);
    }

    public double getHorizontalCellSpacing() {
        return horizontalCellSpacingProperty == null ? 12.0 : horizontalCellSpacingProperty
                .get();
    }

    public final DoubleProperty horizontalCellSpacingProperty() {
        if (horizontalCellSpacingProperty == null) {
            horizontalCellSpacingProperty = new SimpleStyleableDoubleProperty(this,
                    "horizontalCellSpacing",
                    StyleableProperties.HORIZONTAL_CELL_SPACING);
        }
        return horizontalCellSpacingProperty;
    }

    public void setVerticalCellSpacing(double value) {
        verticalCellSpacingProperty().set(value);
    }

    public double getVerticalCellSpacing() {
        return verticalCellSpacingProperty == null ? 12.0 : verticalCellSpacingProperty.get();
    }

    public final DoubleProperty verticalCellSpacingProperty() {
        if (verticalCellSpacingProperty == null) {
            verticalCellSpacingProperty = new SimpleStyleableDoubleProperty(this,
                    "verticalCellSpacing",
                    StyleableProperties.VERTICAL_CELL_SPACING);
        }
        return verticalCellSpacingProperty;
    }

    public final DoubleProperty cellWidthProperty() {
        if (cellWidthProperty == null) {
            cellWidthProperty = new SimpleStyleableDoubleProperty(this, "cellWidth",
                    StyleableProperties.CELL_WIDTH);
        }
        return cellWidthProperty;
    }

    public void setCellWidth(double value) {
        cellWidthProperty().set(value);
    }

    public double getCellWidth() {
        return cellWidthProperty == null ? 64.0 : cellWidthProperty.get();
    }

    public final DoubleProperty cellHeightProperty() {
        if (cellHeightProperty == null) {
            cellHeightProperty = new SimpleStyleableDoubleProperty(this, "cellHeight",
                    StyleableProperties.CELL_HEIGHT);
        }
        return cellHeightProperty;
    }

    public void setCellHeight(double value) {
        cellHeightProperty().set(value);
    }

    public double getCellHeight() {
        return cellHeightProperty == null ? 64.0 : cellHeightProperty.get();
    }

    public final ObjectProperty<HPos> horizontalAlignmentProperty() {
        if (horizontalAlignmentProperty == null) {
            horizontalAlignmentProperty = new StyleableObjectProperty<HPos>(HPos.CENTER) {

                @SuppressWarnings("rawtypes")
                @Override
                public StyleableProperty getStyleableProperty() {
                    return StyleableProperties.HORIZONTAL_ALIGNMENT;
                }

                @Override
                public Object getBean() {
                    return GridView.this;
                }

                @Override
                public String getName() {
                    return "horizontalAlignment";
                }
            };
        }
        return horizontalAlignmentProperty;
    }

    public final void setHorizontalAlignment(HPos value) {
        horizontalAlignmentProperty().set(value);
    }

    public final HPos getHorizontalAlignment() {
        return horizontalAlignmentProperty == null ? HPos.CENTER : horizontalAlignmentProperty.get();
    }

    public final ObjectProperty<Callback<GridView<T>, GridCell<T>>> cellFactoryProperty() {
        if (cellFactoryProperty == null)
            cellFactoryProperty = new SimpleObjectProperty(this,"cellFactory");
        return cellFactoryProperty;
    }

    public final void setCellFactory(Callback<GridView<T>, GridCell<T>> value) {
        cellFactoryProperty().set(new GridViewMultipleSelectionModelCellFactoryWrapper<>(value));
    }

    public final Callback<GridView<T>, GridCell<T>> getCellFactory() {
        return cellFactoryProperty == null ? null : cellFactoryProperty.get();
    }

    public final void setItems(ObservableList<T> value) {
        itemsProperty().set(value);
    }

    public final ObservableList<T> getItems() {
        return itemsProperty == null ? null : itemsProperty.get();
    }

    public final ObjectProperty<ObservableList<T>> itemsProperty() {
        if (itemsProperty == null) {
            itemsProperty = new SimpleObjectProperty<ObservableList<T>>(this, "items");
        }
        return itemsProperty;
    }

    /**
     * This method is needed in order to get the selection.
     *
     * @return The selection model
     */
    public MultipleSelectionModel<T> getSelectionModel() {
        return selectionModelProperty.get();
    }

    /**
     * Return the selection model property.
     *
     * @return The property
     */
    public ObjectProperty<MultipleSelectionModel<T>> selectionModelProperty() {
        return selectionModelProperty;
    }

    @Override
    protected String getUserAgentStylesheet() {
        return GridView.class.getResource("gridview.css").toExternalForm();
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    private static class StyleableProperties {

        private static final StyleableProperty<GridView, HPos> HORIZONTAL_ALIGNMENT = new StyleableProperty<GridView, HPos>(
                "-fx-horizontal-alignment", new EnumConverter<HPos>(HPos.class),
                HPos.CENTER) {

                    @Override
                    public boolean isSettable(GridView n) {
                        return n.horizontalAlignmentProperty == null || !n.horizontalAlignmentProperty.isBound();
                    }

                    @SuppressWarnings("unchecked")
                    @Override
                    public WritableValue<HPos> getWritableValue(GridView n) {
                        return n.horizontalAlignmentProperty();
                    }

                    @Override
                    public HPos getInitialValue(GridView n) {
                        return HPos.CENTER;
                    }
                };

        private static final StyleableProperty<GridView, Number> CELL_WIDTH = new StyleableProperty<GridView, Number>(
                "-fx-cell-width", SizeConverter.getInstance(), 64.0) {

                    @Override
                    public boolean isSettable(GridView n) {
                        return n.cellWidthProperty == null || !n.cellWidthProperty.isBound();
                    }

                    @Override
                    public WritableValue<Number> getWritableValue(GridView n) {
                        return n.cellWidthProperty();
                    }
                };

        private static final StyleableProperty<GridView, Number> CELL_HEIGHT = new StyleableProperty<GridView, Number>(
                "-fx-cell-height", SizeConverter.getInstance(), 64.0) {

                    @Override
                    public boolean isSettable(GridView n) {
                        return n.cellHeightProperty == null || !n.cellHeightProperty.isBound();
                    }

                    @Override
                    public WritableValue<Number> getWritableValue(GridView n) {
                        return n.cellHeightProperty();
                    }
                };

        private static final StyleableProperty<GridView, Number> HORIZONTAL_CELL_SPACING = new StyleableProperty<GridView, Number>(
                "-fx-horizontal-cell-spacing", SizeConverter.getInstance(),
                12.0) {

                    @Override
                    public boolean isSettable(GridView n) {
                        return n.horizontalCellSpacingProperty == null
                        || !n.horizontalCellSpacingProperty.isBound();
                    }

                    @Override
                    public WritableValue<Number> getWritableValue(GridView n) {
                        return n.horizontalCellSpacingProperty();
                    }
                };

        private static final StyleableProperty<GridView, Number> VERTICAL_CELL_SPACING = new StyleableProperty<GridView, Number>(
                "-fx-vertical-cell-spacing", SizeConverter.getInstance(), 12.0) {

                    @Override
                    public boolean isSettable(GridView n) {
                        return n.verticalCellSpacingProperty == null
                        || !n.verticalCellSpacingProperty.isBound();
                    }

                    @Override
                    public WritableValue<Number> getWritableValue(GridView n) {
                        return n.verticalCellSpacingProperty();
                    }
                };

        private static final List<StyleableProperty> STYLEABLES;

        static {
            final List<StyleableProperty> styleables = new ArrayList<StyleableProperty>(
                    Control.impl_CSS_STYLEABLES());
            Collections.addAll(styleables, HORIZONTAL_ALIGNMENT, CELL_HEIGHT, CELL_WIDTH,
                    HORIZONTAL_CELL_SPACING, VERTICAL_CELL_SPACING);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @SuppressWarnings("rawtypes")
    public static List<StyleableProperty> impl_CSS_STYLEABLES() {
        return GridView.StyleableProperties.STYLEABLES;
    }

    @SuppressWarnings("rawtypes")
    public List<StyleableProperty> impl_getStyleableProperties() {
        return impl_CSS_STYLEABLES();
    }
}
