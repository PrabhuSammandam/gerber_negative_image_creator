/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.jinjuamla.camfilelibrary.Consts;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.camfilelibrary.NumberFormat;
import org.jinjuamla.gerber.common.Region;

/**
 *
 * @author psammand
 */
public class GraphicsState {

    private int _levelPolarity;
    private int _quadrantMode;
    private int _unitMode;
    private int _currentApertureCode;
    private int _regionMode;
    private int _interpolateMode;
    private int _zeroOmision;
    private int _notation;
    private NumberFormat _numberFormat;
    private Coordinate _currentPosition = new Coordinate( 0, 0 );
    private final HashMap<Integer, Geometry> _apertureDict;
    private final List<Geometry> _geometryList;
    List<Region> _regionList = new LinkedList<>();

    private Region _currentRegion;

    public GraphicsState() {
        this._currentApertureCode = 0;
        this._apertureDict = new HashMap<>();
        _geometryList = new ArrayList<>();
        _regionMode = Consts.REGION_MODE_OFF;
        _zeroOmision = Consts.ZERO_OMISION_TRAILING;
        _notation = Consts.COORDINATE_NOTATION_ABSOLUTE;
        _levelPolarity = Consts.LEVEL_POLARITY_DARK;
        _interpolateMode = Consts.INTERPOLATION_MODE_LINEAR;
        _quadrantMode = Consts.QUADRANT_MODE_MULTIPLE;
        _unitMode = Consts.UNIT_MODE_MM;
    }

    public Region getCurrentRegion() {
        return _currentRegion;
    }

    public void setCurrentRegion( Region currentRegion ) {
        this._currentRegion = currentRegion;
    }

    public List<Region> getRegionList() {
        return _regionList;
    }

    public Geometry getCurrentAperture() {
        if ( _apertureDict.containsKey( _currentApertureCode ) ) {
            return _apertureDict.get( _currentApertureCode );
        }
        return null;
    }

    public List<Geometry> getGeometryList() {
        return _geometryList;
    }

    public HashMap<Integer, Geometry> getApertureDict() {
        return _apertureDict;
    }

    public int getZeroOmision() {
        return _zeroOmision;
    }

    public void setZeroOmision( int zeroOmision ) {
        this._zeroOmision = zeroOmision;
    }

    public int getNotation() {
        return _notation;
    }

    public void setNotation( int notation ) {
        this._notation = notation;
    }

    public NumberFormat getNumberFormat() {
        return _numberFormat;
    }

    public void setNumberFormat( NumberFormat numberFormat ) {
        this._numberFormat = numberFormat;
    }

    public int getInterpolateMode() {
        return _interpolateMode;
    }

    public void setInterpolateMode( int interpolateMode ) {
        this._interpolateMode = interpolateMode;
    }

    public double getX() {
        return _currentPosition.getX();
    }

    public void setX( double x ) {
        _currentPosition.setX( x );
    }

    public double getY() {
        return _currentPosition.getY();
    }

    public void setY( double y ) {
        _currentPosition.setY( y );
    }

    public Coordinate getCurrentPosition() {
        return _currentPosition;
    }

    public void setCurrentPosition( Coordinate currentPosition ) {
        this._currentPosition = currentPosition;
    }

    public int getCurrentApertureCode() {
        return _currentApertureCode;
    }

    public void setCurrentApertureCode( int currentApertureCode ) {
        this._currentApertureCode = currentApertureCode;
    }

    public int getRegionMode() {
        return _regionMode;
    }

    public void setRegionMode( int regionMode ) {
        this._regionMode = regionMode;
    }

    public int getQuadrantMode() {
        return _quadrantMode;
    }

    public void setQuadrantMode( int quadrantMode ) {
        this._quadrantMode = quadrantMode;
    }

    public int getUnitMode() {
        return _unitMode;
    }

    public void setUnitMode( int unitMode ) {
        this._unitMode = unitMode;
    }

    public int getLevelPolarity() {
        return _levelPolarity;
    }

    public void setLevelPolarity( int levelPolarity ) {
        this._levelPolarity = levelPolarity;
    }

}
