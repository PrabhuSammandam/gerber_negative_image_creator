/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

/**
 *
 * @author psammand
 */
public class GCmdComment extends GCmd {

    private String _comment;

    public GCmdComment( String comment ) {
        this._comment = comment;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment( String comment ) {
        this._comment = comment;
    }

}
