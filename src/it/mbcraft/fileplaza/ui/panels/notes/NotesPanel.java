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

package it.mbcraft.fileplaza.ui.panels.notes;

import static it.mbcraft.fileplaza.i18n.Lang.L;
import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.common.helpers.ComponentFactory;
import java.io.File;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class NotesPanel implements INodeProvider {

    private final VBox box = new VBox();
    
    private final TextArea textArea = new TextArea();
    
    private Button clearButton;
    
    public NotesPanel(ObjectProperty<File> currentSelectedFileProperty,StringProperty notesProperty) {        
        initContainer();
        
        initContent();
        
        initButtons();
        
        initBindings(currentSelectedFileProperty,notesProperty);
    }
    
    private void initContainer() {
        box.setPadding(new Insets(5));
        box.setAlignment(Pos.TOP_LEFT);
    }
    
    private void initContent() {
        
        textArea.setPromptText(L(this,"Notes_PromptText"));
        textArea.setPrefColumnCount(20);
        textArea.setDisable(true);
        textArea.textProperty().addListener(new ChangeListener<String>(){

            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                textChanged();
            }
            
        });
        
        box.getChildren().add(ComponentFactory.newPaddingPane(textArea, 5));
        
    }
    
    private void textChanged() {
        String text = textArea.getText();
        
        if (text!=null && !text.equals("")) 
            clearButton.setDisable(false);
        else 
            clearButton.setDisable(true);
    }
    
    private void clearContent() {
        textArea.setText("");
        clearButton.setDisable(true);
        textArea.requestFocus();
    }
        
    private void initButtons() {
        FlowPane buttons = new FlowPane();
        buttons.setPadding(new Insets(5));
        buttons.setHgap(10);
        
        clearButton = new Button(L(this,"Clear_Button"));
        clearButton.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t) {
                clearContent();
            }
        
        });
        clearButton.setDisable(true);
        
        buttons.getChildren().add(clearButton);
        
        box.getChildren().add(buttons);
    }
    
    public String getNotes() {
        return textArea.getText();
    }
    
    public void setNotes(String text) {
        textArea.setText(text);
    }
    
    @Override
    public Node getNode() {
        return box;
    }

    private void initBindings(ObjectProperty<File> currentSelectedFileProperty,StringProperty notesProperty) {
        textArea.textProperty().bindBidirectional(notesProperty);
        
        currentSelectedFileProperty.addListener(new ChangeListener<File>(){

            @Override
            public void changed(ObservableValue<? extends File> ov, File oldValue, File newValue) {
                if (newValue==null)
                    textArea.setDisable(true);
                else
                    textArea.setDisable(false);
            }
        });
    }
    
}
