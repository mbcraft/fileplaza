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

package it.mbcraft.fileplaza.ui.main;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.main.browse.BrowsePanel;
import it.mbcraft.fileplaza.ui.main.search.SearchPanel;
import it.mbcraft.fileplaza.ui.main.menu.MainMenuBar;
import it.mbcraft.fileplaza.ui.main.menu.file.actions.FileQuitAction;
import it.mbcraft.fileplaza.ui.main.sort.SortPanel;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This class handles the main application window.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.MainWindow")
public class MainWindow {

    private final Stage primaryStage;
    private BorderPane mainLayout;
    
    private MainMenuBar menuBarProvider;
    
    public MainWindow(Stage primaryStage, String version) {
        
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

            @Override
            public void handle(WindowEvent t) {
                FileQuitAction.getInstance().handle(null);
            }
        });
        
        this.primaryStage = primaryStage;

        initWindow(version);

        setupMenu();
        setupPanels();
    }

    private void initWindow(String title) {
        primaryStage.setTitle(title);
        primaryStage.getIcons().add(IconFactory.getAppIconAsImage(32));
        mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 700, 400);
        primaryStage.setScene(scene);
    }

    private void setupMenu() {
        menuBarProvider = new MainMenuBar();
        mainLayout.setTop(menuBarProvider.getMenuBar());
    }
    
    private void setupPanels() {
        TabPane pane = new TabPane();
        
        //browse
        BrowsePanel browsePanel = new BrowsePanel();     
        Tab t1 = new Tab(L(this,"Browse_Tab"));
        t1.setClosable(false);
        t1.setContent(browsePanel.getNode());
        pane.getTabs().add(t1);
 
        //search
        SearchPanel searchPanel = new SearchPanel();
        Tab t2 = new Tab(L(this,"Search_Tab"));
        t2.setClosable(false);
        t2.setContent(searchPanel.getNode());
        pane.getTabs().add(t2);
        
        //sort
        SortPanel sortPanel = new SortPanel();
        Tab t3 = new Tab(L(this,"Sort_Tab"));
        t3.setClosable(false);
        t3.setContent(sortPanel.getNode());
        pane.getTabs().add(t3);
        
        mainLayout.setCenter(pane);
    }

    public void show() {
        primaryStage.show();
    }
}