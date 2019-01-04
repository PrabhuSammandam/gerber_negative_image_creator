/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.camfilelibrary;

import static java.lang.Double.doubleToLongBits;
import static java.lang.Math.atan2;

/**
 *
 * @author psammand
 */
public class Coordinate {

    private double _x;
    private double _y;

    public Coordinate( double x, double y ) {
        this._x = x;
        this._y = y;
    }

    public Coordinate( Coordinate other ) {
        _x = other._x;
        _y = other._y;
    }

    public double getX() {
        return _x;
    }

    public void setX( double x ) {
        this._x = x;
    }

    public double getY() {
        return _y;
    }

    public void setY( double y ) {
        this._y = y;
    }

    public double angle( Coordinate secondPt ) {
        double deltaX = _x - secondPt._x;
        double deltaY = _y - secondPt._y;

        return atan2( deltaY, deltaX );
    }

    @Override
    public boolean equals( Object obj ) {
        return obj instanceof Coordinate && _x == ((Coordinate) obj)._x && _y == ((Coordinate) obj)._y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + ( int ) (doubleToLongBits( this._x ) ^ (doubleToLongBits( this._x ) >>> 32));
        hash = 47 * hash + ( int ) (doubleToLongBits( this._y ) ^ (doubleToLongBits( this._y ) >>> 32));
        return hash;

    }

}
