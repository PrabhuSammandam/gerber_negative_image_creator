package org.jinjuamla.camfilelibrary;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class BoundingBox {

    private Double _min_x;
    private Double _min_y;
    private Double _max_x;
    private Double _max_y;

    public BoundingBox() {
        this( 0, 0, 0, 0 );
    }

    public BoundingBox( double minX, double minY, double maxX, double maxY ) {
        this._min_x = minX;
        this._min_y = minY;
        this._max_x = maxX;
        this._max_y = maxY;
    }

    public double getMaxX() {
        return _max_x;
    }

    public double getMaxY() {
        return _max_y;
    }

    public double getMinX() {
        return _min_x;
    }

    public double getMinY() {
        return _min_y;
    }

    public void setMaxX( double maxX ) {
        this._max_x = maxX;
    }

    public void setMaxY( double maxY ) {
        this._max_y = maxY;
    }

    public void setMinX( double minX ) {
        this._min_x = minX;
    }

    public void setMinY( double minY ) {
        this._min_y = minY;
    }

    public double getWidth() {
        if ( _min_x != null && _max_x != null ) {
            return _max_x - _min_x;
        }

        return 0;
    }

    public double getHeight() {
        if ( _min_y != null && _max_y != null ) {
            return _max_y - _min_y;
        }

        return 0;
    }

    public void normalize() {
        _min_x = floor( _min_x );
        _min_y = floor( _min_y );
        _max_x = ceil( _max_x );
        _max_y = ceil( _max_y );
    }

    public void grow( double vert, double horz ) {
        _min_x -= horz;
        _min_y -= vert;
        _max_x += horz;
        _max_y += vert;
    }

    public void updateLimits( BoundingBox otherBox ) {
        if ( otherBox != null ) {
            setMinX( min( otherBox.getMinX(), getMinX() ) );
            setMaxX( max( otherBox.getMaxX(), getMaxX() ) );
            setMinY( min( otherBox.getMinY(), getMinY() ) );
            setMaxY( max( otherBox.getMaxY(), getMaxY() ) );
        }
    }

    public void setMinMax( Coordinate position, double xDelta, double yDelta ) {
        _min_x = position.getX() - xDelta;
        _max_x = position.getX() + xDelta;
        _min_y = position.getY() - yDelta;
        _max_y = position.getY() + yDelta;

    }

    public boolean isValid() {
        return !(_min_x == Double.POSITIVE_INFINITY || _min_y == Double.POSITIVE_INFINITY || _max_x == Double.NEGATIVE_INFINITY || _max_y == Double.NEGATIVE_INFINITY);
    }

    /**
     * Offsets the xaxis value to minX and yaxis value to minY. This function
     * moves the box based on the minX and minY
     *
     * @param xaxis
     * @param yaxis
     */
    public void offset( double xaxis, double yaxis ) {
        if ( xaxis == 0 || yaxis == 0 ) {
        }
    }

    @Override
    public String toString() {
        return String.format( "BBox = { MinX = %f, MinY = %f, MaxX = %f, MaxY = %f }", _min_x, _min_y, _max_x, _max_y );
    }

}
