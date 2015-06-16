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
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;


/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class FileSortOptionsWindow extends AbstractSettingsWindow {

    private final ObjectProperty<FileSortMode> sortModeProperty;
    private final ObjectProperty<FileSortOption> sortOptionProperty;
    
    private final ToggleGroup sortMode = new PersistentButtonToggleGroup();
    
    private ToggleButton alphabeticalToggle;
    private ToggleButton dateToggle;
    
    private final ToggleGroup sortDirection = new PersistentButtonToggleGroup();
    
    private ToggleButton ascendingToggle;
    private ToggleButton descendingToggle;
    
    private final ToggleGroup sortOption = new PersistentButtonToggleGroup();
    
    private ToggleButton mixedToggle;
    private ToggleButton foldersThenFilesToggle;
    private ToggleButton filesThenFoldersToggle;
    
    
    
    
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
        
        //sort mode
        FlowPane modePane = new FlowPane();
        alphabeticalToggle = new ToggleButton(L(this,"Toggle_ModeAlphabetical"));
        alphabeticalToggle.setToggleGroup(sortMode);
        dateToggle = new ToggleButton(L(this,"Toggle_ModeDate"));
        dateToggle.setToggleGroup(sortMode);
        modePane.getChildren().addAll(alphabeticalToggle,dateToggle);
               
        //sort direction
        FlowPane directionPane = new FlowPane();
        ascendingToggle = new ToggleButton(L(this,"Toggle_ModeAscending"));
        ascendingToggle.setToggleGroup(sortDirection);
        descendingToggle = new ToggleButton(L(this,"Toggle_ModeDescending"));
        descendingToggle.setToggleGroup(sortDirection);
        directionPane.getChildren().addAll(ascendingToggle,descendingToggle);
        
        //sort option
        FlowPane optionPane = new FlowPane();
        mixedToggle = new ToggleButton(L(this,"Toggle_OptionMixed"));
        mixedToggle.setToggleGroup(sortOption);
        foldersThenFilesToggle = new ToggleButton(L(this,"Toggle_OptionFoldersThenFiles"));
        foldersThenFilesToggle.setToggleGroup(sortOption);
        filesThenFoldersToggle = new ToggleButton(L(this,"Toggle_OptionFilesThenFolders"));
        filesThenFoldersToggle.setToggleGroup(sortOption);
        optionPane.getChildren().addAll(mixedToggle,foldersThenFilesToggle,filesThenFoldersToggle);
        
        GridPaneFiller.reset(2);
        GridPane pane = new GridPane();
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
            case ALPHABETICAL_ASCENDING:sortMode.selectToggle(alphabeticalToggle);sortDirection.selectToggle(ascendingToggle);break;
            case ALPHABETICAL_DESCENDING:sortMode.selectToggle(alphabeticalToggle);sortDirection.selectToggle(descendingToggle);break;
            case DATE_ASCENDING:sortMode.selectToggle(dateToggle);sortDirection.selectToggle(ascendingToggle);break;
            case DATE_DESCENDING:sortMode.selectToggle(dateToggle);sortDirection.selectToggle(descendingToggle);break;
            default:throw new IllegalStateException("Unexpected FileSortMode value.");
        }
        
        switch (sortOptionProperty.get()) {
            case MIXED:sortOption.selectToggle(mixedToggle);break;
            case FOLDERS_THEN_FILES:sortOption.selectToggle(foldersThenFilesToggle);break;
            case FILES_THEN_FOLDERS:sortOption.selectToggle(filesThenFoldersToggle);break;
            default:throw new IllegalStateException("Unexpected FileSortOption value.");
        }
    }

    @Override
    protected void saveData() {
        FileSortMode mode = null;
        if (sortMode.getSelectedToggle()==alphabeticalToggle) {
            if (sortDirection.getSelectedToggle()==ascendingToggle)
                mode = FileSortMode.ALPHABETICAL_ASCENDING;
            else
                mode = FileSortMode.ALPHABETICAL_DESCENDING;
        }
        if (sortMode.getSelectedToggle()==dateToggle) {
            if (sortOption.getSelectedToggle()==ascendingToggle)
                mode = FileSortMode.DATE_ASCENDING;
            else
                mode = FileSortMode.DATE_DESCENDING;
        }
            
        FileSortOption option = null;
        if (sortOption.getSelectedToggle()==mixedToggle)
            option = FileSortOption.MIXED;
        if (sortOption.getSelectedToggle()==foldersThenFilesToggle)
            option = FileSortOption.FOLDERS_THEN_FILES;
        if (sortOption.getSelectedToggle()==filesThenFoldersToggle)
            option = FileSortOption.FILES_THEN_FOLDERS;
     
        sortModeProperty.set(mode);
        sortOptionProperty.set(option);
        
    }

    @Override
    protected boolean validateBeforeSave() {
        //no validation is needed
        return true;
    }
    
}
