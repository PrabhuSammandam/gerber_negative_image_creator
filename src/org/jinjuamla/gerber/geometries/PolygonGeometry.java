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
public class PolygonGeometry extends Geometry {

    private double _outerDiameter;
    private int _vertices;
    private double _rotateAngle;

    public PolygonGeometry( double outerDiameter, int vertices, double rotateAngle ) {
        this._outerDiameter = outerDiameter;
        this._vertices = vertices;
        this._rotateAngle = rotateAngle;
        this._holeDiameter = 0;

        updateInternal();
    }

    public PolygonGeometry( double outerDiameter, int vertices, double rotateAngle, double holeDiameter ) {
        this._outerDiameter = outerDiameter;
        this._vertices = vertices;
        this._rotateAngle = rotateAngle;
        this._holeDiameter = holeDiameter;

        updateInternal();
    }

    public PolygonGeometry( PolygonGeometry other ) {
        super( other );

        this._outerDiameter = other._outerDiameter;
        this._vertices = other._vertices;
        this._rotateAngle = other._rotateAngle;
        this._holeDiameter = other._holeDiameter;

        updateInternal();
    }

    public int getVertices() {
        return _vertices;
    }

    public void setVertices( int vertices ) {
        this._vertices = vertices;
        updateInternal();
    }

    @Override
    public double getRotateAngle() {
        return _rotateAngle;
    }

    @Override
    public void setRotateAngle( double rotateAngle ) {
        this._rotateAngle = rotateAngle;
        updateInternal();
    }

    public double getOuterDiameter() {
        return _outerDiameter;
    }

    public void setOuterDiameter( double outerDiameter ) {
        this._outerDiameter = outerDiameter;
        updateInternal();
    }

    @Override
    protected void doUpdateValues( double value ) {
        super.doUpdateValues( value );
        _outerDiameter *= value;
    }

    @Override
    public BoundingBox getBounds() {
        return null;
    }

}
