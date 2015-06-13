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

package it.mbcraft.fileplaza;

import it.mbcraft.fileplaza.ui.common.components.windows.ComponentTesterWindow;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import it.mbcraft.fileplaza.ui.panels.files.IElementActionListener;
import it.mbcraft.fileplaza.ui.panels.files.NotHiddenFileFilter;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileIconCell;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileViewIconPanel;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListCell;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListViewPanel;
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
        FileListViewPanel panel = new FileListViewPanel(new SimpleIntegerProperty(2), listener);
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
