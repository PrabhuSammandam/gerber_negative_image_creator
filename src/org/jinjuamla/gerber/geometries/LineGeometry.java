/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.geometries;

import static java.lang.Math.max;
import static java.lang.Math.min;
import org.jinjuamla.camfilelibrary.BoundingBox;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;

/**
 *
 * @author psammand
 */
public class LineGeometry extends Geometry {

    private Coordinate _startPt;
    private Coordinate _endPt;
    private Geometry _strokeGObj;

    public LineGeometry( Coordinate startPt, Coordinate endPt, Geometry strokeGObj ) {
        this._startPt = startPt;
        this._endPt = endPt;
        this._strokeGObj = strokeGObj;
    }

    public Geometry getStrokeGObj() {
        return _strokeGObj;
    }

    public void setStrokeGObj( Geometry strokeGObj ) {
        this._strokeGObj = strokeGObj;
    }

    public Coordinate getEndPt() {
        return _endPt;
    }

    public void setEndPt( Coordinate endPt ) {
        this._endPt = endPt;
    }

    public Coordinate getStartPt() {
        return _startPt;
    }

    public void setStartPt( Coordinate startPt ) {
        this._startPt = startPt;
    }

    public double getAngle() {
        return _endPt.angle( _startPt );
    }

    @Override
    protected void update() {
        super.update();

    }

    @Override
    protected void doUpdateValues( double value ) {
        super.doUpdateValues( value );
        _startPt.setX( value * _startPt.getX() );
        _startPt.setY( value * _startPt.getY() );

        _endPt.setX( value * _endPt.getX() );
        _endPt.setY( value * _endPt.getY() );

        if ( _strokeGObj instanceof CircleGeometry ) {
            (( CircleGeometry ) _strokeGObj).doUpdateValues( value );
        }
    }

    @Override
    public BoundingBox getBounds() {
        if ( _boundingBox == null ) {
            double width2 = 0.0;
            double height2 = 0.0;

            if ( _strokeGObj != null && _strokeGObj instanceof CircleGeometry ) {
                width2 = (( CircleGeometry ) _strokeGObj).radius();
                height2 = width2;
            } else if ( _strokeGObj != null && _strokeGObj instanceof RectGeometry ) {
                width2 = (( RectGeometry ) _strokeGObj).getWidth() / 2;
                height2 = (( RectGeometry ) _strokeGObj).getHeight() / 2;
            }
            double min_x = min( _startPt.getX(), _endPt.getX() - width2 );
            double max_x = max( _startPt.getX(), _endPt.getX() + width2 );
            double min_y = min( _startPt.getY(), _endPt.getY() - height2 );
            double max_y = max( _startPt.getY(), _endPt.getY() + height2 );

            _boundingBox = new BoundingBox( min_x, min_y, max_x, max_y );
        }

        return _boundingBox;
    }

    @Override
    public String toString() {
        String stokeStr = "";
        if ( _strokeGObj != null && _strokeGObj instanceof CircleGeometry ) {
            stokeStr = String.format( "Circle: dia=%f", (( CircleGeometry ) _strokeGObj).getDiameter() );
        } else if ( _strokeGObj != null && _strokeGObj instanceof RectGeometry ) {
            RectGeometry rect = ( RectGeometry ) _strokeGObj;
            stokeStr = String.format( "Rect: w=%f, h=%f", rect.getWidth(), rect.getHeight() );
        }

        return String.format( "Line { startX=%f, startY=%f, endX=%f, endY=%f LineWidth[%s] %s }", _startPt.getX(), _startPt.getY(), _endPt.getX(), _endPt.getY(), stokeStr, super.toString() );
    }

}
