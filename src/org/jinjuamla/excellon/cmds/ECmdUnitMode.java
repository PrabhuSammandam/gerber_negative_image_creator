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
public class ECmdUnitMode extends ECmd {

    private Integer _unitMode;
    private Integer _zeroOmisionMode;

    public ECmdUnitMode( Integer unitMode, Integer zeroOmisionMode ) {
        this._unitMode = unitMode;
        this._zeroOmisionMode = zeroOmisionMode;
    }

    public Integer getZeroOmisionMode() {
        return _zeroOmisionMode;
    }

    public void setZeroOmisionMode( Integer zeroOmisionMode ) {
        this._zeroOmisionMode = zeroOmisionMode;
    }

    public Integer getUnitMode() {
        return _unitMode;
    }

    public void setUnitMode( Integer unitMode ) {
        this._unitMode = unitMode;
    }

    @Override
    public Geometry[] evaluate( ExcellonDecoderState state ) {
        if ( state.getUnits() == null ) {
            state.setUnits( _unitMode );
        }
        if ( state.getZeroOmision() == null ) {
            state.setZeroOmision( _zeroOmisionMode );
        }
        return null;
    }

}
