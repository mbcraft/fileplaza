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

package it.mbcraft.fileplaza.ui.main.search;

import it.mbcraft.fileplaza.data.models.Tag;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListViewPanel;
import it.mbcraft.fileplaza.ui.main.search.filters.FiltersPanel;
import it.mbcraft.fileplaza.ui.panels.tags.TagListPanel;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ZoomHelper;
import java.io.File;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.SetChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class SearchPanel implements INodeProvider {
    private final VBox box = new VBox();
    private final SearchState state = new SearchState();
    private FileListViewPanel fileListPanel;
    private TagListPanel tagListPanel;
    private final ListProperty<Tag> tagList = new SimpleListProperty();

    public SearchPanel() {
        
        initContainer();
        initPanels();
        initEvents();
    }
    
    private void initContainer() {
        box.setPadding(new Insets(5));
    }
    
    @Override
    public Node getNode() {
        return box;
    }

    private void initPanels() {
        FiltersPanel filtersPanel = new FiltersPanel(state);
        //questo pannello deve consentire la visualizzazione di file in diverse cartelle
        fileListPanel = new FileListViewPanel(new SimpleIntegerProperty(ZoomHelper.getMinLevelIndex()), new SearchPanelFileListListener());
        
        tagListPanel = new TagListPanel(L(this,"TagList_Label"), true, tagList);
        
        box.getChildren().add(filtersPanel.getNode());
        box.getChildren().add(fileListPanel.getNode());
        box.getChildren().add(tagListPanel.getNode());
    }
    
    private void initEvents() {
        state.elementsFoundProperty().addListener(new SetChangeListener<File>(){

            @Override
            public void onChanged(SetChangeListener.Change<? extends File> change) {
                fileListPanel.itemsProperty().get().setAll(change.getSet());
            }
        });
    }
}
