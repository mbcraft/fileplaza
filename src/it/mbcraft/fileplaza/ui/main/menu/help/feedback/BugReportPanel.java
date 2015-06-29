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

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.i18n.LangResource;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import it.mbcraft.fileplaza.ui.common.helpers.GridPaneFiller;
import it.mbcraft.fileplaza.ui.dialogs.DialogFactory;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * This class creates a panel for reporting bugs.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
@LangResource("main.menu.help.feedback.BugReportPanel")
public class BugReportPanel implements INodeProvider {

    public static final String PUBLIC_BUG_PAGE = "http://www.fileplaza.net/known_issues.php";
    
    private static String[] BUG_TYPES;
    
    private final VBox container = new VBox();
    private final GridPane grid = new GridPane();
    
    private TextField title;
    private ComboBox type;
    private TextArea description;
    private TextField attachment1;
    private TextField attachment2;
    private TextField attachment3;
        
    /**
     * Creates the panel. No parameters are needed.
     */
    public BugReportPanel() { 
        BUG_TYPES = new String[] {L(this,"1bug_Choice"),L(this,"2bug_Choice"),L(this,"3bug_Choice"),L(this,"4bug_Choice"),L(this,"5bug_Choice")};
        initGrid();
        initButtonBar();   
    }
    
    private void initGrid() {
        GridPaneFiller.reset(2);
        grid.setPadding(new Insets(5));
        grid.setHgap(5);
        grid.setVgap(5);
        
        initCheckBeforeSubmit();
        initBugType();
        initTitle();
        initDescription();
        initAttachment1();
        initAttachment2();
        initAttachment3();
        
        container.getChildren().add(grid);
    }
    
    private void initCheckBeforeSubmit() {
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        pane.setHgap(10);
        pane.setPadding(new Insets(5));
        pane.setStyle("-fx-border:1;-fx-border-color:black;");
        Label checkBeforeSubmit = new Label(L(this,"CheckBeforeSubmit_Label"));
        checkBeforeSubmit.setFont(Font.font("Verdena", FontWeight.NORMAL, 14));
        pane.getChildren().add(checkBeforeSubmit);
        Hyperlink link = new Hyperlink(PUBLIC_BUG_PAGE);
        link.setFont(Font.font("Verdena", FontWeight.BOLD, 14));
        link.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                try {
                    Desktop.getDesktop().browse(new URI(PUBLIC_BUG_PAGE));
                } catch (IOException | URISyntaxException ex) {
                    Logger.getLogger(BugReportPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pane.getChildren().add(link);
        
        container.getChildren().add(ComponentFactory.newPaddingPane(pane,20));
    }
    
    private void initTitle() {
        //title
        Label titleLabel = new Label(L(this,"BugTitle_Label"));
        grid.add(titleLabel,GridPaneFiller.X(),GridPaneFiller.Y());
        title = new TextField();
        title.setPromptText(L(this,"BugTitle_PromptText"));
        grid.add(title,GridPaneFiller.X(),GridPaneFiller.Y());
    }
    
    private void initDescription() {
        //description
        Label descriptionLabel = new Label(L(this,"BugDescription_Label"));
        descriptionLabel.setWrapText(true);
        grid.add(descriptionLabel,GridPaneFiller.X(),GridPaneFiller.Y());
        description = new TextArea();
        description.setPromptText(L(this,"BugDescription_PromptText"));
        description.setPrefColumnCount(20);
        description.setPrefRowCount(10);
        grid.add(description,GridPaneFiller.X(),GridPaneFiller.Y());
    }
    
    private void initBugType() {
        //bug type
        Label typeLabel = new Label(L(this,"BugType_Label"));
        grid.add(typeLabel,GridPaneFiller.X(),GridPaneFiller.Y());
        type = new ComboBox();
        type.getItems().addAll(Arrays.asList(BUG_TYPES));
        type.setPromptText(L(this,"BugType_PromptText"));
        grid.add(type,GridPaneFiller.X(),GridPaneFiller.Y());
    }
    
    private void initAttachment1() {
        //attachment 1
        Label attachment1Label = new Label(L(this,"Attachment1_Label"));
        grid.add(attachment1Label,GridPaneFiller.X(),GridPaneFiller.Y());
        
        FlowPane pathAndBrowse = new FlowPane();
        
        attachment1 = new TextField();
        attachment1.setDisable(true);
        attachment1.setPromptText(L(this,"AttachmentField_PromptText"));
        
        pathAndBrowse.getChildren().add(attachment1);
        pathAndBrowse.getChildren().add(ComponentFactory.newFileBrowseButton(L(this,"Browse_Button"),attachment1,attachment1));
        
        grid.add(pathAndBrowse,GridPaneFiller.X(),GridPaneFiller.Y());
    }
    
    private void initAttachment2() {
        //attachment 2
        Label attachment2Label = new Label(L(this,"Attachment2_Label"));
        grid.add(attachment2Label,GridPaneFiller.X(),GridPaneFiller.Y());
        
        FlowPane pathAndBrowse = new FlowPane();
        
        attachment2 = new TextField();
        attachment2.setDisable(true);
        attachment2.setPromptText(L(this,"AttachmentField_PromptText"));
                
        pathAndBrowse.getChildren().add(attachment2);
        pathAndBrowse.getChildren().add(ComponentFactory.newFileBrowseButton(L(this,"Browse_Button"),attachment2,attachment2));
        
        grid.add(pathAndBrowse,GridPaneFiller.X(),GridPaneFiller.Y());
    }
    
    private void initAttachment3() {
        //attachment 3
        Label attachment3Label = new Label(L(this,"Attachment3_Label"));
        grid.add(attachment3Label,GridPaneFiller.X(),GridPaneFiller.Y());
        
        FlowPane pathAndBrowse = new FlowPane();
        
        attachment3 = new TextField();
        attachment3.setDisable(true);
        attachment3.setPromptText(L(this,"AttachmentField_PromptText"));
                
        pathAndBrowse.getChildren().add(attachment3);
        pathAndBrowse.getChildren().add(ComponentFactory.newFileBrowseButton(L(this,"Browse_Button"),attachment3,attachment3));
        
        grid.add(pathAndBrowse,GridPaneFiller.X(),GridPaneFiller.Y());
    }
    
    private void initButtonBar() {
        BorderPane buttonPane = new BorderPane();
        buttonPane.setPadding(new Insets(5));
        
        Button submitButton = new Button(L(this,"Submit_Button"));
        submitButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (validate()) {
                    reportBug();
                    clearAllFields();
                    DialogFactory.showInformationDialog(L(this,"ThankYou_Dialog"), L(this,"ThankYou_Text"));
                }
            }
        });
        buttonPane.setCenter(submitButton);
        
        container.getChildren().add(buttonPane);
    }
       
    private boolean validate() {
        if (type.getSelectionModel().getSelectedIndex()>=0 && !title.getText().equals("") && !description.getText().equals(""))
            return true;
        else {
            DialogFactory.showErrorDialog(L(this,"Error_Dialog"), L(this,"Error_Text"));
            return false;
        }
    }
    
    @Override
    public Node getNode() {
        return container;
    }
        
    private void reportBug() {
        
    }
    
    private void clearAllFields() {
        title.setText("");
        description.setText("");
        type.getSelectionModel().clearSelection();
        attachment1.setText("");
        attachment2.setText("");
        attachment3.setText("");
    }
    
}
