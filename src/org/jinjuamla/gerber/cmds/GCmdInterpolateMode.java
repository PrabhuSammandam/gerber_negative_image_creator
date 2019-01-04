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
public class GCmdInterpolateMode extends GCmd {

    private int _interpolation;

    public GCmdInterpolateMode( int interpolation ) {
        this._interpolation = interpolation;
    }

    public int getInterpolation() {
        return _interpolation;
    }

    public void setInterpolation( int interpolation ) {
        this._interpolation = interpolation;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState) {
        gerberState.setInterpolateMode( _interpolation );
        return null;
    }

}
