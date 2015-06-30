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

import it.mbcraft.fileplaza.service.drivewatch.os.DriveIdentifier;
import java.io.File;
import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Contains a property with the current drive used in file browsers.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class CurrentDriveState {
    private final ObservableList<DriveIdentifier> driveList = FXCollections.observableArrayList();
    private final ObjectProperty<ObservableList<DriveIdentifier>> myDriveListProperty = new SimpleObjectProperty(driveList);
    private final ObjectProperty<DriveIdentifier> myCurrentDriveProperty = new SimpleObjectProperty(null);
    
    public CurrentDriveState() {
        
        //add default behaviour for drive list changes
        myDriveListProperty.addListener(new ChangeListener<ObservableList<DriveIdentifier>>(){

            @Override
            public void changed(ObservableValue<? extends ObservableList<DriveIdentifier>> ov, ObservableList<DriveIdentifier> oldValue, ObservableList<DriveIdentifier> newValue) {

                //if the old list was empty and the new is not empty, set the current drive to the first drive available
                if (oldValue.isEmpty() && !newValue.isEmpty()) {
                    myCurrentDriveProperty.setValue(newValue.get(0));
                }
                //if the new list is empty, set the current drive to null
                if (newValue.isEmpty()) {
                    myCurrentDriveProperty.setValue(null);
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
        myCurrentDriveProperty.addListener(new ChangeListener<DriveIdentifier>(){

            @Override
            public void changed(ObservableValue<? extends DriveIdentifier> ov, DriveIdentifier oldValue, DriveIdentifier newValue) {
                if (newValue!=null) {
                    File mp = new File(newValue.getMountPoint());
                    currentPathProperty.setValue(mp);
                } else
                    currentPathProperty.setValue(null);
            }
        });
    }
    
    /**
     * Returns the list of the currently available drives, as a property.
     * 
     * @return The ObservableList of DriveIdentifier s, contained inside an ObjectProperty
     */
    public ObjectProperty<ObservableList<DriveIdentifier>> driveListProperty() {
        return myDriveListProperty;
    }
    
    /**
     * Returns the current drive, as an ObjectProperty.
     * 
     * @return The DriveIdentifier inside an ObjectProperty
     */
    public ObjectProperty<DriveIdentifier> currentDriveProperty() {
        return myCurrentDriveProperty;
    }

    /**
     * Returns the current drive list, as a List of DriveIdentifier s.
     * 
     * TO FIX : check if this method is needed and the list can not be obtained from the
     * driveList property.
     * 
     * @return A List of DriveIdentifier.
     */
    public List<DriveIdentifier> getDriveList() {
        return driveList;
    }

}
