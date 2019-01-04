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
import org.jinjuamla.camfilelibrary.NumberFormat;
import org.jinjuamla.excellon.ExcellonDecoderState;

/**
 *
 * @author psammand
 */
public class ECmdFactory {

    static NumberFormat nf = new NumberFormat( 2, 4, Consts.ZERO_OMISION_LEADING );

    public static ECmdComment createCommentCmd( ExcellonDecoderState state, String line ) {
        return new ECmdComment( line.substring( 1 ) );
    }

    public static ECmdRewindStop createRewindStopCmd( ExcellonDecoderState state, String line ) {
        return new ECmdRewindStop();
    }

    public static ECmdHdrBegin createHdrBeginCmd( ExcellonDecoderState state, String line ) {
        return new ECmdHdrBegin();
    }

    public static ECmdHdrEnd createHdrEndCmd( ExcellonDecoderState state, String line ) {
        return new ECmdHdrEnd();
    }

    public static ECmdEndOfProgram createEndOfProgramCmd( ExcellonDecoderState state, String line ) {
        line = line.substring( 3 );
        XYValue xy = parseCoordinatesIntl( state, line );

        if ( xy != null ) {
            return new ECmdEndOfProgram( xy.x, xy.y );
        }

        return null;
    }

    public static ECmdRoutCmd createECmdRoutCmd( ExcellonDecoderState state, String line ) {
        ECmdFactory.XYValue xy = parseCoordinatesIntl( state, line.substring( 3 ) );

        if ( xy != null ) {
            return new ECmdRoutCmd( xy.x, xy.y );
        }

        return null;
    }

    public static ECmdDrillMode createDrillModeCmd( ExcellonDecoderState state, String line ) {
        return new ECmdDrillMode();
    }

    public static ECmdUnitMode createUnitModeCmd( ExcellonDecoderState state, String line ) {
        int _units = (line.contains( "INCH" )) ? Consts.UNIT_MODE_INCH : Consts.UNIT_MODE_MM;
        int zero = (line.contains( "LZ" )) ? Consts.ZERO_OMISION_LEADING : Consts.ZERO_OMISION_TRAILING;

        nf.setZeroOmision( zero );

        return new ECmdUnitMode( _units, zero );
    }

    public static ECmdMeasurementMode createMeasurementModeCmd( ExcellonDecoderState state, String line ) {
        int _units = (line.contains( "M72" )) ? Consts.UNIT_MODE_INCH : Consts.UNIT_MODE_MM;

        return new ECmdMeasurementMode( _units );
    }

    public static ECmdIncrmentalMode createIncrmentalModeCmd( ExcellonDecoderState state, String line ) {
        return new ECmdIncrmentalMode( line.contains( "ON" ) );
    }

    public static ECmdAbsoulteMode createAbsoulteModeCmd( ExcellonDecoderState state, String line ) {
        return new ECmdAbsoulteMode();
    }

    public static ECmd createToolCmd( ExcellonDecoderState state, String line ) {
        Pattern compile = compile( "^T(?<code>[0-9]+).*C(?<hole>[+\\-]?\\d*\\.?\\d*)(.*)" );

        Matcher matcher = compile.matcher( line );

        if ( matcher.matches() ) {
            String codeStr = matcher.group( "code" );
            String holeStr = matcher.group( "hole" );

            int code = Integer.parseInt( codeStr );
            double hole = Double.parseDouble( holeStr );

            return new ECmdToolDefine( code, hole );
        }

        Pattern compile1 = compile( "^T(?<code>[0-9]+)$" );
        matcher = compile1.matcher( line );

        if ( matcher.matches() ) {
            String codeStr = matcher.group( "code" );
            int code = Integer.parseInt( codeStr );

            return new ECmdToolSelect( code );
        }
        return null;
    }

    public static ECmdRepeatHole createRepeatHoleCmd( ExcellonDecoderState state, String line ) {
        Pattern PATTERN_COMMENT = compile( "^R(?<count>[0-9]{1,4})(X(?<xoff>[+\\-]?\\d*\\.?\\d*))?(Y(?<yoff>[+\\-]?\\d*\\.?\\d*))?" );
        Matcher match = PATTERN_COMMENT.matcher( line );

        if ( match.matches() ) {
            String countStr = match.group( "count" );
            String xoffStr = match.group( "xoff" );
            String yoffStr = match.group( "yoff" );

            int count = Integer.parseInt( countStr );
            double xOffset = nf.decode( xoffStr );
            double yOffset = nf.decode( yoffStr );

            return new ECmdRepeatHole( count, xOffset, yOffset );
        }

        return null;
    }

    public static ECmdCoordinate createCoordinateCmd( ExcellonDecoderState state, String line ) {
        XYValue coord = parseCoordinatesIntl( state, line );

        if ( coord != null ) {
            return new ECmdCoordinate( coord.x, coord.y );
        }
        return null;
    }

    private static ECmdFactory.XYValue parseCoordinatesIntl( ExcellonDecoderState state, String line ) {
        if ( line.length() > 0 ) {
            Pattern PATTERN_COORDINATE = compile( "^(X(?<xoff>[+\\-]?\\d*\\.?\\d*))?(Y(?<yoff>[+\\-]?\\d*\\.?\\d*))?.*" );
            Matcher match = PATTERN_COORDINATE.matcher( line );

            if ( match.matches() ) {
                Double xOffset = nf.decode( match.group( "xoff" ) );
                Double yOffset = nf.decode( match.group( "yoff" ) );

                return new ECmdFactory.XYValue( xOffset, yOffset );
            }
        }
        return null;
    }

    private static class XYValue {

        public Double x;
        public Double y;

        public XYValue( Double x, Double y ) {
            this.x = x;
            this.y = y;
        }
    }

}
