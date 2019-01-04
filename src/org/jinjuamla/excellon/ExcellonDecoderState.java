/*
 * Copyright (C) 2016 psammand
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jinjuamla.excellon;

import java.util.HashMap;
import org.jinjuamla.camfilelibrary.Consts;
import org.jinjuamla.camfilelibrary.NumberFormat;

/**
 *
 * @author psammand
 */
public class ExcellonDecoderState {

    public static final int PARSER_STATE_INIT = 0;
    public static final int PARSER_STATE_HEADER = 1;
    public static final int PARSER_STATE_DRILL = 2;
    public static final int PARSER_STATE_ROUT = 3;

    private int _state = PARSER_STATE_INIT;
    private NumberFormat _numberFormat = null;
    private Integer _zeroOmision = null;
    private double _x = 0;
    private double _y = 0;
    /*Unless you specify otherwise, the CNC-7 runs in the absolute mode,
     and part programs must be programmed for absolute. When you program
     in the incremental mode, include the ICI,ON command in the part 
    program header, or in the MACH.DAT file.*/
    private Integer _notation = Consts.COORDINATE_NOTATION_ABSOLUTE;
    private Integer _units = null;
    private HashMap<Integer, Double> _toolMap = new HashMap<>();
    private int _activeTool = -1;

    public int getState() {
        return _state;
    }

    public void setState( int state ) {
        this._state = state;
    }

    public HashMap<Integer, Double> getToolMap() {
        return _toolMap;
    }

    public void setToolMap( HashMap<Integer, Double> toolMap ) {
        this._toolMap = toolMap;
    }

    public int getActiveTool() {
        return _activeTool;
    }

    public void setActiveTool( int activeTool ) {
        this._activeTool = activeTool;
    }

    public NumberFormat getNumberFormat() {
        return _numberFormat;
    }

    public void setNumberFormat( NumberFormat numberFormat ) {
        this._numberFormat = numberFormat;
    }

    public Integer getZeroOmision() {
        return _zeroOmision;
    }

    public void setZeroOmision( Integer zeroOmision ) {
        this._zeroOmision = zeroOmision;
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

    public Integer getNotation() {
        return _notation;
    }

    public void setNotation( Integer notation ) {
        this._notation = notation;
    }

    public Integer getUnits() {
        return _units;
    }

    public void setUnits( Integer units ) {
        this._units = units;
    }

    public Double getCurrentToolDiameter() {
        if ( _toolMap.containsKey( _activeTool ) ) {
            return _toolMap.get( _activeTool );
        }

        return null;
    }

}
