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

package it.mbcraft.fileplaza.ui.dialogs;

import it.mbcraft.fileplaza.ui.common.helpers.WindowStack;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Mark Heckler (mark.heckler@gmail.com, @MkHeck)
 * 
 * Some changes by Marco Bagnaresi (marco.bagnaresi@gmail.com)
 */
public class Dialog {

    /**
     * Type of dialog box is one of the following, each with a distinct icon:
     * <p>
     * ACCEPT = check mark icon
     * <p>
     * ERROR = red 'X' icon
     * <p>
     * INFO = blue 'i' (information) icon
     * <p>
     * QUESTION = blue question mark icon
     * <p>
     * If no type is specified in the constructor, the default is INFO.
     */
    public enum Type { INPUT, ACCEPT, ERROR, INFO, QUESTION };
    public enum ButtonAlignment { LEFT, RIGHT, CENTER };
    
    private boolean shown = false;
    private Type type;
    private Scene scene;
    private Stage stage;
    private final BorderPane pane = new BorderPane();
    private final ImageView icon = new ImageView();
    private final Label message = new Label();
    private final VBox messageAndInputs = new VBox();
    private final GridPane inputGrid = new GridPane();
    private final HBox buttonBox = new HBox(10);
    private final List<DialogButton> buttons = new ArrayList<>();
    private final List<DialogInputField> inputs = new ArrayList<>();
    private final List<Object> propertiesAndSelectionModes = new ArrayList<>();
    private int buttonCancel = -1;
    private int buttonSelected = -1;
    private ButtonAlignment buttonAlignment = ButtonAlignment.CENTER;
    private float displayTime = 0f;
    private float fadeInOutTime;
    private final List<String> stylesheets = new ArrayList<>();

    /**
     * Default constructor for a Dialog box. Creates an INFO box.
     *
     * @see Type
     */
    public Dialog() {
        initDialog(Type.INFO);
    }

    /**
     * Constructor for a Dialog box that accepts one of the enumerated
     * types listed above.
     *
     * @param t The type of Dialog dialog box to create.
     * @see Type
     */
    public Dialog(Type t) {
        initDialog(t);
    }

    private void addOKButton() {
        DialogButton okBtn = new DialogButton();
        okBtn.setType(DialogButton.Type.OK);
        okBtn.setLabel("_OK");
        okBtn.setCancelButton(false);
        okBtn.setDefaultButton(true);

        addButton(okBtn);
    }
    
    private void addCancelButton() {
        DialogButton cancelBtn = new DialogButton();
        cancelBtn.setType(DialogButton.Type.CANCEL);
        cancelBtn.setLabel("_Cancel");
        cancelBtn.setCancelButton(true);
        cancelBtn.setDefaultButton(false);

        addButton(cancelBtn);
    }

    private void addYesNoButtons() {
        /*
         * No default or cancel buttons designated, by design.
         * Some cases would require the Yes button to be default & No to cancel,
         * while others would require the opposite. You as the developer can 
         * assign default/cancel Yes/No buttons using the full addButtons()
         * method if required. You have the power!
         */
        DialogButton yesBtn = new DialogButton();
        yesBtn.setType(DialogButton.Type.YES);
        yesBtn.setLabel("_Yes");
        yesBtn.setCancelButton(false);
        yesBtn.setDefaultButton(false);

        addButton(yesBtn);

        DialogButton noBtn = new DialogButton();
        noBtn.setType(DialogButton.Type.NO);
        noBtn.setLabel("_No");
        noBtn.setCancelButton(false);
        noBtn.setDefaultButton(false);

        addButton(noBtn);
    }
    
    /**
     * Public method used to add an input to a Dialog dialog.
     * 
     * @param ii A DialogInputField input to add
     */
    
