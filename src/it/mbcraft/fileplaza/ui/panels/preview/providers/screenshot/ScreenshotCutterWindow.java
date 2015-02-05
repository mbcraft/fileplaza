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

package it.mbcraft.fileplaza.ui.panels.preview.providers.screenshot;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.ui.common.components.misc.GlassPane;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ScreenshotCutterWindow {

    //misc layout
    private final Stage myStage;
    private final BorderPane content;

    private Group imageAndCut;
    private Rectangle selection;

    private VBox commandsPanel;

    //state
    private boolean selectMode = false;
    private Point firstClick = null;

    //buttons
    private Button selectButton;
    private Button dropSelectionButton;
    private Button cancelButton;
    private Button confirmButton;

    //screenshots
    private BufferedImage myInputScreenshot;
    private BufferedImage myOutputScreenshot;

    private static final double SCREENSHOT_SCALE_FACTOR = 0.9;

    public ScreenshotCutterWindow() {
        myStage = new Stage();
        myStage.setTitle(L(this, "ScreenshotCutter_Window"));
        content = new BorderPane();
        myStage.setScene(new Scene(content));

        initContent();
        initCommands();
    }

    private void initContent() {
        imageAndCut = new Group();
        imageAndCut.setScaleX(SCREENSHOT_SCALE_FACTOR);
        imageAndCut.setScaleY(SCREENSHOT_SCALE_FACTOR);
        content.setCenter(imageAndCut);
        selection = new Rectangle(0, 0, 0, 0);
        selection.setFill(Color.TRANSPARENT);
        selection.setStroke(Color.BLACK);
        imageAndCut.getChildren().add(selection);
    }

    private void initCommands() {
        commandsPanel = new VBox();
        commandsPanel.setSpacing(10);

        cancelButton = new Button();
        cancelButton.setTooltip(new Tooltip(L(this, "CancelButton_Tooltip")));
        cancelButton.setGraphic(IconFactory.getFeatureIcon("Red_Cross", 32));
        cancelButton.setOnAction(new EventHandler() {

            @Override
            public void handle(Event t) {
                myStage.hide();
            }
        });

        confirmButton = new Button();
        confirmButton.setTooltip(new Tooltip(L(this, "ConfirmButton_Tooltip")));
        confirmButton.setGraphic(IconFactory.getFeatureIcon("Task_Done_32", 32));
        confirmButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (firstClick != null) {
                    //devo dividere perchè il subimage è eseguito sull'immagine a grandezza originale.
                    myOutputScreenshot = myInputScreenshot.getSubimage((int)(selection.getX()),(int)(selection.getY()),(int)(selection.getWidth()), (int)(selection.getHeight()));
                
                } else {
                    myOutputScreenshot = myInputScreenshot;
                }
                myStage.hide();
            }

        });

        selectButton = new Button();
        selectButton.setTooltip(new Tooltip(L(this, "SelectButton_Tooltip")));
        selectButton.setGraphic(IconFactory.getFeatureIcon("Image_Woodenframe_32", 32));
        selectButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (selectMode) {
                    abortSelection();
                } else {
                    startSelection();
                }
            }

        });

        dropSelectionButton = new Button();
        dropSelectionButton.setTooltip(new Tooltip(L(this, "DropSelectionButton_Tooltip")));
        dropSelectionButton.setGraphic(IconFactory.getFeatureIcon("Delete_Woodenframe_32", 32));
        dropSelectionButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                resetSelectionData();
            }

        });

        commandsPanel.getChildren().addAll(selectButton, dropSelectionButton, cancelButton, confirmButton);
        commandsPanel.setAlignment(Pos.TOP_CENTER);

        content.setRight(ComponentFactory.newLeftPaddingPane(commandsPanel, 16));
    }

    private void updateSelectionMode(boolean isSelecting) {
        if (isSelecting) {
            selectMode = true;
            selectButton.setEffect(new Bloom());
            confirmButton.setDisable(true);
            selectButton.setCursor(Cursor.CROSSHAIR);
            imageAndCut.setCursor(Cursor.CROSSHAIR);
        } else {
            selectMode = false;
            selectButton.setEffect(null);
            confirmButton.setDisable(false);
            selectButton.setCursor(Cursor.DEFAULT);
            imageAndCut.setCursor(Cursor.DEFAULT);
        }
    }

    private void resetSelectionData() {
        selectMode = false;
        firstClick = null;
        selection.setX(0);
        selection.setY(0);
        selection.setWidth(0);
        selection.setHeight(0);
        selectButton.setDisable(false);
        dropSelectionButton.setDisable(true);

    }

    private void startSelection() {
        resetSelectionData();
        updateSelectionMode(true);
    }

    private void abortSelection() {
        resetSelectionData();
        updateSelectionMode(false);
    }

    private void completeSelection() {
        updateSelectionMode(false);
        dropSelectionButton.setDisable(false);
        selectButton.setDisable(true);
    }

    public void updateInputScreenshot(BufferedImage screenshot) {
        if (screenshot == null) {
            throw new InvalidParameterException("Provided screenshot is null!");
        }
        myInputScreenshot = screenshot;
        Image i = SwingFXUtils.toFXImage(screenshot, null);
        ImageView imgView = new ImageView(i);

        GlassPane glass = new GlassPane(i.getWidth(), i.getHeight());
        
        updateSelectionListeners(glass);
        imageAndCut.getChildren().clear();
        imageAndCut.getChildren().addAll(imgView, selection, glass);
        
    }

    public void updateSelectionListeners(Node glass) {
        glass.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                if (selectMode) {
                    if (t.getButton() == MouseButton.PRIMARY) {
                        if (firstClick == null) {
                            firstClick = new Point((int) t.getX(), (int) t.getY());

                            selection.setX(firstClick.x);
                            selection.setY(firstClick.y);
                            selection.setWidth(0);
                            selection.setHeight(0);
                        } else {
                            completeSelection();
                        }
                    }
                    if (t.getButton() == MouseButton.SECONDARY) {
                        resetSelectionData();
                    }
                }
            }
        });

        glass.setOnMouseMoved(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if (selectMode && firstClick != null) {
                    int startX = t.getX() < firstClick.x ? (int) t.getX() : firstClick.x;
                    int startY = t.getY() < firstClick.y ? (int) t.getY() : firstClick.y;

                    int width = (t.getX() < firstClick.x ? firstClick.x : (int) t.getX()) - startX;
                    int height = (t.getY() < firstClick.y ? firstClick.y : (int) t.getY()) - startY;

                    selection.setX(startX);
                    selection.setY(startY);
                    selection.setWidth(width);
                    selection.setHeight(height);
                }
            }
        });
    }

    public BufferedImage getOutputScreenshot() {
        return myOutputScreenshot;
    }

    public void waitForSelection() {
        resetSelectionData();
        myStage.showAndWait();
    }

}
