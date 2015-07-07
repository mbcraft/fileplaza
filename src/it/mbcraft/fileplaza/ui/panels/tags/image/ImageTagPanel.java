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

package it.mbcraft.fileplaza.ui.panels.tags.image;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

/**
 * This panel provides image tagging capabilities. Can support different
 * tagging specifications.
 * 
 * TO BE IMPLEMENTED
 *
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class ImageTagPanel implements INodeProvider {

    private BorderPane pane;
    private final StringProperty imageTagSpecificationProperty = new SimpleStringProperty();
    
    public ImageTagPanel() {
        
    }
    
    /**
     * The image tag specification to use for tagging the image.
     * @return 
     */
    public StringProperty imageTagSpecificationProperty() {
        return imageTagSpecificationProperty;
    }
    
    /**
     * Sets the current image tag specification.
     * 
     * @param specName 
     */
    public void setCurrentImageTagSpecification(String specName) {
        imageTagSpecificationProperty.set(specName);
    }
    
    /**
     * Gets the current image tag specification.
     * @return 
     */
    public String getCurrentImageTagSpecification() {
        return imageTagSpecificationProperty.get();
    }
    
    /**
     * Returns the panel
     * 
     * @return The panel as a JavaFX Node instance
     */
    @Override
    public Node getNode() {
        return pane;
    }
    
}
