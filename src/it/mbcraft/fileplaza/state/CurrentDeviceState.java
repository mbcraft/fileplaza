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

package it.mbcraft.fileplaza.state;

import it.mbcraft.fileplaza.service.os.DriveIdentifier;
import it.mbcraft.fileplaza.ui.main.browse.path.CurrentPathPanel;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CurrentDeviceState {
    
    private final ObjectProperty<ObservableList<DriveIdentifier>> myDriveListProperty = new SimpleObjectProperty(FXCollections.emptyObservableList());

    private final ObjectProperty<DriveIdentifier> myCurrentDrive = new SimpleObjectProperty();
    
    public CurrentDeviceState() {
        
        //add default behaviour for drive list changes
        myDriveListProperty.addListener(new ChangeListener<ObservableList<DriveIdentifier>>(){

            @Override
            public void changed(ObservableValue<? extends ObservableList<DriveIdentifier>> ov, ObservableList<DriveIdentifier> oldValue, ObservableList<DriveIdentifier> newValue) {
                //if the old list was empty and the new is not empty, set the current drive to the first drive available
                if (oldValue.isEmpty() && !newValue.isEmpty()) {
                    myCurrentDrive.setValue(newValue.get(0));
                }
                //if the new list is empty, set the current drive to null
                if (newValue.isEmpty()) {
                    myCurrentDrive.setValue(null);
                }
            }
        });
    }
    
    /**
     * Links drive changes to changes in the current path
     * 
     * @param currentPathProperty The property containing the current path
     */
    public void linkDriveChangesToPathChanges(final ObjectProperty<File> currentPathProperty) {
        myCurrentDrive.addListener(new ChangeListener<DriveIdentifier>(){

            @Override
            public void changed(ObservableValue<? extends DriveIdentifier> ov, DriveIdentifier oldValue, DriveIdentifier newValue) {
                File mp = new File(newValue.getMountPoint());
                currentPathProperty.set(mp);
            }
        });
    }
    
    public ObjectProperty<ObservableList<DriveIdentifier>> driveListProperty() {
        return myDriveListProperty;
    }
    
    public ObjectProperty<DriveIdentifier> currentDriveProperty() {
        return myCurrentDrive;
    }

}
