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

package it.mbcraft.fileplaza.ui.main.search;

import it.mbcraft.fileplaza.data.models.Tag;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.ui.panels.files.list.DirectoryFileListPanel;
import it.mbcraft.fileplaza.ui.main.search.filters.FiltersPanel;
import it.mbcraft.fileplaza.ui.panels.tags.TagListPanel;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import java.io.File;
import javafx.beans.property.ListProperty;
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
    private DirectoryFileListPanel fileListPanel;
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
        fileListPanel = new DirectoryFileListPanel();
        fileListPanel.setCellListener(new SearchPanelFileListListener());
        
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
