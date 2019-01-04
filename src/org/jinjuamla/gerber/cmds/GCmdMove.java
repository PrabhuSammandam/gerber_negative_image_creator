/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import static org.jinjuamla.camfilelibrary.Consts.REGION_MODE_ON;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;
import org.jinjuamla.gerber.common.Region;


/**
 *
 * @author psammand
 */
public class GCmdMove extends GCmd {

    private Double _x;
    private Double _y;

    public GCmdMove( Double x, Double y ) {
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
        Geometry returnPrimitive = null;

        double x = (this._x == null) ? gerberState.getX() : ( double ) this._x;
        double y = (this._y == null) ? gerberState.getY() : ( double ) this._y;

        if ( gerberState.getRegionMode() == REGION_MODE_ON ) {
            /* if current is region is null then D02 is the first statement
            after the region on statement. So create a new region.*/
            Region currentRegion = gerberState.getCurrentRegion();
            if ( currentRegion == null ) {
                gerberState.setCurrentRegion( new Region( new Coordinate( x, y ) ) );
            } else {
                /* Current region is not null so the region is already created by the D01 statement.
                    Check if the previous created region is closed. If closed then create a new region and append
                    the current region to region list. If it is not closed the discard the current region and
                    start with new region */
                if ( currentRegion.isClosed() ) {
                    gerberState.getRegionList().add( currentRegion );
                }

                gerberState.setCurrentRegion( new Region( new Coordinate( x, y ) ) );
            }
        }
        gerberState.setX( x );
        gerberState.setY( y );

        Geometry[] ret = new Geometry[ 1 ];
        ret[ 0 ] = returnPrimitive;
        return ret;
    }

}
