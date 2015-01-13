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

import it.mbcraft.fileplaza.data.dao.config.FeedbackDAO;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractPresenterWindow;
import it.mbcraft.fileplaza.ui.common.helpers.StackablePanelSelector;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.feedback.FeedbackWindow")
public class FeedbackWindow extends AbstractPresenterWindow {
    
    public static final double FIRST_COLUMN_MAX_WIDTH = 300;
    public static final double SECOND_COLUMN_MAX_WIDTH = 500;
    
    private ComboBox selector;
    private StackPane stackPane;
    
    private StackablePanelSelector stackableSelector;
    
    private ThankYouPanel thankYouPanel;
    private ApplicationReviewPanel applicationReviewPanel;
    private SuggestAFeaturePanel suggestAFeaturePanel;
    private BugReportPanel bugReportPanel;
    
    public FeedbackWindow() {
        super(L("main.menu.help.feedback.FeedbackWindow","Feedback_Window"));
    }
    
    @Override
    protected void initMiddleContent() {
        initSelector();
        initStackPane();
    }
    
    private void initSelector() {
        BorderPane container = new BorderPane();
        container.setPadding(new Insets(5));
        
        selector = new ComboBox();
        selector.getItems().addAll(Arrays.asList(L(this,"SuggestFeature_Choice"),L(this,"WriteReview_Choice"),L(this,"ReportBug_Choice")));
        selector.setPromptText(L(this,"Chooser_PromptText"));
        selector.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                stackableSelector.select(selector.getSelectionModel().getSelectedIndex()+1);
            }
        
        });
        selector.setPrefWidth(200);
        container.setCenter(selector);
        addToWindow(container);
    }

    private void initStackPane() {
        
        stackableSelector = new StackablePanelSelector();
        
        stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER);
        stackPane.setPadding(new Insets(5));
        
        initThankYouPane(stackPane);
        initSuggestAFeaturePanel(stackPane);
        initReviewPanel(stackPane);
        initBugReportPanel(stackPane);

        addToWindow(stackPane);
    }
        
    private void initThankYouPane(Pane parent) {
        thankYouPanel = new ThankYouPanel();
        stackableSelector.add(thankYouPanel);
        parent.getChildren().add(thankYouPanel.getNode());
    }
    
    private void initSuggestAFeaturePanel(Pane parent) {
        suggestAFeaturePanel = new SuggestAFeaturePanel();
        stackableSelector.add(suggestAFeaturePanel);
        parent.getChildren().add(suggestAFeaturePanel.getNode());
    }
    
    private void initBugReportPanel(Pane parent) {
        bugReportPanel = new BugReportPanel();
        stackableSelector.add(bugReportPanel);
        parent.getChildren().add(bugReportPanel.getNode());
    }
    
    private void initReviewPanel(Pane parent) {
        applicationReviewPanel = new ApplicationReviewPanel();
        stackableSelector.add(applicationReviewPanel);
        parent.getChildren().add(applicationReviewPanel.getNode()); 
    }
    
    @Override
    protected void reset() {
        selector.getSelectionModel().clearSelection();
        stackableSelector.select(0);
        applicationReviewPanel.setApplicationReview(FeedbackDAO.getInstance().load());
    }    
}
