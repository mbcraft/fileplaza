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

package it.mbcraft.fileplaza.ui.main.menu.help.register;

import it.mbcraft.fileplaza.data.dao.config.SettingsDAO;
import it.mbcraft.fileplaza.data.models.config.Settings;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.state.CurrentLicenseState;
import it.mbcraft.fileplaza.state.license.AbstractLicense;
import it.mbcraft.fileplaza.state.license.LicenseException;
import it.mbcraft.fileplaza.state.license.LicenseFactory;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractSettingsWindow;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.register.RegisterWindow")
public class RegisterWindow extends AbstractSettingsWindow {

    private static final Logger logger = Logger.getLogger("it.mbcraft.fileplaza.ui.main.menu.help.register.RegisterWindow");
    
    private TextField registrationEmailField;
    private TextArea licenseCodeArea;
    private TextArea machineCodeArea;
    private Button generateMachineCodeButton;
    private Label currentLicenseLabel;
        
    public RegisterWindow() {
        super(L(RegisterWindow.class,"Register_Window"),true);
    }

    @Override
    protected void initMiddleContent() {
        
        addToWindow(new Label(L(this,"RegistrationInstructions_Label")));
        
        GridPane pane = new GridPane();
        GridPaneFiller.reset(3);
        
        pane.add(new Label(L(this,"RegistrationEmail_Label")),GridPaneFiller.X(),GridPaneFiller.Y());
        registrationEmailField = new TextField();
        registrationEmailField.setPromptText("example@myemail.com");
        registrationEmailField.setOnKeyTyped(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent t) {
                fireDataChanged();
                if (registrationEmailField.getText()!=null && registrationEmailField.getText().length()>5)
                    generateMachineCodeButton.setDisable(false);
                else
                    generateMachineCodeButton.setDisable(true);
            }
        });
        pane.add(registrationEmailField,GridPaneFiller.X(),GridPaneFiller.Y());
        pane.add(new Pane(),GridPaneFiller.X(),GridPaneFiller.Y());
        
        pane.add(new Label(L(this,"MachineCode_Label")),GridPaneFiller.X(),GridPaneFiller.Y());
        machineCodeArea = new TextArea();
        machineCodeArea.setEditable(false);
        machineCodeArea.setWrapText(true);
        machineCodeArea.setText("");
        pane.add(machineCodeArea,GridPaneFiller.X(),GridPaneFiller.Y());
        
        generateMachineCodeButton = new Button(L(this,"GenerateMachineCode_Button"));
        generateMachineCodeButton.setDisable(true);
        generateMachineCodeButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                updateMachineCode();
            }

        });
        pane.add(generateMachineCodeButton,GridPaneFiller.X(),GridPaneFiller.Y());
        
        pane.add(new Label(L(this,"LicenseCode_Label")),GridPaneFiller.X(),GridPaneFiller.Y());
        licenseCodeArea = new TextArea();
        
        licenseCodeArea.setPromptText("ExampleLicenseCodevcx98yw87yn87crevtntvr76tvc");
        licenseCodeArea.setOnKeyPressed(new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent t) {
                fireDataChanged();
            }
        });
        pane.add(licenseCodeArea,GridPaneFiller.X(),GridPaneFiller.Y());
        pane.add(new Pane(),GridPaneFiller.X(),GridPaneFiller.Y());
        
        pane.add(new Label(L(this,"CurrentLicenseType_Label")),GridPaneFiller.X(),GridPaneFiller.Y());
        currentLicenseLabel = new Label();
        pane.add(currentLicenseLabel,GridPaneFiller.X(),GridPaneFiller.Y());
        pane.add(new Pane(),GridPaneFiller.X(),GridPaneFiller.Y());
        
        addToWindow(pane);
    }
    
    private void updateMachineCode() {
        machineCodeArea.setText(LicenseFactory.getMachineCode(registrationEmailField.getText()));
    }
    
    private void checkEmailAddress() {
        
    }

    @Override
    protected void loadData() {
        AbstractLicense license = CurrentLicenseState.getInstance().getLicense();
        
        registrationEmailField.setText(license.getRegistrationEmail());
        licenseCodeArea.setText(license.getLicenseCode());
        currentLicenseLabel.setText(license.getLicenseName());
    }

    @Override
    protected void saveData() {
        SettingsDAO dao = SettingsDAO.getInstance();
        Settings s = dao.load();
        
        String registrationEmail = registrationEmailField.getText();
        String licenseCode = licenseCodeArea.getText();
        
        s.setRegistrationEmail(registrationEmail);
        s.setLicenseCode(licenseCode);
        
        dao.save(s);
    }

    @Override
    protected boolean validateBeforeSave() {
        try {
            String registrationEmail = registrationEmailField.getText();
            String licenseCode = licenseCodeArea.getText();
            
            AbstractLicense license = LicenseFactory.getCurrentLicense(licenseCode);
            return true;
        } catch (LicenseException ex) {
            Logger.getLogger(RegisterWindow.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
}