/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.common;

import java.util.LinkedList;
import java.util.List;
import org.jinjuamla.camfilelibrary.Coordinate;

/**
 *
 * @author psammand
 */
public class Region {

    List<Coordinate> _points = null;

    public Region( Coordinate startPt ) {
        this();
        _points.add( startPt );
    }

    public Region() {
        _points = new LinkedList<>();
    }

    public void close() {
        if ( !isClosed() ) {
            _points.add( new Coordinate( _points.get( 0 ) ) );
        }
    }

    public List<Coordinate> getPoints() {
        return _points;
    }

    public boolean isClosed() {
        if ( _points.size() < 3 ) {
            return true;
        }

        Coordinate startPt = _points.get( 0 );

        return startPt.equals( _points.get( _points.size() - 1 ) );
    }

}
