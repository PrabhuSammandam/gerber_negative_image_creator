/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import static org.jinjuamla.camfilelibrary.Consts.INTERPOLATION_MODE_LINEAR;
import static org.jinjuamla.camfilelibrary.Consts.REGION_MODE_OFF;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;
import org.jinjuamla.gerber.common.Region;
import org.jinjuamla.gerber.geometries.LineGeometry;


/**
 *
 * @author psammand
 */
public class GCmdInterpolate extends GCmd {

    private Double _x;
    private Double _y;
    private Double _i;
    private Double _j;

    public GCmdInterpolate( Double x, Double y, Double i, Double j ) {
        this._x = x;
        this._y = y;
        this._i = i;
        this._j = j;
    }

    public Double getJ() {
        return _j;
    }

    public void setJ( Double j ) {
        this._j = j;
    }

    public Double getI() {
        return _i;
    }

    public void setI( Double i ) {
        this._i = i;
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
        if ( _i != null ) {
            _i += x;
        }

        if ( _j != null ) {
            _j += y;
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

        Coordinate startPt = new Coordinate( gerberState.getX(), gerberState.getY() );
        Coordinate endPt = new Coordinate( x, y );

        if ( gerberState.getInterpolateMode() == INTERPOLATION_MODE_LINEAR ) {
            if ( gerberState.getRegionMode() == REGION_MODE_OFF ) {
                returnPrimitive = new LineGeometry( startPt, endPt, gerberState.getCurrentAperture() );
                returnPrimitive.setLevelPolarity( gerberState.getLevelPolarity() );
                returnPrimitive.setUnits( gerberState.getUnitMode() );
            } else if ( gerberState.getCurrentRegion() != null ) {
                /*add new segment*/
                gerberState.getCurrentRegion().getPoints().add( endPt );
            } else {
                /*create a region with current point*/
                gerberState.setCurrentRegion( new Region( startPt ) );
                /*create a new segment with current and new point*/
                gerberState.getCurrentRegion().getPoints().add( endPt );
            }
        }

        gerberState.setX( x );
        gerberState.setY( y );

        Geometry[] ret = new Geometry[ 1 ];
        ret[ 0 ] = returnPrimitive;
        return ret;
    }

}