    public void addInput(DialogInputField ii) {
        inputs.add(ii);
        
        Label l = new Label(ii.getLabel());
        
        switch (ii.getType()) {
            case TEXT : {
                //label on the left
                l.setAlignment(Pos.CENTER_RIGHT);
                inputGrid.add(l, 0, inputs.size()-1);
                
                //control on the right
                TextArea area = new TextArea();
                area.setPrefColumnCount(10);
                area.setPrefRowCount(5);
                if (ii.getDefaultValue()!=null)
                    area.setText((String)ii.getDefaultValue());
                propertiesAndSelectionModes.add(area.textProperty());
                inputGrid.add(area, 1, inputs.size()-1);
                break;
            }
            case STRING : {
                //label on the left
                l.setAlignment(Pos.CENTER_RIGHT);
                inputGrid.add(l, 0, inputs.size()-1);
                
                //control on the right
                TextField field = new TextField();
                field.setPrefColumnCount(10);
                field.setAlignment(Pos.CENTER_LEFT);
                if (ii.getDefaultValue()!=null)
                    field.setText((String)ii.getDefaultValue());
                propertiesAndSelectionModes.add(field.textProperty());
                inputGrid.add(field, 1, inputs.size()-1);
                break;
            }
            case INTEGER : {
                //label on the left
                l.setAlignment(Pos.CENTER_RIGHT);
                inputGrid.add(l, 0, inputs.size()-1);
                
                //control on the right
                TextField field = new TextField();
                field.setPrefColumnCount(10);
                field.setAlignment(Pos.CENTER_LEFT);
                if (ii.getDefaultValue()!=null)
                    field.setText((String)ii.getDefaultValue());
                propertiesAndSelectionModes.add(field.textProperty());
                inputGrid.add(field, 1, inputs.size()-1);
                break;
            }
            case FLOAT : {
                //label on the left
                l.setAlignment(Pos.CENTER_RIGHT);
                inputGrid.add(l, 0, inputs.size()-1);
                
                //control on the right
                TextField field = new TextField();
                field.setPrefColumnCount(10);
                field.setAlignment(Pos.CENTER_LEFT);
                if (ii.getDefaultValue()!=null)
                    field.setText(ii.getDefaultValue().toString());
                propertiesAndSelectionModes.add(field.textProperty());
                inputGrid.add(field, 1, inputs.size()-1);
                break;
            }
            case COMBOBOX : {
                //label on the left
                l.setAlignment(Pos.CENTER_RIGHT);
                inputGrid.add(l, 0, inputs.size()-1);
                
                //control on the right
                ComboBox combobox = new ComboBox();
                if (ii.getCellFactory()!=null)
                    combobox.setCellFactory(ii.getCellFactory());
                if (ii.getValueList()!=null)
                    combobox.getItems().addAll(ii.getValueList());
                if (ii.getDefaultValue()!=null) {
                    combobox.getSelectionModel().select(ii.getDefaultValue());
                }
                propertiesAndSelectionModes.add(combobox.getSelectionModel());
                inputGrid.add(combobox, 1, inputs.size()-1);
                break;
            }
            case LIST : {
                //label on the left
                l.setAlignment(Pos.CENTER_RIGHT);
                inputGrid.add(l, 0, inputs.size()-1);
                
                //control on the right
                ListView listview = new ListView();
                if (ii.getCellFactory()!=null)
                    listview.setCellFactory(ii.getCellFactory());
                if (ii.getValueList()!=null)
                    listview.getItems().addAll(ii.getValueList());
                if (ii.getDefaultValue()!=null) {
                    listview.getSelectionModel().select(ii.getDefaultValue());
                }
                propertiesAndSelectionModes.add(listview.getSelectionModel());
                inputGrid.add(listview, 1, inputs.size()-1);
                break;
            }
            case CHECKBOX : {
                //checkbox input is on the left
                CheckBox checkbox = new CheckBox();
                checkbox.setAllowIndeterminate(false);
                checkbox.setAlignment(Pos.CENTER_RIGHT);
                inputGrid.add(checkbox, 0, inputs.size()-1);
                
                //label on the right
                l.setAlignment(Pos.CENTER_LEFT);
                if (ii.getDefaultValue()!=null) {
                    checkbox.setSelected(Boolean.parseBoolean(ii.getDefaultValue().toString()));
                }
                propertiesAndSelectionModes.add(checkbox.selectedProperty());
                inputGrid.add(l, 1, inputs.size()-1);
                break;
            }
            default : throw new IllegalStateException("No type has been found.");
        }
        
    }
    
    /**
     * Returns the index DialogInputField added to this Dialog dialog.
     * 
     * @param index The index of the input
     * 
     * @return The DialogInputField
     */
    public DialogInputField getInput(int index) {
        return inputs.get(index);
    }
    
    /**
     * Returns the SelectionModel for the ix-th input control.
     * 
     * @param ix The index of the input
     * @return The SelectionModel with the selected values.
     */
    public SelectionModel getInputSelectionModel(int ix) {
        SelectionModel model = (SelectionModel) propertiesAndSelectionModes.get(ix);
        return model;
    }
    
    /**
     * Returns the input property of the ix-th input control.
     * 
     * @param ix
     * @return The property of the input control
     */
    public Property getInputProperty(int ix) {
        Property prop = (Property) propertiesAndSelectionModes.get(ix);
        return prop;
    }

