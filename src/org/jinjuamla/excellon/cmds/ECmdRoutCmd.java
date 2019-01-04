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

import org.jinjuamla.camfilelibrary.Consts;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.excellon.ExcellonDecoderState;
import static org.jinjuamla.excellon.ExcellonDecoderState.PARSER_STATE_ROUT;

/**
 *
 * @author psammand
 */
public class ECmdRoutCmd extends ECmd {

    private Double _x;

    private Double _y;

    public ECmdRoutCmd( Double x, Double y ) {
        this._x = x;
        this._y = y;
    }

    public Double getY() {
        return _y;
    }

    public void setY( Double y ) {
        this._y = y;
    }

    public Double getX() {
        return _x;
    }

    public void setX( Double x ) {
        this._x = x;
    }

    @Override
    public Geometry[] evaluate( ExcellonDecoderState state ) {
        state.setState( PARSER_STATE_ROUT );

        Integer notation = state.getNotation();

        if ( notation == Consts.COORDINATE_NOTATION_ABSOLUTE ) {
            if ( _x != null ) {
                state.setX( _x );
            }
            if ( _y != null ) {
                state.setY( _y );
            }
        } else {
            if ( _x != null ) {
                state.setX( state.getX() + _x );
            }
            if ( _y != null ) {
                state.setY( state.getY() + _y );
            }
        }
        return null;
    }

}
