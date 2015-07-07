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
import javafx.scene.control.MultipleSelectionModel;

public class GridView<T> extends Control {

    // Items property
    private final ObjectProperty<ObservableList<T>> itemsProperty;

    // Cell factory property
    private final ObjectProperty<Callback<GridView<T>, GridCell<T>>> cellFactoryProperty;

    // Selection model property
    private final ObjectProperty<MultipleSelectionModel<T>> selectionModelProperty;

    // Horizontal cell spacing property
    private final DoubleProperty horizontalCellSpacingProperty = new SimpleStyleableDoubleProperty(this,
            "horizontalCellSpacing",
            StyleableProperties.HORIZONTAL_CELL_SPACING);

    // Vertical cell spacing property
    private final DoubleProperty verticalCellSpacingProperty = new SimpleStyleableDoubleProperty(this,
            "verticalCellSpacing",
            StyleableProperties.VERTICAL_CELL_SPACING);

    // Horizontal alignment property, usually "Center"
    private ObjectProperty<HPos> horizontalAlignmentProperty = null;

    public GridView() {
        this(FXCollections.<T>emptyObservableList());
    }

    public GridView(ObservableList<T> items) {

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

        getStyleClass().add("grid-view");

        itemsProperty = new SimpleObjectProperty(items);
        cellFactoryProperty = new SimpleObjectProperty();
        selectionModelProperty = new SimpleObjectProperty(new GridViewMultipleSelectionModel(itemsProperty));

    }

    public void setHorizontalCellSpacing(double value) {
        horizontalCellSpacingProperty().set(value);
    }

    public double getHorizontalCellSpacing() {
        return horizontalCellSpacingProperty.get();
    }

    public final DoubleProperty horizontalCellSpacingProperty() {
        return horizontalCellSpacingProperty;
    }

    public void setVerticalCellSpacing(double value) {
        verticalCellSpacingProperty().set(value);
    }

    public double getVerticalCellSpacing() {
        return verticalCellSpacingProperty.get();
    }

    public final DoubleProperty verticalCellSpacingProperty() {
        return verticalCellSpacingProperty;
    }

    public final ObjectProperty<HPos> horizontalAlignmentProperty() {
        return horizontalAlignmentProperty;
    }

    public final void setHorizontalAlignment(HPos value) {
        horizontalAlignmentProperty().set(value);
    }

    public final HPos getHorizontalAlignment() {
        return horizontalAlignmentProperty.get();
    }

    public final ObjectProperty<Callback<GridView<T>, GridCell<T>>> cellFactoryProperty() {
        return cellFactoryProperty;
    }

    public final void setCellFactory(Callback<GridView<T>, GridCell<T>> value) {
        cellFactoryProperty().set(new GridViewMultipleSelectionModelCellFactoryWrapper<>(value));
    }

    public final Callback<GridView<T>, GridCell<T>> getCellFactory() {
        return cellFactoryProperty.get();
    }

    public final void setItems(ObservableList<T> value) {
        itemsProperty().set(value);
    }

    public final ObservableList<T> getItems() {
        return itemsProperty.get();
    }

    public final ObjectProperty<ObservableList<T>> itemsProperty() {
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
                        return !n.horizontalAlignmentProperty.isBound();
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

        private static final StyleableProperty<GridView, Number> HORIZONTAL_CELL_SPACING = new StyleableProperty<GridView, Number>(
                "-fx-horizontal-cell-spacing", SizeConverter.getInstance(),
                12.0) {

                    @Override
                    public boolean isSettable(GridView n) {
                        return !n.horizontalCellSpacingProperty.isBound();
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
                        return !n.verticalCellSpacingProperty.isBound();
                    }

                    @Override
                    public WritableValue<Number> getWritableValue(GridView n) {
                        return n.verticalCellSpacingProperty();
                    }
                };

        private static final List<StyleableProperty> STYLEABLES;

        static {
            final List<StyleableProperty> styleables = new ArrayList<>(
                    Control.impl_CSS_STYLEABLES());
            Collections.addAll(styleables, HORIZONTAL_ALIGNMENT,
                    HORIZONTAL_CELL_SPACING, VERTICAL_CELL_SPACING);
            STYLEABLES = Collections.unmodifiableList(styleables);
        }
    }

    @SuppressWarnings("rawtypes")
    public static List<StyleableProperty> impl_CSS_STYLEABLES() {
        return GridView.StyleableProperties.STYLEABLES;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public List<StyleableProperty> impl_getStyleableProperties() {
        return impl_CSS_STYLEABLES();
    }
}
