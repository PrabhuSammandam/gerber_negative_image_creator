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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.compile;
import org.jinjuamla.camfilelibrary.Consts;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.camfilelibrary.NumberFormat;
import org.jinjuamla.excellon.ExcellonDecoderState;
import org.jinjuamla.excellon.ExcellonEncoderState;

/**
 *
 * @author psammand
 */
public class ECmdComment extends ECmd {

    private String _comment;
    private boolean isFormatLinePresent = false;

    public ECmdComment( String comment ) {
        this._comment = comment;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment( String comment ) {
        this._comment = comment;
    }

    @Override
    public String encode( ExcellonEncoderState encState ) {
        String cmd;

        if ( isFormatLinePresent ) {
            //cmd = String.format( ";FORMAT={%s/ %s / %s / %s}", args );
        }
        return super.encode( encState ); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Geometry[] evaluate( ExcellonDecoderState state ) {
        if ( _comment.contains( "FORMAT" ) ) {
            Pattern regex = compile( "^FORMAT=\\{(?<format>.*)/(?<notation>.*)/(?<unit>.*)/(?<zero>.*)\\}$" );

            Matcher matcher = regex.matcher( _comment );
            if ( matcher.matches() ) {
                isFormatLinePresent = true;
                String formatStr = matcher.group( "format" );
                String notationStr = matcher.group( "notation" );
                String unitStr = matcher.group( "unit" );
                String zeroStr = matcher.group( "zero" );

                int unitMode = (unitStr.trim().equals( "metric" )) ? Consts.UNIT_MODE_MM : Consts.UNIT_MODE_INCH;
                int notationMode = (notationStr.trim().equals( "absolute" )) ? Consts.COORDINATE_NOTATION_ABSOLUTE : Consts.COORDINATE_NOTATION_INCREMENTAL;
                int zeroOmisionMode = 0;
                boolean parseFormatString = true;

                switch ( zeroStr.trim() ) {
                    case "suppress trailing zeros":
                        zeroOmisionMode = Consts.ZERO_OMISION_TRAILING;
                        break;
                    case "suppress leading zeros":
                        zeroOmisionMode = Consts.ZERO_OMISION_LEADING;
                        break;
                    case "decimal":
                        zeroOmisionMode = Consts.ZERO_OMISSION_DECIMAL;
                        parseFormatString = false;
                        break;
                    case "keep zeros":
                        zeroOmisionMode = Consts.ZERO_OMISION_LEADING;
                        break;
                    default:
                        break;
                }

                state.setNotation( notationMode );
                state.setUnits( unitMode );
                state.setZeroOmision( zeroOmisionMode );

                if ( parseFormatString ) {
                    String[] split = formatStr.split( ":" );
                    NumberFormat numberFormat = new NumberFormat( Integer.parseInt( split[ 0 ] ), Integer.parseInt( split[ 1 ] ) );
                    state.setNumberFormat( numberFormat );
                }
            }
        }

        return null;
    }

}
