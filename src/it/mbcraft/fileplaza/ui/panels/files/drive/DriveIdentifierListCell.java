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

package it.mbcraft.fileplaza.ui.panels.files.drive;

import it.mbcraft.fileplaza.service.drivewatch.os.DriveIdentifier;
import it.mbcraft.fileplaza.ui.common.IconReference;
import it.mbcraft.fileplaza.ui.common.components.listview.ImprovedListCell;
import javafx.beans.property.IntegerProperty;

/**
 * This class shows a ListCell with a drive icon, depending on the drive type.
 * Used for the drive selector. Support zoom.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DriveIdentifierListCell extends ImprovedListCell<DriveIdentifier> {

    private static final IconReference HDD_MAIN_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "Drive_32");
    private static final IconReference HDD_OTHER_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "Drive_Other_32");
    private static final IconReference CD_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "CD_32");
    private static final IconReference DVD_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "DVD_32");
    private static final IconReference MMC_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "Memory_Card_32");
    private static final IconReference USB_PEN_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "Usb_Pen_32");
    private static final IconReference EXT_HD_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "External_HD_32");
    private static final IconReference TAPE_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "Tape_32");
    private static final IconReference UNKNOWN_REFERENCE = new IconReference(IconReference.IconCategory.MISC, "Unknown_32");
    
    /**
     * Build a DriveIdentifierListCell. Needs a zoom property (can be a fixed IntegerProperty).
     * 
     * @param cellZoomLevelProp The zoom property, as an IntegerProperty
     */
    public DriveIdentifierListCell(IntegerProperty cellZoomLevelProp) {
        super(cellZoomLevelProp);
    }
    
    @Override
    public void updateItem(DriveIdentifier data,boolean empty) {
        
        super.updateItem(data, empty);
        if (empty) {
            setLabelText(null);
            setMainIcon(null);
        } else {
            setLabelText(data.getFriendlyName());
            
            IconReference mainIcon;
            
            switch (data.getDriveType()) {
                case HDD_MAIN : mainIcon = HDD_MAIN_REFERENCE;break;
                case HDD_OTHER : mainIcon = HDD_OTHER_REFERENCE;break;
                case CD : mainIcon = CD_REFERENCE;break;
                case DVD : mainIcon = DVD_REFERENCE;break;
                case MMC : mainIcon = MMC_REFERENCE;break;
                case USB_PEN : mainIcon = USB_PEN_REFERENCE;break;
                case EXT_HD : mainIcon = EXT_HD_REFERENCE;break;
                case TAPE : mainIcon = TAPE_REFERENCE;break;
                case UNKNOWN : mainIcon = UNKNOWN_REFERENCE;break;
                default:throw new IllegalStateException("DriveType not mapped to icon reference.");
            }
            
            setMainIcon(mainIcon);
            
        }
    }
    
}
