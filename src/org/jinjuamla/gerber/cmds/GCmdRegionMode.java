/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import static org.jinjuamla.camfilelibrary.Consts.REGION_MODE_OFF;
import static org.jinjuamla.camfilelibrary.Consts.REGION_MODE_ON;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;
import org.jinjuamla.gerber.common.Region;
import org.jinjuamla.gerber.geometries.RegionGeometry;


/**
 *
 * @author psammand
 */
public class GCmdRegionMode extends GCmd {

    private int _regionMode;

    public GCmdRegionMode( int regionMode ) {
        this._regionMode = regionMode;
    }

    public int getRegionMode() {
        return _regionMode;
    }

    public void setRegionMode( int regionMode ) {
        this._regionMode = regionMode;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState ) {
        Geometry[] ret = null;
        /*if region mode is already on and if region mode off is requested then
			close the current region*/
        if ( gerberState.getRegionMode() == REGION_MODE_ON
                && _regionMode == REGION_MODE_OFF
                && gerberState.getCurrentRegion() != null ) {
            gerberState.getCurrentRegion().close();

            int index = 0;
            ret = new Geometry[ gerberState.getRegionList().size() + 1 ];

            for ( Region region : gerberState.getRegionList() ) {
                ret[ index ] = new RegionGeometry( region );
                index++;
            }

            ret[ index ] = new RegionGeometry(gerberState.getCurrentRegion() );

            gerberState.getRegionList().clear();
            gerberState.setCurrentRegion( null );
        }

        gerberState.setRegionMode( _regionMode );

        return ret;
    }

}
