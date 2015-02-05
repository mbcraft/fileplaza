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

package it.mbcraft.fileplaza.ui.dialogs;

/**
 * This is a factory for common dialog windows.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DialogFactory {
    
    /**
     * This method creates a simple input dialog with a text field.
     * 
     * @param title The title of the dialog
     * @param inputLabel The label of the input
     * @param defaultValue The default value set
     * 
     * @return The dialog
     */
    public static Dialog newTextInputDialog(String title, String inputLabel, String defaultValue) {
        DialogInputField inputText = DialogInputFieldBuilder.create()
                .label(inputLabel)
                .defaultValue(defaultValue)
                .type(DialogInputField.Type.STRING)
                .build();
        
        return DialogBuilder.create()
                .input(inputText)
                .type(Dialog.Type.INPUT)
                .titleText(title)
                .buttonAlignment(Dialog.ButtonAlignment.RIGHT)
                .message("")
                .modal(true)
                .build();        
    }
    
    /**
     * This method display an information dialog to the user.
     * 
     * @param title The title of the dialog
     * @param message The message to display
     */
    public static void showInformationDialog(String title,String message) {
        DialogBuilder.create()
                .titleText(title)
                .message(message)
                .modal(true)
                .type(Dialog.Type.INFO)
                .buttonAlignment(Dialog.ButtonAlignment.RIGHT)
                .build().show();
    }
    
    /**
     * This method shows an error dialog
     * 
     * @param title The title of the dialog
     * @param message The message to display to the user
     */
    public static void showErrorDialog(String title, String message) {
            DialogBuilder.create()
                .titleText(title)
                .message(message)
                .modal(true)
                .type(Dialog.Type.ERROR)
                .buttonAlignment(Dialog.ButtonAlignment.RIGHT)
                .build().show();
    }
    /**
     * This method shows a confirm dialog. User can choose 'OK' or 'Cancel'.
     * @param title The title of the confirm dialog
     * @param message The message to display to the user.
     * 
     * @return The result of the shown dialog.
     */
    public static boolean newConfirmDialog(String title, String message) {
            return DialogBuilder.create()
                .titleText(title)
                .message(message)
                .modal(true)
                .type(Dialog.Type.ACCEPT)
                .button(DialogButtonBuilder.create().type(DialogButton.Type.OK).build())
                .button(DialogButtonBuilder.create().type(DialogButton.Type.CANCEL).build())
                .buttonAlignment(Dialog.ButtonAlignment.RIGHT)
                .build().show() == DialogButton.Type.OK;
    }
}
