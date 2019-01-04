/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import org.jinjuamla.camfilelibrary.Coordinate;


/**
 *
 * @author psammand
 */
public class PositionedGCmd extends GCmd {

    private Coordinate _position;
    private Coordinate _offset;

    public PositionedGCmd( Coordinate position ) {
        this._position = position;
        _offset = new Coordinate( 0, 0 );
    }

    public PositionedGCmd( double x, double y ) {
        _position = new Coordinate( x, y );
        _offset = new Coordinate( 0, 0 );
    }

    public Coordinate getOffset() {
        return _offset;
    }

    public void setOffset( Coordinate offset ) {
        this._offset = offset;
    }

    public Coordinate getPosition() {
        return _position;
    }

    public void setPosition( Coordinate position ) {
        this._position = position;
    }

}
