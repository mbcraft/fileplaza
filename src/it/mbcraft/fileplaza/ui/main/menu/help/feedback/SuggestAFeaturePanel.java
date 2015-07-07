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

package it.mbcraft.fileplaza.ui.main.menu.help.feedback;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.net.SuggestionReporter;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class creates a panel for letting user submit a suggestion.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.feedback.SuggestAFeaturePanel")
public class SuggestAFeaturePanel implements INodeProvider {
    
    private final VBox box = new VBox();
    
    private final GridPane grid = new GridPane();
    
    private final ComboBox featureTypeSelector = new ComboBox();
    private final TextField featureTitle = new TextField();
    private final TextArea featureDescription = new TextArea();
    
    /**
     * Creates the panel. No parameters are needed.
     */
    public SuggestAFeaturePanel() {
        initGrid();
        initButtonBar();
    }
    
    @Override
    public Node getNode() {
        return box;
    }

    private void initGrid() {
        GridPaneFiller.reset(2);
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);
        
        initSelector();
        initFeatureTitle();
        initFeatureDescription();
        
        box.getChildren().add(grid);
    }
    
    private void initSelector() {
        Label l = new Label(L(this,"Selector_Label"));
        grid.add(l, GridPaneFiller.X(), GridPaneFiller.Y());
        
        featureTypeSelector.getItems().addAll(Arrays.asList(L(this,"1feature_Choice"),L(this,"2feature_Choice"),L(this,"3feature_Choice"),L(this,"4feature_Choice"),L(this,"5feature_Choice")));
        
        grid.add(featureTypeSelector, GridPaneFiller.X(), GridPaneFiller.Y());
    }
    
    private void initFeatureTitle() {
        Label l = new Label(L(this,"ShortTitle_Label"));
        grid.add(l,GridPaneFiller.X(), GridPaneFiller.Y());

        featureTitle.setPromptText(L(this,"ShortTitle_PromptText"));
        
        grid.add(featureTitle, GridPaneFiller.X(), GridPaneFiller.Y());
    }
    
    private void initFeatureDescription() {
        Label l = new Label(L(this,"Description_Label"));
        grid.add(l,GridPaneFiller.X(),GridPaneFiller.Y());

        featureDescription.setPromptText(L(this,"Description_PromptText"));
        
        grid.add(featureDescription, GridPaneFiller.X(), GridPaneFiller.Y());
    }

    private void initButtonBar() {
        BorderPane pane = new BorderPane();
        pane.setPadding(new Insets(5));
        
        Button sendButton = new Button(L(this,"Send_Button"));
        sendButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (validate()) {
                    submitFeature();
                    DialogFactory.showInformationDialog(L(this,"ThankYou_Dialog"), L(this,"ThankYou_Text"));
                    clearFields();
                }
            }
        });
        
        pane.setCenter(sendButton);
        box.getChildren().add(pane);
    }
    
    private boolean validate() {
        if (featureTypeSelector.getSelectionModel().getSelectedIndex()>=0 && !featureTitle.getText().equals("") && !featureDescription.getText().equals(""))
            return true;
        else {
            DialogFactory.showErrorDialog(L(this,"Error_Dialog"), L(this,"Error_Text"));
            return false;
        }
    }
    
    private void clearFields() {
        featureTypeSelector.getSelectionModel().clearSelection();
        featureTitle.setText(null);
        featureDescription.setText(null);
    }
    
    private void submitFeature() {
        SuggestionReporter rep = new SuggestionReporter();
        rep.suggestFeature(featureTypeSelector.getSelectionModel().getSelectedIndex(),featureTitle.getText(),featureDescription.getText());
    }
    
}
