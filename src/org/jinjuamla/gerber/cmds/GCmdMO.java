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
public class GCmdMO extends GCmd {

    private int _unitMode;

    public GCmdMO( int unitMode ) {
        this._unitMode = unitMode;
    }

    public int getUnitMode() {
        return _unitMode;
    }

    public void setUnitMode( int unitMode ) {
        this._unitMode = unitMode;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState) {
        gerberState.setUnitMode( _unitMode );
        
        return null;
    }

}
