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
package org.jinjuamla.excellon.cmds;

import java.util.ArrayList;
import java.util.List;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.excellon.ExcellonDecoderState;
import static org.jinjuamla.excellon.ExcellonDecoderState.PARSER_STATE_HEADER;
import org.jinjuamla.gerber.geometries.DrillGeometry;

/**
 *
 * @author psammand
 */
public class ECmdRepeatHole extends ECmd {

    private int _holeCount;
    private Double _xGap;
    private Double _yGap;

    public ECmdRepeatHole( int holeCount, Double xGap, Double yGap ) {
        this._holeCount = holeCount;
        this._xGap = xGap;
        this._yGap = yGap;
    }

    public Double getyGap() {
        return _yGap;
    }

    public void setyGap( Double yGap ) {
        this._yGap = yGap;
    }

    public Double getxGap() {
        return _xGap;
    }

    public void setxGap( Double xGap ) {
        this._xGap = xGap;
    }

    public int getHoleCount() {
        return _holeCount;
    }

    public void setHoleCount( int holeCount ) {
        this._holeCount = holeCount;
    }

    @Override
    public Geometry[] evaluate( ExcellonDecoderState state ) {
        if ( state.getState() != PARSER_STATE_HEADER ) {
            double xdelta = (_xGap == null) ? 0 : _xGap;
            double ydelta = (_yGap == null) ? 0 : _yGap;
            List<Geometry> _graphicsObjList = new ArrayList<>();

            double x = state.getX();
            double y = state.getY();

            for ( int i = 0; i < _holeCount; i++ ) {
                x += xdelta;
                y += ydelta;

                DrillGeometry drillGObj = new DrillGeometry( state.getCurrentToolDiameter() );
                drillGObj.setPosition( new Coordinate( x, y ) );
                drillGObj.setUnits( state.getUnits() );
                _graphicsObjList.add( drillGObj );
            }
            state.setX( x );
            state.setY( y );

            return ( Geometry[] ) _graphicsObjList.toArray();

        }
        return null;
    }

}
