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

package it.mbcraft.fileplaza.ui.main.sort;

import it.mbcraft.fileplaza.algorithm.sort.FileElementSort;
import it.mbcraft.fileplaza.algorithm.sort.SortScore;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.CommonActionNodeContainer;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.sort.SortDetailsPanel")
class SortDetailsPanel implements INodeProvider {

    private final VBox box = new VBox();
    
    private final ObservableList<File> fileList;
    private final ObjectProperty<MultipleSelectionModel<File>> selection;
    private final ObjectProperty<FileElementSort> sort;
    
    private final Label previousPathLabel = new Label();
    private final Label previousPath = new Label();
    private final Label newPathLabel = new Label();
    private final Label newPath = new Label();
    
    private final CommonActionNodeContainer labelsAndButtons = new CommonActionNodeContainer();
    
    private Button removeButton;
    private Button sortButton;
    
    SortDetailsPanel(ObservableList<File> fileListProperty,ObjectProperty<MultipleSelectionModel<File>> selectionProperty,ObjectProperty<FileElementSort> currentSortProperty) {
        fileList = fileListProperty;
        selection = selectionProperty;
        sort = currentSortProperty;

        initContainer();
        initContent();
        initButtons();
        initListeners();
    }

    private void initContainer() {
        box.setPadding(new Insets(5));
    }
    
    private void initContent() {
        GridPane pane = new GridPane();
        pane.setPadding(new Insets(10));
        GridPaneFiller.reset(2);
        
        previousPathLabel.setText(L(this,"PreviousPath_Label"));
        previousPathLabel.setDisable(true);
        pane.add(previousPathLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        pane.add(previousPath, GridPaneFiller.X(), GridPaneFiller.Y());
        
        newPathLabel.setText(L(this,"NewPath_Label"));
        newPathLabel.setDisable(true);
        pane.add(newPathLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        pane.add(newPath, GridPaneFiller.X(), GridPaneFiller.Y());
        
        labelsAndButtons.addAll(previousPathLabel,previousPath,newPathLabel,newPath);
        
        box.getChildren().add(pane);
        
    }
    
    private void initButtons() {
        FlowPane buttons = new FlowPane();
        buttons.setPadding(new Insets(10));
        buttons.setHgap(10);
        buttons.setAlignment(Pos.CENTER);
        
        removeButton = new Button(L(this,"Remove_Button"));
        removeButton.setDisable(true);
        removeButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                File selected = selection.get().getSelectedItem();
                sort.get().remove(selected);
                fileList.remove(selected);
                if (fileList.isEmpty())
                    sort.set(null);
            }
        });
        
        buttons.getChildren().add(removeButton);
        
        sortButton = new Button(L(this,"Sort_Button"));
        sortButton.setDisable(true);
        sortButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                File selected = selection.get().getSelectedItem();
                sort.get().sort(selected);
                fileList.remove(selected);
                if (fileList.isEmpty())
                    sort.set(null);
            }
        });
        
        buttons.getChildren().add(sortButton);
        
        labelsAndButtons.addAll(removeButton,sortButton);
        
        box.getChildren().add(buttons);
    }

    private void initListeners() {
        selection.get().selectedItemProperty().addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldSelection, File newSelection) {
                if (newSelection!=null)
                    updateDetails(sort.get().getSortScore(newSelection));
                else
                    updateDetails(null);
            }
        });
        
    }
    
    private void updateDetails(SortScore sortScore) {
        if (sortScore!=null) {
            labelsAndButtons.setDisable(false);
            previousPath.setText(sortScore.getElement().getCurrentPath());
            newPath.setText(sortScore.getTargetFolder().getAbsolutePath());
        } else {
            labelsAndButtons.setDisable(true);
            previousPath.setText(null);
            newPath.setText(null);
        }
    }
        
    @Override
    public Node getNode() {
        return box;
    }
}
