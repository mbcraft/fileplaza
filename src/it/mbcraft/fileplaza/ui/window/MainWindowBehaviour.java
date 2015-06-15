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

package it.mbcraft.fileplaza.ui.window;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.main.browse.BrowsePanel;
import it.mbcraft.fileplaza.ui.main.search.SearchPanel;
import it.mbcraft.fileplaza.ui.main.menu.MainMenuBar;
import it.mbcraft.fileplaza.ui.main.sort.SortPanel;
import java.awt.GraphicsEnvironment;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * This class handles the main application window.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.MainWindow")
public class MainWindowBehaviour {

    private final Stage primaryStage;
    private BorderPane mainLayout;
    
    private WindowTitleBar windowTitleBarProvider;
    private MainMenuBar mainMenuBarProvider;
    private StatusBar statusBarProvider;
    
    public MainWindowBehaviour(Stage primaryStage, String version) {
        
        Platform.setImplicitExit(false);
        
        this.primaryStage = primaryStage;

        initWindow();
        setupTitleBar(version);
        setupMenu();
        setupPanels();
        setupStatusBar();
    }

    private void initWindow() {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setResizable(false);
        
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Rectangle rect = env.getMaximumWindowBounds();
        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setWidth(rect.width);
        primaryStage.setHeight(rect.height);
        
        mainLayout = new BorderPane();
        Scene scene = new Scene(mainLayout, 700, 400);
        primaryStage.setScene(scene);
    }
    
    private void setupTitleBar(String title) {
        windowTitleBarProvider = new WindowTitleBar(title, primaryStage);
    }

    private void setupMenu() {
        mainMenuBarProvider = new MainMenuBar();
        
        VBox topBars = new VBox();
        topBars.getChildren().addAll(windowTitleBarProvider.getNode(),mainMenuBarProvider.getMenuBar());
        
        mainLayout.setTop(topBars);
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
    
    private void setupStatusBar() {
        statusBarProvider = new StatusBar();
        
        mainLayout.setBottom(statusBarProvider.getNode());
    }

    public void show() {
        primaryStage.show();
    }
}