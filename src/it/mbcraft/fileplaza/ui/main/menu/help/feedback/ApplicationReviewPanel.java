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

    public ApplicationReview getApplicationReview() {
        ApplicationReview review = new ApplicationReview();
        review.setStars(stars.getSelectionModel().getSelectedIndex());
        review.setReviewText(reviewText.getText());
        return review;
    }
    
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
