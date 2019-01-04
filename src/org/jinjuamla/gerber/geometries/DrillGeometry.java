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
package org.jinjuamla.gerber.geometries;

import org.jinjuamla.camfilelibrary.BoundingBox;
import org.jinjuamla.camfilelibrary.Geometry;

/**
 *
 * @author psammand
 */
public class DrillGeometry extends Geometry {

    public DrillGeometry( double diameter ) {
        this._holeDiameter = diameter;
        updateInternal();
    }

    public DrillGeometry( Geometry other ) {
        super( other );
        this._holeDiameter = other.getHoleDiameter();
        updateInternal();
    }

    public double radius() {
        return _holeDiameter / 2;
    }

    @Override
    public BoundingBox getBounds() {
        if ( _boundingBox == null ) {
            _boundingBox = new BoundingBox();
            _boundingBox.setMinMax( _position, radius(), radius() );
        }
        return _boundingBox;
    }

    @Override
    public String toString() {
        return String.format( "Drill { dia=%f, %s }", _holeDiameter, super.toString() );
    }

}
