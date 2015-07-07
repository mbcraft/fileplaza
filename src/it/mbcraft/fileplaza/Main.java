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

import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.i18n.Lang;
import it.mbcraft.fileplaza.state.CurrentLicenseState;
import it.mbcraft.fileplaza.ui.window.FilePlazaMainWindow;
import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;


/**
 * This is the main entry point for the JavaFX application.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class Main extends Application {

    public static final String RELEASE_DATE = "10/06/2015";
    public static final String SOFTWARE_NAME = "FilePlaza ";
    public static final String VERSION = "0.6";

    private static final Logger logger = Logger.getLogger("it.mbcraft.fileplaza.Main");

    /**
     * Initializes the logger if needed. Change logging behaviour from here.
     */
    public Main() {
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("").addHandler(new ConsoleHandler());

        logger.config("Logs initialized.");
    }
    
    /**
     * Returns the current version of FilePlaza.
     * 
     * @return The complete version number of FilePlaza
     */
    public static String getVersion() {
        return VERSION;
    }

    /**
     * Returns the release date of this application (print format).
     * Used only for informative purposes as text.
     * 
     * @return The release date as text for informative purposes only.
     */
    public static String getReleaseDate() {
        return RELEASE_DATE;
    }

    /**
     * Loads the license information of this application.
     */
    private static void loadLicense() {
        logger.config("Loading license ...");
        CurrentLicenseState cls = CurrentLicenseState.getInstance();
        logger.config("License found : " + cls.getLicense().getLicenseName());
    }

    /**
     * Runs FilePlaza. 
     * 
     * @param stage The JavaFX stage
     */
    private static void runFilePlaza(Stage stage) {
        loadLicense();

        Lang.init(SettingsDAO.getInstance().load().getCurrentLanguage());

        WindowStack.push(stage);

        FilePlazaMainWindow mainWindow = new FilePlazaMainWindow(stage, SOFTWARE_NAME + VERSION);
        mainWindow.show();
    }
        
    /**
     * Start method of JavaFX application. Can be used to change behaviour and run
     * Testers if needed.
     * 
     * @param primaryStage The JavaFX stage
     */
    @Override
    public void start(Stage primaryStage) {
        //Tester.runFileListTester(primaryStage);
        //Tester.runFileIconTester(primaryStage);
        //Tester.runListCellTester(primaryStage);
        //Tester.runGridCellTester(primaryStage);
        //Tester.runGridViewTester(primaryStage);
        
        Main.runFilePlaza(primaryStage);
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