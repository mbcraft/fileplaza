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

package it.mbcraft.fileplaza;

import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.i18n.Lang;
import it.mbcraft.fileplaza.state.CurrentLicenseState;
import it.mbcraft.fileplaza.ui.window.MainWindowBehaviour;
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

    public static final String RELEASE_DATE = "10/06/2015";
    public static final String SOFTWARE_NAME = "FilePlaza ";
    public static final String VERSION = "0.6";

    private static final Logger logger = Logger.getLogger("it.mbcraft.fileplaza.Main");

    public Main() {
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("").addHandler(new ConsoleHandler());

        logger.config("Logs initialized.");
    }
    
    public static String getVersion() {
        return VERSION;
    }

    public static String getReleaseDate() {
        return RELEASE_DATE;
    }

    private static void loadLicense() {
        logger.config("Loading license ...");
        CurrentLicenseState cls = CurrentLicenseState.getInstance();
        logger.config("License found : " + cls.getLicense().getLicenseName());
    }

    private static void runFilePlaza(Stage stage) {
        loadLicense();

        Lang.init(SettingsDAO.getInstance().load().getCurrentLanguage());

        WindowStack.push(stage);

        MainWindowBehaviour mainWindow = new MainWindowBehaviour(stage, SOFTWARE_NAME + VERSION);
        mainWindow.show();
    }
    
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
