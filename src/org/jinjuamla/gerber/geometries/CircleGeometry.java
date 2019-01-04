/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.geometries;

import org.jinjuamla.camfilelibrary.BoundingBox;
import org.jinjuamla.camfilelibrary.Geometry;


/**
 *
 * @author psammand
 */
public class CircleGeometry extends Geometry {

    private double _diameter;

    public CircleGeometry( double diameter ) {
        this._diameter = diameter;
        this._holeDiameter = 0;

        updateInternal();
    }

    public CircleGeometry( double diameter, double holeDiameter ) {
        this._diameter = diameter;
        this._holeDiameter = holeDiameter;

        updateInternal();
    }

    public CircleGeometry( CircleGeometry other ) {
        super( other );
        this._diameter = other._diameter;
        this._holeDiameter = other._holeDiameter;

        updateInternal();
    }

    public double getDiameter() {
        return _diameter;
    }

    public void setDiameter( double diameter ) {
        this._diameter = diameter;
    }

    public double radius() {
        return _diameter / 2;
    }

    @Override
    protected void doUpdateValues( double value ) {
        super.doUpdateValues( value );
        _diameter *= value;
    }

    @Override
    public BoundingBox getBounds() {
        if ( _boundingBox == null ) {
            _boundingBox = new BoundingBox();
            _boundingBox.setMinMax( _position, radius(), radius() );
        }
        return _boundingBox;
    }

    @Override
    public Geometry clone() throws CloneNotSupportedException {
        CircleGeometry prim = new CircleGeometry( this );

        return prim;
    }

    @Override
    public String toString() {
        return String.format( "Circle { dia=%f, %s }", _diameter, super.toString() );
    }

}
