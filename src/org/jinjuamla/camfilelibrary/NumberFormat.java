/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.camfilelibrary;

import static java.lang.Double.parseDouble;
import static org.jinjuamla.camfilelibrary.Consts.ZERO_OMISION_LEADING;
import static org.jinjuamla.camfilelibrary.Consts.ZERO_OMISION_TRAILING;

/**
 *
 * @author psammand
 */
public class NumberFormat {

    private int _zeroOmision;
    private int _decLength;
    private int _intLength;

    public NumberFormat( int intLength, int decLength, int zeroOmision ) {
        this._zeroOmision = zeroOmision;
        this._decLength = decLength;
        this._intLength = intLength;
    }

    public NumberFormat( int intLength, int decLength ) {
        this( intLength, decLength, Consts.ZERO_OMISION_LEADING );
    }

    public int getZeroOmision() {
        return _zeroOmision;
    }

    public void setZeroOmision( int zeroOmision ) {
        this._zeroOmision = zeroOmision;
    }

    public int totalLength() {
        return _decLength + _intLength;
    }

    public int getDecLength() {
        return _decLength;
    }

    public void setDecLength( int decLength ) {
        this._decLength = decLength;
    }

    public int getIntLength() {
        return _intLength;
    }

    public void setIntLength( int intLength ) {
        this._intLength = intLength;
    }

    public Double decode( String value ) {
        String sValue = value; 
        if ( sValue == null || sValue.length() <= 0 ) {
            return null;
        }

        /*Decimals are not needed in either INCH or METRIC modes.
        But if you do use them, the decimal point will automatically override
        leading zero or trailing zero mode. Coordinates can be typed with or
        without the decimal. If you use the decimal and the coordinate distance
        is less than one inch or one centimeter, you can eliminate the zeros
        to the left of the decimal. */
        if ( sValue.contains( "." ) ) {
            return Double.parseDouble( sValue );
        }

        boolean isNegative = false;

        if ( sValue.startsWith( "+" ) ) {
            sValue = sValue.substring( 1 );
        }

        if ( sValue.startsWith( "-" ) ) {
            isNegative = true;
            sValue = sValue.substring( 1 );
        }

        int missingDigits = totalLength() - sValue.length();

        if ( _zeroOmision == ZERO_OMISION_TRAILING ) {
            for ( int i = 0; i < missingDigits; i++ ) {
                sValue += "0";
            }
        } else if ( _zeroOmision == ZERO_OMISION_LEADING ) {
            for ( int i = 0; i < missingDigits; i++ ) {
                sValue = "0" + sValue;
            }
        }

        sValue = sValue.substring( 0, _intLength ) + "." + sValue.substring( _intLength );

        double result = parseDouble( sValue );

        return (isNegative) ? (result * -1) : result;
    }

}
