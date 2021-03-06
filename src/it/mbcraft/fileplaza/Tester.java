/*
 *    FilePlaza - a tag based file manager
 *    Copyright (C) 2015 - Marco Bagnaresi
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published 
 *    by the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with this program.  If not, see 
 *    https://www.gnu.org/licenses/agpl-3.0.html.
 */

package it.mbcraft.fileplaza;

import it.mbcraft.fileplaza.ui.common.components.windows.ComponentTesterWindow;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import it.mbcraft.fileplaza.ui.panels.files.NotHiddenFileFilter;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileIconCell;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileViewIconPanel;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListCell;
import it.mbcraft.fileplaza.ui.panels.files.list.FileViewListPanel;
import it.mbcraft.fileplaza.utils.NodeUtils;
import java.io.File;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * This class contains test method used for testing components or other
 * behavior inside the JavaFX application. This is not unit testing, it's
 * live running inside the JavaFX environment with all the libraries loaded as
 * dependencies.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Tester {
        
    public static void runFileListTester(Stage stage) {
        stage.setTitle("FileList Tester");
    
        WindowStack.push(stage);
        IElementActionListener listener = new IElementActionListener() {
        
            @Override
            public void simpleSelection(File f,MouseEvent ev,IElementActionListener.SelectionPlace p) {
                System.out.println("Single selection over "+p.name());
                IndexedCell cell = (IndexedCell) ev.getSource();
                cell.updateSelected(!cell.isSelected());
            }

            @Override
            public void heavySelection(File f, MouseEvent ev,IElementActionListener.SelectionPlace p) {
                System.out.println("Multiple selection over "+p.name());
            }

            @Override
            public void contextMenu(File f, MouseEvent ev, IElementActionListener.SelectionPlace p) {
                System.out.println("Context menu opened on "+p.name());
            }
        
        };
        FileViewListPanel panel = new FileViewListPanel(new SimpleIntegerProperty(2), listener);
        //panel.zoomIn();
        File home = new File("/home/marco");
        panel.itemsProperty().get().addAll(home.listFiles(new NotHiddenFileFilter()));

        ComponentTesterWindow tester = new ComponentTesterWindow(stage, panel.getNode());
        tester.showAndWait();
    }
    
    
    public static void runListCellTester(Stage stage) {
        stage.setTitle("ListCell Tester");
    
        WindowStack.push(stage);
        FileListCell cell = new FileListCell(new SimpleIntegerProperty(2));
        cell.setMinHeight(200);
        cell.setMinWidth(200);
        File f = new File("/home/marco/NetBeansProjects");
        cell.updateItem(f, false);
        
        ComponentTesterWindow tester = new ComponentTesterWindow(stage, cell);
        tester.showAndWait();
    }
    
    
    public static void runGridCellTester(Stage stage) {
        stage.setTitle("GridCell Tester");
    
        WindowStack.push(stage);

        FileIconCell cell = new FileIconCell(new SimpleIntegerProperty(2));
        
        NodeUtils.setupNodeHover(cell, "-fx-background-color:#ffffff", "-fx-background-color:#ededff");
        NodeUtils.setupNodeSelection(cell,"-fx-background-color:#ffffff","-fx-background-color:#abed77");
        
        cell.setMaxWidth(300);
        cell.setMaxHeight(300);
        File f = new File("/home/marco/anincredibilylongdocumentnamecanbeverylongbutthefilemanagerhandlesit.txt");
        cell.updateItem(f, false);
        
        ComponentTesterWindow tester = new ComponentTesterWindow(stage, cell);
        tester.showAndWait();

    }
    
    
    public static void runGridViewTester(Stage stage) {
        stage.setTitle("GridView Tester");

        final ObservableList<File> list = FXCollections.<File>observableArrayList();
        IElementActionListener listener = new IElementActionListener() {
        
            @Override
            public void simpleSelection(File f,MouseEvent ev,IElementActionListener.SelectionPlace p) {
                System.out.println("Single selection over "+p.name());
                IndexedCell cell = (IndexedCell) ev.getSource();
                cell.updateSelected(!cell.isSelected());
            }

            @Override
            public void heavySelection(File f, MouseEvent ev,IElementActionListener.SelectionPlace p) {
                System.out.println("Multiple selection over "+p.name());
            }

            @Override
            public void contextMenu(File f, MouseEvent ev, IElementActionListener.SelectionPlace p) {
                System.out.println("Context menu opened on "+p.name());
            }
        
        };
        
        FileViewIconPanel myGrid = new FileViewIconPanel(new SimpleIntegerProperty(2), listener);

        //myGrid.setStyle("-fx-border-color: black;");
        File f = new File("/home/marco/NetBeansProjects");
                        
        for (File z : f.listFiles(new NotHiddenFileFilter()))
            list.add(z);

        myGrid.itemsProperty().set(list);
        
        ScrollPane pane = new ScrollPane();

        pane.setPannable(false);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setFitToWidth(true);
        pane.setContent(myGrid.getNode()); 

        Scene scene = new Scene(pane, 1200, 600);
        
        stage.setScene(scene);
        stage.show();
    }
    
}
