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

import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.i18n.Lang;
import it.mbcraft.fileplaza.state.CurrentLicenseState;
import it.mbcraft.fileplaza.ui.main.MainWindow;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
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
        logger.config("License found : "+cls.getLicense().getLicenseName());
    }
    
    @Override
    public void start(Stage primaryStage) {       
        
        initializeLog();
        
        loadLicense();
        
        Lang.init(SettingsDAO.getInstance().load().getCurrentLanguage());
        
        WindowStack.push(primaryStage);

        MainWindow mainWindow = new MainWindow(primaryStage,SOFTWARE_NAME+VERSION);
        mainWindow.show();
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