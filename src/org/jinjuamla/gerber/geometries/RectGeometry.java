package org.jinjuamla.gerber.geometries;

import org.jinjuamla.camfilelibrary.BoundingBox;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author psammand
 */
public class RectGeometry extends Geometry {

    private double _width;
    private double _height;

    public RectGeometry( double width, double height ) {
        this._width = width;
        this._height = height;
        this._holeDiameter = 0;

        updateInternal();
    }

    public RectGeometry( double width, double height, double holeDiameter ) {
        this._width = width;
        this._height = height;
        this._holeDiameter = holeDiameter;

        updateInternal();
    }

    public RectGeometry( RectGeometry other ) {
        this._width = other._width;
        this._height = other._height;
        this._holeDiameter = other._holeDiameter;

        updateInternal();
    }

    public double getHeight() {
        return _height;
    }

    public void setHeight( double height ) {
        this._height = height;
    }

    public double getWidth() {
        return _width;
    }

    public void setWidth( double width ) {
        this._width = width;
    }

    @Override
    public BoundingBox getBounds() {
        if ( _boundingBox == null ) {
            _boundingBox = new BoundingBox();
            _boundingBox.setMinMax( _position, axisAlignedWidth() / 2.0, axisAlignedHeight() / 2.0 );
        }
        return _boundingBox;
    }

    public Coordinate lowerLeft() {
        return new Coordinate( _position.getX() - axisAlignedWidth() / 2, _position.getY() - axisAlignedHeight() / 2 );
    }

    private double axisAlignedWidth() {
        return CosTheta() * _width + SinTheta() * _height;
    }

    private double axisAlignedHeight() {
        return CosTheta() * _height + SinTheta() * _width;
    }

    @Override
    protected void doUpdateValues( double value ) {
        super.doUpdateValues( value );
        _width *= value;
        _height *= value;
    }

    @Override
    public Geometry clone() throws CloneNotSupportedException {
        RectGeometry prim = new RectGeometry( this );

        return prim;
    }

    @Override
    public String toString() {
        return String.format( "Rect { W=%f, H=%f, %s }", _width, _height, super.toString() );
    }

}
