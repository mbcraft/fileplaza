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
