package org.jinjuamla.camfilelibrary;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;
import static org.jinjuamla.camfilelibrary.Consts.LEVEL_POLARITY_NONE;
import static org.jinjuamla.camfilelibrary.Consts.UNIT_MODE_INCH;
import static org.jinjuamla.camfilelibrary.Consts.UNIT_MODE_MM;

public abstract class Geometry {

    protected int _levelPolarity;
    protected double _rotation;
    protected int _units;
    protected double _holeDiameter;
    protected Coordinate _position;

    protected BoundingBox _boundingBox;

    public Geometry() {
        this._levelPolarity = LEVEL_POLARITY_NONE;
        this._rotation = 0;
        this._units = UNIT_MODE_MM;
        this._holeDiameter = 0;
        this._position = null;
        this._boundingBox = null;
    }

    public Geometry( Geometry other ) {
        this._levelPolarity = other._levelPolarity;
        this._rotation = other._rotation;
        this._units = other._units;
        this._holeDiameter = other._holeDiameter;
        this._position = other._position;
        this._boundingBox = null;
    }

    public Coordinate getPosition() {
        return _position;
    }

    public void setPosition( Coordinate position ) {
        this._position = position;
    }

    public double CosTheta() {
        return cos( toRadians( _rotation ) );
    }

    public double SinTheta() {
        return sin( toRadians( _rotation ) );
    }

    public double getHoleDiameter() {
        return _holeDiameter;
    }

    public void setHoleDiameter( double holeDiameter ) {
        this._holeDiameter = holeDiameter;
        update();
    }

    public int getLevelPolarity() {
        return _levelPolarity;
    }

    public void setLevelPolarity( int levelPolarity ) {
        this._levelPolarity = levelPolarity;
        update();
    }

    /**
     * Returns the lower-left (min x & y) and upper-right (max x & y)
     * co-ordinates as bounding box.
     *
     * @return
     */
    public abstract BoundingBox getBounds();

    protected void update() {
        _boundingBox = null;
    }

    protected final void updateInternal() {
        update();
    }

    public int getUnits() {
        return _units;
    }

    public void setUnits( int units ) {
        this._units = units;
        update();
    }

    public double getRotateAngle() {
        return _rotation;
    }

    public void setRotateAngle( double rotation ) {
        this._rotation = rotation;
        update();
    }

    public double getX() {
        if ( _position != null ) {
            return _position.getX();
        }
        return 0;
    }

    public double getY() {
        if ( _position != null ) {
            return _position.getY();
        }
        return 0;
    }

    public void toInch() {
        if ( _units != Consts.UNIT_MODE_INCH ) {
            doUpdateValues( 1 / 25.4 );
            update();
        }
    }

    public void toMM() {
        if ( _units != Consts.UNIT_MODE_MM ) {
            doUpdateValues( 25.4 );
            update();
        }
    }

    public void move( double offset ) {
        if ( _position != null ) {
            _position.setX( offset + _position.getX() );
            _position.setY( offset + _position.getY() );
        }
    }

    protected void doUpdateValues( double value ) {
        _holeDiameter *= value;

        if ( _position != null ) {
            _position.setX( value * _position.getX() );
            _position.setY( value * _position.getY() );
        }
    }

    public double mmToInch( double mm ) {
        return mm / 25.4;
    }

    public double inchToMm( double inch ) {
        return inch * 25.4;
    }

    @Override
    public Geometry clone() throws CloneNotSupportedException {
        return null;
    }

    @Override
    public String toString() {
        if ( _position != null ) {
            return String.format( "X=%f, Y=%f, Unit=%s", _position.getX(), _position.getY(), (_units == UNIT_MODE_INCH) ? "Inch" : "MM" );
        } else {
            return String.format( "x=null, y=null, unit=%d", _units );
        }
    }

}
