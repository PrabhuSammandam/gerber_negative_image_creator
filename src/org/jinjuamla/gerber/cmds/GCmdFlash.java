/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getLogger;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;

/**
 *
 * @author psammand
 */
public class GCmdFlash extends GCmd {

    private Double _x;
    private Double _y;

    public GCmdFlash( Double x, Double y ) {
        this._x = x;
        this._y = y;
    }

    public Double getY() {
        return _y;
    }

    public void setY( Double y ) {
        this._y = y;
    }

    public Double getX() {
        return _x;
    }

    public void setX( Double x ) {
        this._x = x;
    }

    @Override
    public void setOffset( double x, double y ) {
        if ( _x != null ) {
            _x += x;
        }

        if ( _y != null ) {
            _y += y;
        }
    }

    @Override
    public void invert( boolean isInvertX, boolean isInvertY ) {
        if ( isInvertX && _x != null ) {
            _x *= -1;
        }

        if ( isInvertY && _y != null ) {
            _y *= -1;
        }
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState ) {
        Geometry primitive = null;
        double x = (this._x == null) ? gerberState.getX() : ( double ) this._x;
        double y = (this._y == null) ? gerberState.getY() : ( double ) this._y;

        Geometry currentAperture = gerberState.getCurrentAperture();

        if ( currentAperture != null ) {
            try {
                primitive = currentAperture.clone();
                primitive.setLevelPolarity( gerberState.getLevelPolarity() );
                primitive.setUnits( gerberState.getUnitMode() );
                primitive.setPosition( new Coordinate( x, y ) );
            } catch ( CloneNotSupportedException ex ) {
                getLogger( GCmdFlash.class.getName() ).log(SEVERE, null, ex );
            }
        }
        gerberState.setX( x );
        gerberState.setY( y );

        Geometry[] ret = new Geometry[ 1 ];
        ret[ 0 ] = primitive;
        return ret;
    }

}
