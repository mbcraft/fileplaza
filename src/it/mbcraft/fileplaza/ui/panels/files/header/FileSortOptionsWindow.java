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

package it.mbcraft.fileplaza.ui.panels.files.header;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.state.order.FileSortMode;
import it.mbcraft.fileplaza.state.order.FileSortOption;
import it.mbcraft.fileplaza.ui.common.components.misc.PersistentButtonToggleGroup;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractSettingsWindow;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import java.security.InvalidParameterException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


/**
 * This class provides a window for choosing the current file sort mode. 
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileSortOptionsWindow extends AbstractSettingsWindow {

    private final ObjectProperty<FileSortMode> sortModeProperty;
    private final ObjectProperty<FileSortOption> sortOptionProperty;
    
    private ToggleGroup sortModeToggleGroup;
    
    private ToggleButton alphabeticalToggle;
    private ToggleButton dateToggle;
    
    private ToggleGroup sortDirectionToggleGroup;
    
    private ToggleButton ascendingToggle;
    private ToggleButton descendingToggle;
    
    private ToggleGroup sortOptionToggleGroup;
    
    private ToggleButton mixedToggle;
    private ToggleButton foldersThenFilesToggle;
    private ToggleButton filesThenFoldersToggle;
    
    /**
     * Builds a window to be used to change the FileSortMode and FileSortOption.
     * 
     * @param sortModeProp the FileSortMode, as an ObjectProperty.
     * @param sortOptionProp the FileSortOption, as an ObjectProperty.
     */
    public FileSortOptionsWindow(ObjectProperty<FileSortMode> sortModeProp,ObjectProperty<FileSortOption> sortOptionProp) {
        super(L("panels.files.header.FileSortOptionsWindow","Window_Title"), false);
        if (sortModeProp==null)
            throw new InvalidParameterException("FileSortMode property can't be null.");
        if (sortOptionProp==null)
            throw new InvalidParameterException("FileSortOption property can't be null.");
                
        sortModeProperty = sortModeProp;
        sortOptionProperty = sortOptionProp;      
        
    }

    @Override
    protected void initMiddleContent() {
        
        //initializing mode toggles
        sortModeToggleGroup = new PersistentButtonToggleGroup();
        alphabeticalToggle = new ToggleButton(L(this,"Toggle_ModeAlphabetical"));
        alphabeticalToggle.setToggleGroup(sortModeToggleGroup);
        alphabeticalToggle.setSelected(true);
        dateToggle = new ToggleButton(L(this,"Toggle_ModeDate"));
        dateToggle.setToggleGroup(sortModeToggleGroup);
        sortModeToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                fireDataChanged();
            }
        });
        
        //initializing direction toggles
        sortDirectionToggleGroup = new PersistentButtonToggleGroup();
        ascendingToggle = new ToggleButton(L(this,"Toggle_ModeAscending"));
        ascendingToggle.setToggleGroup(sortDirectionToggleGroup);
        ascendingToggle.setSelected(true);
        descendingToggle = new ToggleButton(L(this,"Toggle_ModeDescending"));
        descendingToggle.setToggleGroup(sortDirectionToggleGroup);
        sortDirectionToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                fireDataChanged();
            }
        });
        
        //initializing option toggles
        sortOptionToggleGroup = new PersistentButtonToggleGroup();
        mixedToggle = new ToggleButton(L(this,"Toggle_OptionMixed"));
        mixedToggle.setToggleGroup(sortOptionToggleGroup);
        foldersThenFilesToggle = new ToggleButton(L(this,"Toggle_OptionFoldersThenFiles"));
        foldersThenFilesToggle.setToggleGroup(sortOptionToggleGroup);
        foldersThenFilesToggle.setSelected(true);
        filesThenFoldersToggle = new ToggleButton(L(this,"Toggle_OptionFilesThenFolders"));
        filesThenFoldersToggle.setToggleGroup(sortOptionToggleGroup);
        sortOptionToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){

            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1) {
                fireDataChanged();
            }
        });
        //sort mode
        FlowPane modePane = new FlowPane();
        modePane.setHgap(5);
        modePane.getChildren().addAll(alphabeticalToggle,dateToggle);
               
        //sort direction
        FlowPane directionPane = new FlowPane();
        directionPane.setHgap(5);
        directionPane.getChildren().addAll(ascendingToggle,descendingToggle);
        
        //sort option
        FlowPane optionPane = new FlowPane();
        optionPane.setHgap(5);
        optionPane.getChildren().addAll(mixedToggle,foldersThenFilesToggle,filesThenFoldersToggle);
        
        GridPaneFiller.reset(2);
        GridPane pane = new GridPane();
        pane.setHgap(10);
        pane.setVgap(20);
        pane.add(new Label(L(this, "Label_SortMode")), GridPaneFiller.X(), GridPaneFiller.Y());
        pane.add(modePane,GridPaneFiller.X(),GridPaneFiller.Y());
        
        pane.add(new Label(L(this, "Label_SortDirection")), GridPaneFiller.X(), GridPaneFiller.Y());
        pane.add(directionPane,GridPaneFiller.X(),GridPaneFiller.Y());
        
        pane.add(new Label(L(this, "Label_SortOption")), GridPaneFiller.X(), GridPaneFiller.Y());
        pane.add(optionPane,GridPaneFiller.X(),GridPaneFiller.Y());
        
        addToWindow(pane);
    }

    @Override
    protected void loadData() {
        switch (sortModeProperty.get()) {
            case ALPHABETICAL_ASCENDING:alphabeticalToggle.setSelected(true);ascendingToggle.setSelected(true);break;
            case ALPHABETICAL_DESCENDING:alphabeticalToggle.setSelected(true);descendingToggle.setSelected(true);break;
            case DATE_ASCENDING:dateToggle.setSelected(true);ascendingToggle.setSelected(true);break;
            case DATE_DESCENDING:dateToggle.setSelected(true);descendingToggle.setSelected(true);break;
            default:throw new IllegalStateException("Unexpected FileSortMode value.");
        }
        
        switch (sortOptionProperty.get()) {
            case MIXED:mixedToggle.setSelected(true);break;
            case FOLDERS_THEN_FILES:foldersThenFilesToggle.setSelected(true);break;
            case FILES_THEN_FOLDERS:filesThenFoldersToggle.setSelected(true);break;
            default:throw new IllegalStateException("Unexpected FileSortOption value.");
        }
    }

    @Override
    protected void saveData() {
        FileSortMode mode = null;
        if (alphabeticalToggle.isSelected()) {
            if (sortDirectionToggleGroup.getSelectedToggle()==ascendingToggle)
                mode = FileSortMode.ALPHABETICAL_ASCENDING;
            else
                mode = FileSortMode.ALPHABETICAL_DESCENDING;
        }
        if (dateToggle.isSelected()) {
            if (sortOptionToggleGroup.getSelectedToggle()==ascendingToggle)
                mode = FileSortMode.DATE_ASCENDING;
            else
                mode = FileSortMode.DATE_DESCENDING;
        }
            
        FileSortOption option = null;
        if (mixedToggle.isSelected())
            option = FileSortOption.MIXED;
        if (foldersThenFilesToggle.isSelected())
            option = FileSortOption.FOLDERS_THEN_FILES;
        if (filesThenFoldersToggle.isSelected())
            option = FileSortOption.FILES_THEN_FOLDERS;
     
        System.out.println("Setting mode : "+mode+" option : "+option);
        
        sortModeProperty.set(mode);
        sortOptionProperty.set(option);
    }

    @Override
    protected boolean validateBeforeSave() {
        //no validation is needed
        return true;
    }
    
}
