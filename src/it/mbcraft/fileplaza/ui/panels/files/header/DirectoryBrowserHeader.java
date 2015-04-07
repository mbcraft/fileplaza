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

package it.mbcraft.fileplaza.ui.panels.files.header;

import it.mbcraft.fileplaza.ui.common.components.INodeProvider;
import it.mbcraft.fileplaza.ui.main.browse.CurrentDirectoryState;
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
        
        _currentPathPanel = new CurrentPathPanel(_currentDirectoryState);
        _browseCommandsPanel = new DirectoryBrowserCommandsPanelProvider(_currentDirectoryState);
        
        
        _container.getChildren().addAll(_currentPathPanel.getNode(),_browseCommandsPanel.getNode());
    }
    
    @Override
    public Node getNode() {
        return _container;
    }
    
}
