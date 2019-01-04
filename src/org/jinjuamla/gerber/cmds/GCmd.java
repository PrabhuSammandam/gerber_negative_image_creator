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
public class GCmd implements IGCmd {

    @Override
    public Geometry[] decode( GraphicsState gerberState ) {
        return null;
    }

    public void setOffset( double x, double y ) {

    }

    public void invert( boolean isInvertX, boolean isInvertY ) {

    }
}
