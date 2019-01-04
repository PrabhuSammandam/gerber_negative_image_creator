/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;

/**
 * Basic Gerber command interface. All the gerber commands should be implemented
 * by this interface.
 * @author psammand
 */
public interface IGCmd {

    /**
     * Evaluate this command. In evaluation all the geometry objects are created.
     * @param gerberState
     * @return
     */
    public Geometry[] decode( GraphicsState gerberState );
}
