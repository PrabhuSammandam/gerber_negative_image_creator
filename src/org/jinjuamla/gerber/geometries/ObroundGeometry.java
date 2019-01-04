/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.geometries;

import org.jinjuamla.camfilelibrary.BoundingBox;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;

/**
 *
 * @author psammand
 */
public class ObroundGeometry extends Geometry {

    private double _width;
    private double _height;
    private CircleGeometry _circle1;
    private CircleGeometry _circle2;
    private RectGeometry _rectangle;

    public ObroundGeometry( double width, double height ) {
        this._width = width;
        this._height = height;
        this._holeDiameter = 0;

        updateInternal();
    }

    public ObroundGeometry( double width, double height, double holeDiameter ) {
        this._width = width;
        this._height = height;
        this._holeDiameter = holeDiameter;

        updateInternal();
    }

    public ObroundGeometry( ObroundGeometry other ) {
        this._width = other._width;
        this._height = other._height;
        this._holeDiameter = other._holeDiameter;

        updateInternal();
    }

    public RectGeometry getRectangle() {
        if ( _rectangle == null ) {
            if ( isVertical() ) {
                _rectangle = new RectGeometry( _width, _height - _width );
            } else {
                _rectangle = new RectGeometry( _width - _height, _height );
            }

            _rectangle.setLevelPolarity( getLevelPolarity() );
            _rectangle.setUnits( getUnits() );
            _rectangle.setPosition( new Coordinate( getPosition() ) );

        }
        return _rectangle;
    }

    public CircleGeometry getCircle1() {
        if ( _circle1 == null ) {
            _circle1 = new CircleGeometry( isVertical() ? _width : _height );

            double center = (_height - _width) / 2.0;

            if ( isVertical() ) {
                _circle1.setPosition( new Coordinate( getX(), getY() + center ) );
            } else {
                _circle1.setPosition( new Coordinate( getX() - center, getY() ) );
            }
            _circle1.setLevelPolarity( getLevelPolarity() );
            _circle1.setUnits( getUnits() );
        }

        return _circle1;
    }

    public CircleGeometry getCircle2() {
        if ( _circle2 == null ) {
            _circle2 = new CircleGeometry( isVertical() ? _width : _height );

            double center = (_height - _width) / 2.0;

            if ( isVertical() ) {
                _circle2.setPosition( new Coordinate( getX(), getY() - center ) );
            } else {
                _circle2.setPosition( new Coordinate( getX() + center, getY() ) );
            }
            _circle2.setLevelPolarity( getLevelPolarity() );
            _circle2.setUnits( getUnits() );
        }

        return _circle2;
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

    public boolean isVertical() {
        return _height > _width;
    }

    @Override
    protected void doUpdateValues( double value ) {
        super.doUpdateValues( value );
        _width *= value;
        _height *= value;
        getCircle1().doUpdateValues( value );
        getCircle2().doUpdateValues( value );
        getRectangle().doUpdateValues( value );
    }

    @Override
    public BoundingBox getBounds() {
        if ( _boundingBox == null ) {
            _boundingBox = new BoundingBox();
            _boundingBox.setMinMax( _position, axisAlignedWidth() / 2.0, axisAlignedHeight() / 2.0 );
        }
        return _boundingBox;
    }

    private double axisAlignedWidth() {
        return CosTheta() * _width + SinTheta() * _height;
    }

    private double axisAlignedHeight() {
        return CosTheta() * _height + SinTheta() * _width;
    }

    @Override
    public Geometry clone() throws CloneNotSupportedException {
        ObroundGeometry prim = new ObroundGeometry( this );

        return prim;
    }

    @Override
    public String toString() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append( String.format( "Obround { W=%f, H=%f,", _width, _height ) );
        if ( _circle1 != null ) {
            strBuilder.append( String.format( " C1={%s} ", _circle1.toString() ) );
        }
        if ( _circle2 != null ) {
            strBuilder.append( String.format( " C2={%s} ", _circle2.toString() ) );
        }
        if ( _rectangle != null ) {
            strBuilder.append( String.format( " R={%s} ", _rectangle.toString() ) );
        }
        strBuilder.append( String.format( " %s }", super.toString() ) );

        return strBuilder.toString();
    }

}
