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

package it.mbcraft.fileplaza.ui.window;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.IconFactory;
import it.mbcraft.fileplaza.ui.main.menu.file.actions.FileQuitAction;
import java.awt.GraphicsEnvironment;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * TO DO : fix button styles and icon
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class WindowTitleBar implements INodeProvider {

    private final BorderPane titleBar;
    
    private final Button minimizeButton;
    private final Button halfOrFullButton;
    private final Button leftOrRightButton;
    private final Button closeButton;
    
    private StageSize currentStageSize = StageSize.FULL;
    private StagePosition currentStagePosition = StagePosition.LEFT;
    
    private final String commonBackgroundStyle;
    private final int screenWidth;
    
    enum StageSize {
        HALF,FULL;
    }
    
    enum StagePosition {
        LEFT,RIGHT;
    }
    
    public WindowTitleBar(String title, Stage st) {
        titleBar = new BorderPane();
        
        screenWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
        
        Image appIcon = IconFactory.getAppIconAsImage(24);
        
        st.setTitle(title);
        st.getIcons().add(appIcon);
        
        commonBackgroundStyle = "-fx-background-color:#6fe187;";
        
        titleBar.setStyle(commonBackgroundStyle);
                
        Label titleLabel = new Label(title);
        titleLabel.setGraphic(new ImageView(appIcon));
        titleLabel.setStyle(commonBackgroundStyle);
        titleLabel.setAlignment(Pos.CENTER_LEFT);
        titleLabel.setFont(new Font("Arial",16));
        titleBar.setLeft(titleLabel);
        
        HBox windowIcons = new HBox();
        
        minimizeButton = initMinimizeButton(st);
        leftOrRightButton = initLeftOrRightButton(st);
        halfOrFullButton = initHalfOrFullButton(st);      
        closeButton = initCloseButton(st);
        
        windowIcons.getChildren().addAll(minimizeButton,halfOrFullButton,leftOrRightButton,closeButton);
        
        titleBar.setRight(windowIcons);
    }
    
    @Override
    public Node getNode() {
        return titleBar;
    }
    
    private Button initMinimizeButton(final Stage st) {
        Button bt = new Button();
        bt.setMinSize(24, 24);
        bt.setMaxSize(24, 24);
        bt.setText(null);
        bt.setGraphic(IconFactory.getFeatureIcon("Minimize_Silver_32", 16));
        bt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                st.setIconified(true);
            }
        
        });
        return bt;
    }
    
    private Button initHalfOrFullButton(final Stage st) {
        Button bt = new Button();
        bt.setMinSize(24, 24);
        bt.setMaxSize(24, 24);
        bt.setText(null);
        bt.setGraphic(IconFactory.getFeatureIcon("Half_Widescreen_32", 16));
        bt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (currentStageSize==StageSize.HALF) {
                    st.setX(0);
                    st.setWidth(screenWidth);
                    currentStageSize = StageSize.FULL;
                    leftOrRightButton.setDisable(true);
                } else
                {
                    st.setWidth(screenWidth/2);
                    if (currentStagePosition==StagePosition.LEFT) {
                        st.setX(0);
                    } else {
                        st.setX(screenWidth/2);
                    }
                    currentStageSize = StageSize.HALF;
                    leftOrRightButton.setDisable(false);
                }
                
            }
        });
        return bt;
    }
    
    private Button initLeftOrRightButton(final Stage st) {
        Button bt = new Button();
        bt.setMinSize(24, 24);
        bt.setMaxSize(24, 24);
        bt.setText(null);
        bt.setGraphic(IconFactory.getFeatureIcon("Arrow_LeftRight_Silver_32", 16));
        bt.setDisable(true);
        bt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (currentStagePosition==StagePosition.LEFT) {
                    st.setX(st.getWidth());
                    currentStagePosition = StagePosition.RIGHT;
                } else
                {
                    st.setX(0);
                    currentStagePosition = StagePosition.LEFT;
                }
                
            }
        });
        return bt;
    }
    
    private Button initCloseButton(final Stage st) {
        Button bt = new Button();
        bt.setMinSize(24, 24);
        bt.setMaxSize(24, 24);
        bt.setText(null);
        bt.setGraphic(IconFactory.getFeatureIcon("Close", 16));
        bt.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                FileQuitAction.getInstance().handle(t);
            }
        
        });
        return bt;
    }
    
}
