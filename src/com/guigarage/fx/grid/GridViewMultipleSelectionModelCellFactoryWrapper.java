/*
 *  Developed by MBCRAFT. Copyright © 2014-2015. All rights reserved.
 *  This file of source code is property of MBCRAFT (http://www.mbcraft.it). 
 *  Do not sell, do not remove this license note even if you edit this file.
 *  Do not use this source code to develop your own file manager application.
 *  You can reuse part or full files for your own project (eg javafx ui classes)
 *  but keep copyright in files, and please link http://www.mbcraft.it on your
 *  project website.
 *
 *  Thanks
 *
 *  - Marco Bagnaresi
 */

package com.guigarage.fx.grid;

import it.mbcraft.fileplaza.utils.NodeUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.util.Callback;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class GridViewMultipleSelectionModelCellFactoryWrapper<T> implements Callback<GridView<T>, GridCell<T>> {
    
    private final Callback<GridView<T>, GridCell<T>> innerCellFactory;
    
    public GridViewMultipleSelectionModelCellFactoryWrapper(Callback<GridView<T>, GridCell<T>> factory) {
        innerCellFactory = factory; 
    }

    @Override
    public GridCell<T> call(final GridView<T> gv) {
        final GridCell<T> cell = innerCellFactory.call(gv);
        
        NodeUtils.setupNodeSelection(cell,"-fx-background-color:#ffffff","-fx-background-color:#abed77");
        
        cell.selectedProperty().addListener(new ChangeListener<Boolean>(){

            @Override
            public void changed(ObservableValue<? extends Boolean> observed, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    gv.selectionModelProperty().get().select(cell.getIndex());
                } else {
                    gv.selectionModelProperty().get().clearSelection(cell.getIndex());
                }
            }
        });
        return cell;
    }
}
