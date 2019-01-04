/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;


/**
 *
 * @author psammand
 */
public class GCmdQuadrantMode extends GCmd {

    private int _quadrantMode;

    public GCmdQuadrantMode( int quadrantMode ) {
        this._quadrantMode = quadrantMode;
    }

    public int getQuadrantMode() {
        return _quadrantMode;
    }

    public void setQuadrantMode( int quadrantMode ) {
        this._quadrantMode = quadrantMode;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState) {
        gerberState.setQuadrantMode( _quadrantMode );
        
        return null;
    }

}
