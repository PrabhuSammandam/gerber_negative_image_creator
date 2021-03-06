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

import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.excellon.ExcellonDecoderState;

/**
 *
 * @author psammand
 */
public class ECmdToolSelect extends ECmd {

    private int _code;

    public ECmdToolSelect( int code ) {
        this._code = code;
    }

    public int getCode() {
        return _code;
    }

    public void setCode( int code ) {
        this._code = code;
    }

    @Override
    public Geometry[] evaluate( ExcellonDecoderState state ) {
        if ( _code != 0 ) {
            state.setActiveTool( _code );
        }
        return null;
    }

}
