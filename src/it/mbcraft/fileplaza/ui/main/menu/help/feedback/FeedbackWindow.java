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

package it.mbcraft.fileplaza.ui.main.menu.help.feedback;

import it.mbcraft.fileplaza.data.dao.config.FeedbackDAO;
import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.windows.AbstractPresenterWindow;
import it.mbcraft.fileplaza.ui.common.components.misc.ImprovedStackPane;
import java.util.Arrays;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

/**
 * Creates the FeedbackWindow, with all its panels.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.feedback.FeedbackWindow")
public class FeedbackWindow extends AbstractPresenterWindow {
    
    public static final double FIRST_COLUMN_MAX_WIDTH = 300;
    public static final double SECOND_COLUMN_MAX_WIDTH = 500;
    
    private ComboBox selector;
    
    private ImprovedStackPane stackableSelector;
    
    private ThankYouPanel thankYouPanel;
    private ApplicationReviewPanel applicationReviewPanel;
    private SuggestAFeaturePanel suggestAFeaturePanel;
    private BugReportPanel bugReportPanel;
    
    /**
     * Creates the 'feedback' window.
     */
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
                stackableSelector.selectedPanelIndexProperty().setValue(selector.getSelectionModel().getSelectedIndex()+1);
            }
        
        });
        selector.setPrefWidth(200);
        container.setCenter(selector);
        
        addToWindow(container);
    }

    private void initStackPane() {
        stackableSelector = new ImprovedStackPane();
        stackableSelector.setAlignment(Pos.CENTER);
        stackableSelector.setPadding(new Insets(5));
        
        initThankYouPane();
        initSuggestAFeaturePanel();
        initReviewPanel();
        initBugReportPanel();

        addToWindow(stackableSelector);
    }
        
    private void initThankYouPane() {
        thankYouPanel = new ThankYouPanel();
        stackableSelector.getChildren().add(thankYouPanel.getNode());
    }
    
    private void initSuggestAFeaturePanel() {
        suggestAFeaturePanel = new SuggestAFeaturePanel();
        stackableSelector.getChildren().add(suggestAFeaturePanel.getNode());
    }
    
    private void initBugReportPanel() {
        bugReportPanel = new BugReportPanel();
        stackableSelector.getChildren().add(bugReportPanel.getNode());

    }
    
    private void initReviewPanel() {
        applicationReviewPanel = new ApplicationReviewPanel();
        stackableSelector.getChildren().add(applicationReviewPanel.getNode());
    }
    
    @Override
    protected void reset() {
        selector.getSelectionModel().clearSelection();
        applicationReviewPanel.setApplicationReview(FeedbackDAO.getInstance().load());
        stackableSelector.selectedPanelIndexProperty().setValue(0);
    }    
}
