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

package it.mbcraft.fileplaza.ui.panels.info;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.ui.common.helpers.CommonActionNodeContainer;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.utils.FileUtils;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class InfoPanel implements INodeProvider {
    
    private final GridPane grid = new GridPane();
    
    private Label typeValueLabel;
    private Label nameValueLabel;
    private Label extensionValueLabel;
    private Label sizeValueLabel;
    private Label lastChangedValueLabel;
    private Label readableValueLabel;
    private Label writableValueLabel;
    private Label executableValueLabel;
    private Label hiddenValueLabel;
    
    private final CommonActionNodeContainer container = new CommonActionNodeContainer();
    
    public InfoPanel(ObjectProperty<File> currentSelectedFile) {
        initGrid();
        initContent();
        initBindings(currentSelectedFile);
    }
    
    private void initGrid() {
        GridPaneFiller.reset(2);
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(8);
        grid.getColumnConstraints().add(new ColumnConstraints(100));
    }
    
    private void initContent() {
        
        
        //type
        Label typeLabel = new Label(L(this,"Type_Label"));
        container.add(typeLabel);
        grid.add(ComponentFactory.newRightPaddingPane(typeLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        typeValueLabel = new Label();
        container.add(typeValueLabel);
        grid.add(typeValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //name
        Label nameLabel = new Label(L(this,"Name_Label"));
        container.add(nameLabel);
        grid.add(ComponentFactory.newRightPaddingPane(nameLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        nameValueLabel = new Label();
        container.add(nameValueLabel);
        grid.add(nameValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //extension
        Label extensionLabel = new Label(L(this,"Extension_Label"));
        container.add(extensionLabel);
        grid.add(ComponentFactory.newRightPaddingPane(extensionLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        extensionValueLabel = new Label();
        container.add(extensionValueLabel);
        grid.add(extensionValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //size
        Label sizeLabel = new Label(L(this,"Size_Label"));
        container.add(sizeLabel);
        grid.add(ComponentFactory.newRightPaddingPane(sizeLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        sizeValueLabel = new Label();
        container.add(sizeValueLabel);
        grid.add(sizeValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //last changed
        Label lastChangedLabel = new Label(L(this,"LastChanged_Label"));
        container.add(lastChangedLabel);
        grid.add(ComponentFactory.newRightPaddingPane(lastChangedLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        lastChangedValueLabel = new Label();
        container.add(lastChangedValueLabel);
        grid.add(lastChangedValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //readable
        Label readableLabel = new Label(L(this,"Readable_Label"));
        container.add(readableLabel);
        grid.add(ComponentFactory.newRightPaddingPane(readableLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        readableValueLabel = new Label();
        container.add(readableValueLabel);
        grid.add(readableValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //writable
        Label writableLabel = new Label(L(this,"Writable_Label"));
        container.add(writableLabel);
        grid.add(ComponentFactory.newRightPaddingPane(writableLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        writableValueLabel = new Label();
        container.add(writableValueLabel);
        grid.add(writableValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //executable
        Label executableLabel = new Label(L(this,"Executable_Label"));
        container.add(executableLabel);
        grid.add(ComponentFactory.newRightPaddingPane(executableLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        executableValueLabel = new Label();
        container.add(executableValueLabel);
        grid.add(executableValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        //hidden
        Label hiddenLabel = new Label(L(this,"Hidden_Label"));
        container.add(hiddenLabel);
        grid.add(ComponentFactory.newRightPaddingPane(hiddenLabel,0), GridPaneFiller.X(), GridPaneFiller.Y());
        
        hiddenValueLabel = new Label();
        container.add(hiddenValueLabel);
        grid.add(hiddenValueLabel, GridPaneFiller.X(), GridPaneFiller.Y());
        
        container.setDisable(true);
    }
    
    private void initBindings(ObjectProperty<File> currentSelectedFile) {
        currentSelectedFile.addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                if (newValue==null)
                    setupUnselectedFile();
                else {
                    try {
                        setupWithFile(newValue);
                    } catch (IOException ex) {
                        Logger.getLogger(InfoPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }
    
    private void setupUnselectedFile() {
        container.setDisable(true);
        typeValueLabel.setText(null);
        nameValueLabel.setText(null);
        extensionValueLabel.setText(null);
        sizeValueLabel.setText(null);
        lastChangedValueLabel.setText(null);
        readableValueLabel.setText(null);
        readableValueLabel.setGraphic(null);
        writableValueLabel.setText(null);
        writableValueLabel.setGraphic(null);
        executableValueLabel.setText(null);
        executableValueLabel.setGraphic(null);
        hiddenValueLabel.setText(null);
        hiddenValueLabel.setGraphic(null);
    }
    
    private void setupWithFile(File f) throws IOException {
        container.setDisable(false);
        
        //type
        if (f.isFile() || f.isDirectory()) {
            if (f.isFile())
                typeValueLabel.setText("File");
            else
                typeValueLabel.setText("Directory");
        } 
        else
            typeValueLabel.setText("Other");
        
        //name
        nameValueLabel.setText(FileUtils.getNameFromFile(f));
        
        //extension
        extensionValueLabel.setText(FileUtils.getExtensionFromFile(f));

        //size
        if (f.isFile() || f.isDirectory()) {
            if (f.isFile())
                sizeValueLabel.setText(""+f.length());
            else
                sizeValueLabel.setText(f.listFiles().length+" files inside");
        } else
            sizeValueLabel.setText(""+f.length());
        
        //lastChanged
        Date lastModified = new Date(f.lastModified());
        lastChangedValueLabel.setText(lastModified.toString());
        
        String yesValue = L(this,"Yes_Value");
        String noValue = L(this,"No_Value");
        
        //readable
        String readable = f.canRead() ? yesValue : noValue;
        readableValueLabel.setText(readable);
        
        //writable
        String writable = f.canWrite()? yesValue : noValue;
        writableValueLabel.setText(writable);
        
        //executable
        String executable = f.canExecute()? yesValue : noValue;
        executableValueLabel.setText(executable);
        
        //hidden
        String hidden = f.isHidden()? yesValue : noValue;
        hiddenValueLabel.setText(hidden);
    }

    @Override
    public Node getNode() {
        return grid;
    }


}
