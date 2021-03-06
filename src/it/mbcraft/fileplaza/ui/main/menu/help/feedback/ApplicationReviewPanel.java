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

import it.mbcraft.fileplaza.net.SuggestionReporter;
import it.mbcraft.fileplaza.data.dao.config.FeedbackDAO;
import it.mbcraft.fileplaza.data.models.config.ApplicationReview;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * This class builds the 'application review' panel. It enables the user to
 * leave a feedback of the application.
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.feedback.ApplicationReviewPanel")
public class ApplicationReviewPanel implements INodeProvider {
       
    private final VBox container = new VBox();
    private final GridPane grid = new GridPane();
    
    private ComboBox stars;
    private TextArea reviewText;
    private Button editReviewButton;
    private Button submitReviewButton;
        
    /**
     * Builds an instance of the ApplicationReviewPanel
     */
    public ApplicationReviewPanel() { 
        initGrid();
        initButtonBar();
    }
    
    private void initGrid() {
        GridPaneFiller.reset(2);
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);
        
        initStarSelector();
        initReviewTextArea();
        
        container.getChildren().add(grid);
    }
    
    private void initStarSelector() {
        Label l = new Label(L(this,"Choose_Label"));
        grid.add(l, GridPaneFiller.X(), GridPaneFiller.Y());
        
        stars = new ComboBox();
        stars.getItems().addAll(Arrays.asList(L(this,"1star_Choice"),L(this,"2star_Choice"),L(this,"3star_Choice"),L(this,"4star_Choice"),L(this,"5star_Choice")));
        stars.getSelectionModel().select(2);        
        grid.add(stars,GridPaneFiller.X(),GridPaneFiller.Y());
    }
    
    private void initReviewTextArea() {
        Label l = new Label(L(this,"ShortReview_Label"));
        grid.add(l,GridPaneFiller.X(),GridPaneFiller.Y());
        
        reviewText = new TextArea();
        reviewText.setPromptText(L(this,"Review_PromptText"));
        grid.add(reviewText, GridPaneFiller.X(), GridPaneFiller.Y());
    }
    
    private void initButtonBar() {
        initEditButton();
        initSubmitButton();
        
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(5));
        
        FlowPane buttonPane = new FlowPane();
        buttonPane.setAlignment(Pos.CENTER);
        buttonPane.setHgap(10);
        buttonPane.getChildren().add(editReviewButton);
        buttonPane.getChildren().add(submitReviewButton);
        
        borderPane.setCenter(buttonPane);
        
        container.getChildren().add(borderPane);
    }

    private void initEditButton() {
        editReviewButton = new Button(L(this,"Edit_Button"));
        editReviewButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                setEditMode();
            }
        });
    }

    private void initSubmitButton() {
        submitReviewButton = new Button(L(this,"Save_Button"));
        submitReviewButton.setDisable(true);
        submitReviewButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                setNoEditMode();
                
                if (validate()) {
                    updateApplicationReview();
                    DialogFactory.showInformationDialog(L(this,"ThankYou_Dialog"), L(this,"ThankYou_Text"));
                    setNoEditMode();
                }
            }

        });
    }
    
    private void setEditMode() {
        stars.setDisable(false);
        reviewText.setDisable(false);
        editReviewButton.setDisable(true);
        submitReviewButton.setDisable(false);
    }
    
    public void setNoEditMode() {
        stars.setDisable(true);
        reviewText.setDisable(true);
        editReviewButton.setDisable(false);
        submitReviewButton.setDisable(true);
    }
        
    @Override
    public Node getNode() {
        return container;
    }

    /**
     * Gets the current ApplicationReview.
     * 
     * @return The ApplicationReview built from the values submitted using this panel
     */
    public ApplicationReview getApplicationReview() {
        ApplicationReview review = new ApplicationReview();
        review.setStars(stars.getSelectionModel().getSelectedIndex());
        review.setReviewText(reviewText.getText());
        return review;
    }
    
    /**
     * Sets the value inside this panel using the provided ApplicationReview.
     * 
     * @param review An instance of ApplicationReview used to set up values inside this panel.
     */
    public void setApplicationReview(ApplicationReview review) {
        stars.getSelectionModel().clearAndSelect(review.getStars());
        reviewText.setText(review.getReviewText());
        
        if (review.getReviewText()==null || review.getReviewText().equals(""))
            setEditMode();
        else
            setNoEditMode();
    }
    
    private boolean validate() {
        if (stars.getSelectionModel().getSelectedIndex()>=0 && !reviewText.getText().equals(""))
            return true;
        else {
            DialogFactory.showErrorDialog(L(this,"Error_Dialog"), L(this,"Error_Text"));
            return false;
        }
    }
    
    private void updateApplicationReview() {
        ApplicationReview review = getApplicationReview();
        FeedbackDAO.getInstance().save(review);
        SuggestionReporter rep = new SuggestionReporter();
        rep.updateApplicationReview(review);
    }
        
}
