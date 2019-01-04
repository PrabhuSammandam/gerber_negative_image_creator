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
public class GCmdAM extends GCmd {

    private String _name;
    private String _macroModifiers;

    public GCmdAM( String name, String macroModifiers ) {
        this._name = name;
        this._macroModifiers = macroModifiers;
    }

    public String getMacroModifiers() {
        return _macroModifiers;
    }

    public void setMacroModifiers( String macroModifiers ) {
        this._macroModifiers = macroModifiers;
    }

    public String getName() {
        return _name;
    }

    public void setName( String name ) {
        this._name = name;
    }

    @Override
   public Geometry[] decode( GraphicsState gerberState) {
       return null;
    }
    
}
