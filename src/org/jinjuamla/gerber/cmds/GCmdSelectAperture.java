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
public class GCmdSelectAperture extends GCmd {

    private int _apertureCode;

    public GCmdSelectAperture( int apertureCode ) {
        this._apertureCode = apertureCode;
    }

    public int getApertureCode() {
        return _apertureCode;
    }

    public void setApertureCode( int apertureCode ) {
        this._apertureCode = apertureCode;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState) {
        gerberState.setCurrentApertureCode( _apertureCode );
        
        return null;
    }

}
