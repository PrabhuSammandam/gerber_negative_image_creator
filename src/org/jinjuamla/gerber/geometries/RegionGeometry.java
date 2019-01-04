/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.geometries;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import org.jinjuamla.camfilelibrary.BoundingBox;
import org.jinjuamla.camfilelibrary.Coordinate;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.common.Region;

/**
 *
 * @author psammand
 */
public class RegionGeometry extends Geometry {

    private Region _region;

    public RegionGeometry( Region region ) {
        this._region = region;
    }

    public Region getRegion() {
        return _region;
    }

    public void setRegion( Region region ) {
        this._region = region;
    }

    @Override
    protected void doUpdateValues( double value ) {
        super.doUpdateValues( value );

        if ( _region != null ) {
            for ( Coordinate point : _region.getPoints() ) {
                point.setX( point.getX() * value );
                point.setY( point.getY() * value );
            }
        }
    }

    @Override
    public BoundingBox getBounds() {
        Path2D.Double path = new Path2D.Double();

        Coordinate startPt = _region.getPoints().get( 0 );

        path.moveTo( startPt.getX(), startPt.getY() );

        for ( int i = 1; i < _region.getPoints().size(); i++ ) {
            Coordinate currentPt = _region.getPoints().get( i );
            path.lineTo( currentPt.getX(), currentPt.getY() );
        }

        Rectangle2D b = path.getBounds2D();

        return new BoundingBox( b.getMinX(), b.getMinY(), b.getMaxX(), b.getMaxY() );
    }

}
