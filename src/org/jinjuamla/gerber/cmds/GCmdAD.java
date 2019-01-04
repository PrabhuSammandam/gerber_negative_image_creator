/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jinjuamla.gerber.cmds;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseUnsignedInt;
import org.jinjuamla.camfilelibrary.Geometry;
import org.jinjuamla.gerber.GraphicsState;
import org.jinjuamla.gerber.geometries.CircleGeometry;
import org.jinjuamla.gerber.geometries.ObroundGeometry;
import org.jinjuamla.gerber.geometries.PolygonGeometry;
import org.jinjuamla.gerber.geometries.RectGeometry;

/**
 *
 * @author psammand
 */
public class GCmdAD extends GCmd {

    private int _code;
    private String _templateName;
    private String _modifiers;

    public GCmdAD( int code, String templateName, String modifiers ) {
        this._code = code;
        this._templateName = templateName;
        this._modifiers = modifiers;
    }

    public String getTemplateName() {
        return _templateName;
    }

    public void setTemplateName( String templateName ) {
        this._templateName = templateName;
    }

    public String getModifiers() {
        return _modifiers;
    }

    public void setModifiers( String modifiers ) {
        this._modifiers = modifiers;
    }

    public int getCode() {
        return _code;
    }

    public void setCode( int code ) {
        this._code = code;
    }

    @Override
    public Geometry[] decode( GraphicsState gerberState ) {
        String[] values;

        values = _modifiers.split( "X" );

        if ( null != _templateName ) {
            switch ( _templateName ) {
                case "C":
                    if ( values.length > 0 ) {
                        double diameter = parseDouble( values[ 0 ] );
                        double holeDiameter = (values.length > 1) ? parseDouble( values[ 1 ] ) : 0;

                        CircleGeometry cgo = new CircleGeometry( diameter, holeDiameter );
                        cgo.setLevelPolarity( gerberState.getLevelPolarity() );
                        cgo.setUnits( gerberState.getUnitMode() );

                        gerberState.getApertureDict().put( _code, cgo );
                    }
                    break;
                case "P": {
                    if ( values.length > 0 ) {
                        double outerDia = parseDouble( values[ 0 ] );
                        int nVertices = (values.length > 1) ? parseUnsignedInt( values[ 1 ] ) : 0;
                        double rotation = (values.length > 2) ? parseDouble( values[ 2 ] ) : 0;
                        double holeDiameter = (values.length > 3) ? parseDouble( values[ 3 ] ) : 0;

                        PolygonGeometry polygonGObj = new PolygonGeometry( outerDia, nVertices, rotation, holeDiameter );
                        polygonGObj.setLevelPolarity( gerberState.getLevelPolarity() );
                        polygonGObj.setUnits( gerberState.getUnitMode() );

                        gerberState.getApertureDict().put( _code, polygonGObj );
                    }
                }
                break;
                case "R":
                case "O":
                    if ( values.length > 0 ) {
                        double width = parseDouble( values[ 0 ] );
                        double height = (values.length > 1) ? parseDouble( values[ 1 ] ) : 0;
                        double holeDiameter = (values.length > 2) ? parseDouble( values[ 2 ] ) : 0;

                        if ( "R".equals( _templateName ) ) {
                            RectGeometry prim = new RectGeometry( width, height, holeDiameter );
                            prim.setLevelPolarity( gerberState.getLevelPolarity() );
                            prim.setUnits( gerberState.getUnitMode() );

                            gerberState.getApertureDict().put( _code, prim );
                        } else {
                            ObroundGeometry prim = new ObroundGeometry( width, height, holeDiameter );
                            prim.setLevelPolarity( gerberState.getLevelPolarity() );
                            prim.setUnits( gerberState.getUnitMode() );

                            gerberState.getApertureDict().put( _code, prim );
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        return null;
    }

}
