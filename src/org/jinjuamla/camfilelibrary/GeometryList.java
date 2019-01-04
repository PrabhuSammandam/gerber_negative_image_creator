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
package org.jinjuamla.camfilelibrary;

import java.util.LinkedList;

/**
 *
 * @author psammand
 */
public class GeometryList extends LinkedList<Geometry> {

    private static final long serialVersionUID = 1L;

    BoundingBox _bounds;

    public GeometryList() {
        this._bounds = null;
    }

    public BoundingBox getBounds() {
        if ( _bounds == null ) {
            _bounds = new BoundingBox( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY );
            this.stream().filter( (gObj) -> ( gObj != null ) ).map( (gObj) -> gObj.getBounds() ).forEachOrdered( (bounds) -> {
                _bounds.updateLimits( bounds );
            } );
        }
        return _bounds;
    }

    public void toInch() {
        this.stream().filter( (geo) -> (geo != null) ).forEachOrdered(Geometry::toInch);
    }

    public void toMM() {
        this.stream().filter( (geo) -> ( geo != null ) ).forEachOrdered(Geometry::toMM);
    }
}
