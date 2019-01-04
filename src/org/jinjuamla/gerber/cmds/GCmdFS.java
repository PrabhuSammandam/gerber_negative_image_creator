/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.camfilelibrary.NumberFormat;
import org.jinjuamla.gerber.GraphicsState;


/**
 *
 * @author psammand
 */
public class GCmdFS extends GCmd {

    private int _zeroOmision;
    private int _notation;
    private NumberFormat _numberFormat;

    public GCmdFS( int zeroOmision, int notation, NumberFormat numberFormat ) {
        this._zeroOmision = zeroOmision;
        this._notation = notation;
        this._numberFormat = numberFormat;
    }

    public int getNotation() {
        return _notation;
    }

    public void setNotation( int notation ) {
        this._notation = notation;
    }

    public NumberFormat getNumberFormat() {
        return _numberFormat;
    }

    public void setNumberFormat( NumberFormat numberFormat ) {
        this._numberFormat = numberFormat;
    }

    public int getZeroOmision() {
        return _zeroOmision;
    }

    public void setZeroOmision( int zeroOmision ) {
        this._zeroOmision = zeroOmision;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState) {
        gerberState.setZeroOmision( _zeroOmision );
        gerberState.setNotation( _notation );
        gerberState.setNumberFormat( _numberFormat );
        
        return null;
    }

}
