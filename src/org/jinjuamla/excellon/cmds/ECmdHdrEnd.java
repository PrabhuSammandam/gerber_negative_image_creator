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
package org.jinjuamla.excellon.cmds;

import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.excellon.ExcellonDecoderState;
import static org.jinjuamla.excellon.ExcellonDecoderState.PARSER_STATE_DRILL;
import static org.jinjuamla.excellon.ExcellonDecoderState.PARSER_STATE_HEADER;

/**
 *
 * @author psammand
 */
public class ECmdHdrEnd extends ECmd {

    public ECmdHdrEnd() {
    }

    @Override
    public Geometry[] evaluate( ExcellonDecoderState state ) {
        if ( state.getState() == PARSER_STATE_HEADER ) {
            state.setState( PARSER_STATE_DRILL );
        }
        return null;
    }

}
