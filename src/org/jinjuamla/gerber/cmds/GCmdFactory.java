/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import static java.lang.Integer.parseInt;
import java.util.regex.Matcher;
import static org.jinjuamla.camfilelibrary.Consts.COORDINATE_NOTATION_ABSOLUTE;
import static org.jinjuamla.camfilelibrary.Consts.COORDINATE_NOTATION_INCREMENTAL;
import static org.jinjuamla.camfilelibrary.Consts.INTERPOLATION_MODE_CIRCULAR_CLOCKWISE;
import static org.jinjuamla.camfilelibrary.Consts.INTERPOLATION_MODE_CIRCULAR_COUNTERCLOCKWISE;
import static org.jinjuamla.camfilelibrary.Consts.INTERPOLATION_MODE_LINEAR;
import static org.jinjuamla.camfilelibrary.Consts.LEVEL_POLARITY_CLEAR;
import static org.jinjuamla.camfilelibrary.Consts.LEVEL_POLARITY_DARK;
import static org.jinjuamla.camfilelibrary.Consts.QUADRANT_MODE_MULTIPLE;
import static org.jinjuamla.camfilelibrary.Consts.QUADRANT_MODE_SINGLE;
import static org.jinjuamla.camfilelibrary.Consts.REGION_MODE_OFF;
import static org.jinjuamla.camfilelibrary.Consts.REGION_MODE_ON;
import static org.jinjuamla.camfilelibrary.Consts.UNIT_MODE_INCH;
import static org.jinjuamla.camfilelibrary.Consts.UNIT_MODE_MM;
import static org.jinjuamla.camfilelibrary.Consts.ZERO_OMISION_LEADING;
import static org.jinjuamla.camfilelibrary.Consts.ZERO_OMISION_TRAILING;
import org.jinjuamla.camfilelibrary.NumberFormat;

/**
 *
 * @author psammand
 */
public class GCmdFactory {

    static NumberFormat _numberFormat;

    public static GCmd createFSStatement( Matcher match ) {
        String zeroStr = match.group( "zero" );
        String notationStr = match.group( "notation" );
        String xnStr = match.group( "xn" );
        String xmStr = match.group( "xm" );

        int zero = ("L".equals( zeroStr )) ? ZERO_OMISION_LEADING : ZERO_OMISION_TRAILING;
        int notation = ("A".equals( notationStr )) ? COORDINATE_NOTATION_ABSOLUTE : COORDINATE_NOTATION_INCREMENTAL;
        int xn = parseInt( xnStr );
        int xm = parseInt( xmStr );

        _numberFormat = new NumberFormat( xn, xm, zero );

        GCmd st = new GCmdFS( zero, notation, _numberFormat );

        return st;
    }

    public static GCmd createCommentStatement( Matcher currentMatch ) {
        String comment = currentMatch.group( "comment" );

        GCmdComment stmt = new GCmdComment( comment );

        return stmt;
    }

    public static GCmd createGStatement( Matcher currentMatch ) {
        String codeStr = currentMatch.group( "code" );
        int code = parseInt( codeStr );

        switch ( code ) {
            case 1:
                return new GCmdInterpolateMode( INTERPOLATION_MODE_LINEAR );
            case 2:
                return new GCmdInterpolateMode( INTERPOLATION_MODE_CIRCULAR_CLOCKWISE );
            case 3:
                return new GCmdInterpolateMode( INTERPOLATION_MODE_CIRCULAR_COUNTERCLOCKWISE );
            case 74:
                return new GCmdQuadrantMode( QUADRANT_MODE_SINGLE );
            case 75:
                return new GCmdQuadrantMode( QUADRANT_MODE_MULTIPLE );
            case 36:
                return new GCmdRegionMode( REGION_MODE_ON );
            case 37:
                return new GCmdRegionMode( REGION_MODE_OFF );
        }

        return null;
    }

    public static GCmd createDStatement( Matcher currentMatch ) {
        String codeStr = currentMatch.group( "opcode" );
        String xStr = currentMatch.group( "x" );
        String yStr = currentMatch.group( "y" );
        String iStr = currentMatch.group( "i" );
        String jStr = currentMatch.group( "j" );

        int code = parseInt( codeStr );

        if ( code >= 10 ) {
            return new GCmdSelectAperture( code );
        } else {
            Double x = _numberFormat.decode( xStr );
            Double y = _numberFormat.decode( yStr );
            Double i = _numberFormat.decode( iStr );
            Double j = _numberFormat.decode( jStr );

            switch ( code ) {
                case 1:
                    return new GCmdInterpolate( x, y, i, j );
                case 2:
                    return new GCmdMove( x, y );
                case 3:
                    return new GCmdFlash( x, y );
            }
        }

        return null;
    }

    public static GCmd createMOStatement( Matcher currentMatch ) {
        String unitStr = currentMatch.group( "unit" );

        int unitMode = ("IN".equals( unitStr )) ? UNIT_MODE_INCH : UNIT_MODE_MM;

        return new GCmdMO( unitMode );
    }

    public static GCmd createLPStatement( Matcher currentMatch ) {
        String polarityStr = currentMatch.group( "polarity" );

        int polarity = ("C".equals( polarityStr )) ? LEVEL_POLARITY_CLEAR : LEVEL_POLARITY_DARK;

        return new GCmdLP( polarity );
    }

    public static GCmd createADStatement( Matcher currentMatch ) {
        String codeStr = currentMatch.group( "dcode" );
        String nameStr = currentMatch.group( "name" );
        String modifierStr = currentMatch.group( "modifiers" );

        int code = parseInt( codeStr );

        return new GCmdAD( code, nameStr, modifierStr );
    }

    public static GCmd createAMStatement( Matcher currentMatch ) {
        String name = currentMatch.group( "name" );
        String macroModifiers = currentMatch.group( "macro" );

        return new GCmdAM( name, macroModifiers );
    }

}