    /**
     * Public method used to add a button to a Dialog dialog.
     *
     * @param btnToAdd A DialogButton object.
     *
     * @see DialogButton
     */
    public void addButton(DialogButton btnToAdd) {
        buttons.add(btnToAdd);

        final Button btn = new Button();

        btn.setMnemonicParsing(true);
        btn.setText(btnToAdd.getLabel());

        if (btnToAdd.getIcon() != null) {
            btn.setGraphic(btnToAdd.getIcon());
        }

        btn.setDefaultButton(btnToAdd.isDefaultButton());
        if (btnToAdd.isCancelButton()) {
            btn.setCancelButton(true);
            buttonCancel = buttons.size() - 1;
        }

        if (btn.isDefaultButton()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    btn.requestFocus();
                }
            });
        }

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent evt) {
                // Iterate through to find correct button.
                for (int i=0; i < buttons.size(); i++) {
                    if (buttons.get(i).getLabel().equalsIgnoreCase(((Button) evt.getSource()).getText())) {
                        buttonSelected = i;
                        break;
                    }
                }

                // Close the dialog
                ((Stage) ((Node) evt.getSource()).getScene().getWindow()).close();
            }
        });

        buttonBox.getChildren().add(btn);
    }

    /**
     * Allows developer to add stylesheet for Dialog dialog, supplementing or
 overriding existing styling.
     *
     * @param stylesheet String variable containing the path/name of the
     * stylesheet to apply to the dialog's scene and contained controls.
     */
    public void addStylesheet(String stylesheet) {
        try {
            String newStyle = this.getClass().getResource(stylesheet).toExternalForm();
            stylesheets.add(newStyle);
        } catch (Exception ex) {
            System.err.println("Unable to find specified stylesheet: " + stylesheet);
            System.err.println("Error message: " + ex.getMessage());
        }
    }

    private void initDialog(Type t) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(WindowStack.top());
        setType(t);
        
        stage.setMaxWidth(Screen.getPrimary().getVisualBounds().getWidth() / 2);
    }

    private void loadIconFromResource(String fileName) {
        Image imgIcon = new Image(getClass().getResourceAsStream(fileName));
        icon.setPreserveRatio(true);
        icon.setFitHeight(48);
        icon.setImage(imgIcon);
    }

    /**
     * Sets the button alignment for the Dialog dialog box. Default is CENTER.
     *
     * @param buttonAlignment Valid values are LEFT, RIGHT, and CENTER.
     *
     * @see ButtonAlignment
     */
    public void setButtonAlignment(ButtonAlignment buttonAlignment) {
        this.buttonAlignment = buttonAlignment;
    }

    /**
     * Sets the display time for the Dialog dialog box. Default is 10
     * seconds.
     *
     * @param displayTime Valid values are any integer value.
     */
    public void setDisplayTime(int displayTime) {
        this.displayTime = displayTime;

        // Fade in/out time = max of 5 seconds
        fadeInOutTime = Math.min(displayTime/4f, 5f);
    }

    /**
     * Sets the text displayed within the Dialog dialog box. Word wrap
     * ensures that all text is displayed.
     *
     * @param msg String variable containing the text to display.
     */
    public void setMessage(String msg) {
        message.setText(msg);
        message.setWrapText(true);
    }

    /**
     * Sets the modality of the Dialog dialog box.
     *
     * @param isModal Boolean. A true value = APPLICATION_MODAL, false = NONE.
     */
    public void setModal(boolean isModal) {
        stage.initModality((isModal ? Modality.APPLICATION_MODAL : Modality.NONE));
    }

    /**
     * Sets the text displayed in the title bar of the Dialog dialog box.
     *
     * @param title String containing the text to place in the title bar.
     */
    public void setTitleText(String title) {
        stage.setTitle(title);
    }

    /**
     * Sets the Type of Dialog dialog box to display.
     *
     * @param typeToSet One of the supported types of dialogs.
     * @see Type
     */
    public void setType(Type typeToSet) {
        type = typeToSet;
    }

    /**
     * Sets the coordinates of the Dialog dialog.
     * 
     * @param x The x coordinate of the upper-left corner of the dialog.
     * @param y The y coordinate of the upper-left corner of the dialog.
     */
    public void setPos(double x, double y) {
        stage.setX(x);
        stage.setY(y);
    }

    /**
     * Sets the x coordinate of the Dialog dialog.
     * 
     * @param x The x coordinate of the upper-left corner of the dialog.
     */
    public void setX(double x) {
        stage.setX(x);
    }

    /**
     * Sets the y coordinate of the Dialog dialog.
     * 
     * @param y The y coordinate of the upper-left corner of the dialog.
     */
    public void setY(double y) {
        stage.setY(y);
    }
    
    public void setInputsHgap(double pixels) {
        inputGrid.setHgap(pixels);
    }
    
    public void setInputsVgap(double pixels) {
        inputGrid.setVgap(pixels);
    }

    private void populateStage() {
        String iconFile;
        
        //adds the message in the "message and input" panel.
        messageAndInputs.getChildren().add(message);

        switch (type) {
            case INPUT :
                iconFile = "resources/images/Dialog-info.jpg";
                messageAndInputs.getChildren().add(inputGrid);
                if (buttons.isEmpty()) {
                    addOKButton();
                    addCancelButton();
                }
                break;
            case ACCEPT:
                iconFile = "resources/images/Dialog-accept.jpg";
                if (buttons.isEmpty()) {
                    addOKButton();
                }
                break;
            case ERROR:
                iconFile = "resources/images/Dialog-error.jpg";
                if (buttons.isEmpty()) {
                    addOKButton();
                }
                break;
            case INFO:
                iconFile = "resources/images/Dialog-info.jpg";
                if (buttons.isEmpty()) {
                    addOKButton();
                }
                break;
            case QUESTION:
                iconFile = "resources/images/Dialog-question.jpg";
                break;
            default:
                iconFile = "resources/images/Dialog-info.jpg";
                break;
        }

        try {
            loadIconFromResource(iconFile);
        } catch (Exception ex) {
            System.err.println("Exception trying to load icon file: " + ex.getMessage());
        }

        BorderPane.setAlignment(icon, Pos.CENTER_LEFT);
        BorderPane.setMargin(icon, new Insets(5));
        pane.setLeft(icon);
        
        BorderPane.setAlignment(messageAndInputs, Pos.CENTER);
        BorderPane.setMargin(messageAndInputs, new Insets(5));
        pane.setCenter(messageAndInputs);

        switch (buttonAlignment) {
            case LEFT:
                buttonBox.setAlignment(Pos.CENTER_LEFT);
                break;
            case CENTER:
                buttonBox.setAlignment(Pos.CENTER);
                break;
            case RIGHT:
                buttonBox.setAlignment(Pos.CENTER_RIGHT);
                break;
        }

        BorderPane.setMargin(buttonBox, new Insets(5));
        pane.setBottom(buttonBox);

        scene = new Scene(pane);
        for (int i=0; i < stylesheets.size(); i++) {
            try {
                scene.getStylesheets().add(stylesheets.get(i));
            } catch (Exception ex) {
                System.err.println("Unable to load specified stylesheet: " + stylesheets.get(i));
                System.err.println(ex.getMessage());
            }
        }
        stage.setScene(scene);
    }

    /**
     * Displays the Dialog dialog box and waits for user input.
     *
     * @return The type of the button pressed.
     *
     * @see DialogButton.Type
     */
    public DialogButton.Type show() {
        if (shown) throw new IllegalStateException("Dialogs are use-only-once. Use factories.");
        shown = true;
        populateStage();
        if (type == Type.QUESTION) {
            //if (buttons.size() == 0) {
            if (buttons.isEmpty()) {
                addYesNoButtons();
            }
        }

        stage.setResizable(false);
        stage.sizeToScene();

        if (displayTime <= 0f) {  // Show dialog indefinitely
            // Zero value or nonsensical one: who shows a dialog for -10 seconds?
            // Just show it!
            if (!(stage.getX() > -1 && stage.getY() > -1)) {
                stage.centerOnScreen();
            }
            stage.showAndWait();
        } else {    // Timed dialog
            stage.setOpacity(0d);

            stage.show();
            final DoubleProperty opacity = stage.opacityProperty();

            Timeline fadeIn = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0d)),
                    new KeyFrame(new Duration(fadeInOutTime * 1000),
                            new EventHandler() {
                                @Override
                                public void handle(Event t) {
                                    Timeline display = new Timeline(
                                            new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1d)),
                                            new KeyFrame(new Duration((displayTime - fadeInOutTime * 2) * 1000),
                                                    new EventHandler() {
                                                        @Override
                                                        public void handle(Event t) {
                                                            Timeline fadeOut = new Timeline(
                                                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1d)),
                                                                    new KeyFrame(new Duration(fadeInOutTime * 1000),
                                                                            new EventHandler() {
                                                                                @Override
                                                                                public void handle(Event t) {
                                                                                    stage.hide();
                                                                                }
                                                                            }, new KeyValue(opacity, 0d)));
                                                            fadeOut.play();
                                                        }
                                                    }, new KeyValue(opacity, 1d)));
                                    display.play();
                                }
                            }, new KeyValue(opacity, 1d)));
            fadeIn.play();
        }

        if (buttonSelected == -1) {
            /* If a different type of button is designated the "cancel button",
             * e.g. a DialogButton.Type.NO button, return that one;
             * otherwise, return a CANCEL button type.
             */
            return (buttonCancel == -1 ? DialogButton.Type.CANCEL : buttons.get(buttonCancel).getType());
        } else {
            return buttons.get(buttonSelected).getType();
        }
    }
}
