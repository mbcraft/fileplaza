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

import com.guigarage.fx.grid.GridView;
import it.mbcraft.fileplaza.ui.common.components.windows.ComponentTesterWindow;
import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.i18n.Lang;
import it.mbcraft.fileplaza.state.CurrentLicenseState;
import it.mbcraft.fileplaza.ui.main.MainWindow;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import it.mbcraft.fileplaza.ui.panels.files.IFileItemActionListener;
import it.mbcraft.fileplaza.ui.panels.files.NotHiddenFileFilter;
import it.mbcraft.fileplaza.ui.panels.files.icon.DirectoryFileIconPanel;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileIconCell;
import it.mbcraft.fileplaza.ui.panels.files.icon.FileIconCellFactory;
import it.mbcraft.fileplaza.ui.panels.files.list.DirectoryFileListPanel;
import it.mbcraft.fileplaza.ui.panels.files.list.FileListCell;
import it.mbcraft.fileplaza.utils.NodeUtils;
import java.io.File;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.IndexedCell;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Main extends Application {

    public static final String RELEASE_DATE = "04/11/2014";
    public static final String SOFTWARE_NAME = "FilePlaza ";
    public static final String VERSION = "0.5";

    private Logger logger;

    public static String getVersion() {
        return VERSION;
    }

    public static String getReleaseDate() {
        return RELEASE_DATE;
    }

    private void initializeLog() {
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("").addHandler(new ConsoleHandler());

        logger = Logger.getLogger("it.mbcraft.fileplaza.Main");
        logger.config("Logs initialized.");
    }

    private void loadLicense() {
        logger.config("Loading license ...");
        CurrentLicenseState cls = CurrentLicenseState.getInstance();
        logger.config("License found : " + cls.getLicense().getLicenseName());
    }

    private void runFilePlaza(Stage stage) {
        loadLicense();

        Lang.init(SettingsDAO.getInstance().load().getCurrentLanguage());

        WindowStack.push(stage);

        MainWindow mainWindow = new MainWindow(stage, SOFTWARE_NAME + VERSION);
        mainWindow.show();
    }

    private void runFileListTester(Stage stage) {
        WindowStack.push(stage);
        DirectoryFileListPanel panel = new DirectoryFileListPanel();
        panel.zoomIn();
        File home = new File("/home/marco");
        panel.itemsProperty().get().addAll(home.listFiles(new NotHiddenFileFilter()));

        ComponentTesterWindow tester = new ComponentTesterWindow(stage, panel.getNode());
        tester.showAndWait();
    }

    private void runListCellTester(Stage stage) {
        WindowStack.push(stage);
        FileListCell cell = new FileListCell(32);
        cell.setMinHeight(200);
        cell.setMinWidth(200);
        File f = new File("/home/marco");
        cell.updateItem(f, false);
        
        ComponentTesterWindow tester = new ComponentTesterWindow(stage, cell);
        tester.showAndWait();
    }

    private void runGridCellTester(Stage stage) {
        WindowStack.push(stage);

        
        FileIconCell cell = new FileIconCell(32);
        
        NodeUtils.setupNodeHover(cell, "-fx-background-color:#ffffff", "-fx-background-color:#ededff");
        NodeUtils.setupNodeSelection(cell,"-fx-background-color:#ffffff","-fx-background-color:#abed77");
        
        cell.setMaxWidth(300);
        cell.setMaxHeight(300);
        File f = new File("/home/marco/anincredibilylongdocumentnamecanbeverylongbutthefilemanagerhandlesit.txt");
        cell.updateItem(f, false);
        
        ComponentTesterWindow tester = new ComponentTesterWindow(stage, cell);
        tester.showAndWait();
        cell.printDebugInformations();
    }

    private void testGridView(Stage primaryStage) {
        primaryStage.setTitle("JGridFX Demo 1");

        
        
        final ObservableList<File> list = FXCollections.<File>observableArrayList();
        DirectoryFileIconPanel myGrid = new DirectoryFileIconPanel();
        //myGrid.setCellWidth(150);
//		myGrid.setStyle("-fx-border-color: black;");
        File f = new File("/home/marco");
                
        
        myGrid.setCellListener(new IFileItemActionListener() {
        
            @Override
            public void simpleSelection(File f,MouseEvent ev,IFileItemActionListener.SelectionPlace p) {
                System.out.println("Single selection over "+p.name());
                IndexedCell cell = (IndexedCell) ev.getSource();
                cell.updateSelected(!cell.isSelected());
            }

            @Override
            public void heavySelection(File f, MouseEvent ev,IFileItemActionListener.SelectionPlace p) {
                System.out.println("Multiple selection over "+p.name());
            }

            @Override
            public void contextMenu(File f, MouseEvent ev, IFileItemActionListener.SelectionPlace p) {
                System.out.println("Context menu opened on "+p.name());
            }
        
        });
        
        for (File z : f.listFiles(new NotHiddenFileFilter()))
            list.add(z);

        myGrid.itemsProperty().set(list);
        
        ScrollPane pane = new ScrollPane();

        pane.setPannable(false);
        pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        pane.setFitToWidth(true);
        pane.setContent(myGrid.getNode());       

        Scene scene = new Scene(pane, 600, 600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) {

        //initializeLog();
        testGridView(primaryStage);
        //runFileListTester(primaryStage);
        //runFileIconTester(primaryStage);
        //runListCellTester(primaryStage);
        //runGridCellTester(primaryStage);
        //runFilePlaza(primaryStage);
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
    }

}
