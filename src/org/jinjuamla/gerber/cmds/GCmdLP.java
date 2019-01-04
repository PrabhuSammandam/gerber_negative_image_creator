/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;


/**
 *
 * @author psammand
 */
public class GCmdLP extends GCmd {

    private int _levelPolarity;

    public GCmdLP( int levelPolarity ) {
        this._levelPolarity = levelPolarity;
    }

    public int getLevelPolarity() {
        return _levelPolarity;
    }

    public void setLevelPolarity( int levelPolarity ) {
        this._levelPolarity = levelPolarity;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState) {
        gerberState.setLevelPolarity( _levelPolarity );
        return null;
    }

}
