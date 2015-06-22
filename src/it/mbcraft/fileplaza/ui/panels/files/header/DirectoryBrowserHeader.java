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

package it.mbcraft.fileplaza.ui.panels.files.header;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.state.CurrentDirectoryState;
import it.mbcraft.fileplaza.ui.main.browse.path.CurrentPathPanel;
import it.mbcraft.fileplaza.ui.main.browse.path.DirectoryBrowserCommandsPanelProvider;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * TODO : define what this component does.
 * 
 * @author Marco Bagnaresi <marco.bagnaresi@gmail.com>
 */
public class DirectoryBrowserHeader implements INodeProvider {

    private final CurrentDirectoryState _currentDirectoryState;
    
    private final CurrentPathPanel _currentPathPanel;
    
    //i comandi vanno isolati anche loro perchè potrebbero dipendere dalla
    //cartella corrente
    private final DirectoryBrowserCommandsPanelProvider _browseCommandsPanel;
    
    private final VBox _container;
    
    public DirectoryBrowserHeader(CurrentDirectoryState state) {
        
        _container = new VBox();
        
        _currentDirectoryState = state;
        
        _currentPathPanel = new CurrentPathPanel(null,_currentDirectoryState);
        _browseCommandsPanel = new DirectoryBrowserCommandsPanelProvider(_currentDirectoryState);
        
        
        _container.getChildren().addAll(_currentPathPanel.getNode(),_browseCommandsPanel.getNode());
    }
    
    @Override
    public Node getNode() {
        return _container;
    }
    
}
